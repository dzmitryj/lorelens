package lore.codegen

/**
 * Parses the subset of C that cbindgen emits, not C in general. Every construct
 * it does not recognise is a hard failure: silently skipping one would produce a
 * struct layout with a missing field, which is undetectable memory corruption at
 * runtime rather than an error.
 */
object HeaderParser {

    private val PRIMITIVES = setOf(
        "bool", "char", "int", "float", "double",
        "int8_t", "int16_t", "int32_t", "int64_t",
        "uint8_t", "uint16_t", "uint32_t", "uint64_t",
        "intptr_t", "uintptr_t", "size_t",
    )

    private val SKIPPED_DIRECTIVES = setOf("#pragma", "#include")

    fun parse(source: String): CHeader {
        val defines = mutableListOf<CDefine>()
        val enums = mutableListOf<CEnum>()
        val structs = mutableListOf<CStruct>()
        val aliases = mutableListOf<CAlias>()
        val functions = mutableListOf<CFunction>()

        val lines = source.lines()
        val doc = mutableListOf<String>()
        var i = 0

        while (i < lines.size) {
            val raw = lines[i]
            val line = raw.trim()

            when {
                line.isEmpty() -> {
                    doc.clear()
                    i++
                }

                line.startsWith("//") -> {
                    doc += line.removePrefix("//").trim()
                    i++
                }

                line.startsWith("/*") -> {
                    while (i < lines.size && !lines[i].contains("*/")) i++
                    i++
                }

                SKIPPED_DIRECTIVES.any { line.startsWith(it) } -> {
                    doc.clear()
                    i++
                }

                line.startsWith("#define") -> {
                    defines += parseDefine(line)
                    doc.clear()
                    i++
                }

                line.startsWith("typedef enum ") || line.startsWith("enum ") -> {
                    val (decl, next) = parseEnum(lines, i, doc.toList())
                    enums += decl
                    doc.clear()
                    i = next
                }

                line.startsWith("typedef struct ") || line.startsWith("typedef union ") -> {
                    val (decl, next) = parseStructOrAlias(lines, i, doc.toList(), structs, aliases)
                    decl?.let { structs += it }
                    doc.clear()
                    i = next
                }

                line.startsWith("typedef ") -> {
                    val (text, next) = joinUntilSemicolon(lines, i)
                    aliases += parseAlias(text)
                    doc.clear()
                    i = next
                }

                else -> {
                    val (text, next) = joinUntilSemicolon(lines, i)
                    functions += parseFunction(text, doc.toList())
                    doc.clear()
                    i = next
                }
            }
        }

        return CHeader(defines, enums, structs, aliases, functions)
    }

    private fun parseDefine(line: String): CDefine {
        val rest = line.removePrefix("#define").trim()
        val name = rest.substringBefore(' ')
        val value = rest.removePrefix(name).trim()
        return CDefine(name, value)
    }

    private fun joinUntilSemicolon(lines: List<String>, start: Int): Pair<String, Int> {
        val builder = StringBuilder()
        var i = start
        while (i < lines.size) {
            val line = lines[i].trim()
            if (!line.startsWith("//")) {
                if (builder.isNotEmpty()) builder.append(' ')
                builder.append(line)
            }
            i++
            if (line.endsWith(";")) return builder.toString() to i
        }
        throw HeaderParseException("Unterminated declaration starting at line ${start + 1}: ${lines[start]}")
    }

    private fun parseEnum(lines: List<String>, start: Int, doc: List<String>): Pair<CEnum, Int> {
        val header = lines[start].trim()
        val typedef = header.startsWith("typedef ")
        val name = header
            .removePrefix("typedef ")
            .removePrefix("enum ")
            .substringBefore('{')
            .trim()
            .ifEmpty { throw HeaderParseException("Anonymous enum at line ${start + 1}") }

        val constants = mutableListOf<CEnumConstant>()
        val constantDoc = mutableListOf<String>()
        var next = -1L
        var i = start + 1

        while (i < lines.size) {
            val line = lines[i].trim()
            when {
                line.startsWith("//") -> constantDoc += line.removePrefix("//").trim()

                line.startsWith("}") -> {
                    val closer = if (typedef) "} $name;" else "};"
                    if (line != closer) {
                        throw HeaderParseException("Expected '$closer' at line ${i + 1}, found '$line'")
                    }
                    return CEnum(name, constants, doc) to i + 1
                }

                line.isEmpty() -> constantDoc.clear()

                else -> {
                    val entry = line.removeSuffix(",")
                    val constName = entry.substringBefore('=').trim()
                    val value = entry.substringAfter('=', "").trim()
                        .let { if (it.isEmpty()) next + 1 else parseIntLiteral(it, i + 1) }
                    constants += CEnumConstant(constName, value, constantDoc.toList())
                    constantDoc.clear()
                    next = value
                }
            }
            i++
        }
        throw HeaderParseException("Unterminated enum starting at line ${start + 1}")
    }

    private fun parseIntLiteral(text: String, line: Int): Long = when {
        text.startsWith("0x") || text.startsWith("0X") -> text.drop(2).toLongOrNull(16)
        else -> text.toLongOrNull()
    } ?: throw HeaderParseException("Unsupported enum value '$text' at line $line")

    private fun parseStructOrAlias(
        lines: List<String>,
        start: Int,
        doc: List<String>,
        structs: MutableList<CStruct>,
        aliases: MutableList<CAlias>,
    ): Pair<CStruct?, Int> {
        val header = lines[start].trim()

        if (!header.endsWith("{")) {
            val (text, next) = joinUntilSemicolon(lines, start)
            val words = text.removeSuffix(";").split(Regex("\\s+"))
            if (words.size == 4 && words[0] == "typedef" && words[1] == "struct" && words[2] == words[3]) {
                structs += CStruct(words[3], emptyList(), opaque = true, doc = doc)
                return null to next
            }
            aliases += parseAlias(text)
            return null to next
        }

        val (fields, closer) = parseFields(lines, start + 1, "}")
        val closeLine = lines[closer].trim()
        val name = closeLine.removePrefix("}").removeSuffix(";").trim()
        if (name.isEmpty()) throw HeaderParseException("Anonymous struct typedef at line ${closer + 1}")
        return CStruct(name, fields, doc = doc) to closer + 1
    }

    /** Returns the fields plus the index of the line holding the closing brace. */
    private fun parseFields(lines: List<String>, start: Int, closerPrefix: String): Pair<List<CField>, Int> {
        val fields = mutableListOf<CField>()
        val doc = mutableListOf<String>()
        var i = start

        while (i < lines.size) {
            val line = lines[i].trim()
            when {
                line.startsWith("//") -> doc += line.removePrefix("//").trim()

                line.isEmpty() -> doc.clear()

                line.startsWith(closerPrefix) -> return fields to i

                line == "union {" || line == "struct {" -> {
                    val (nested, closer) = parseFields(lines, i + 1, "}")
                    val closeLine = lines[closer].trim()
                    if (closeLine != "};") {
                        throw HeaderParseException("Named nested member at line ${closer + 1}: '$closeLine'")
                    }
                    fields += CField("", CType.InlineUnion(nested), doc.toList())
                    doc.clear()
                    i = closer
                }

                line.endsWith(";") -> {
                    fields += parseField(line, i + 1, doc.toList())
                    doc.clear()
                }

                else -> throw HeaderParseException("Unrecognised struct member at line ${i + 1}: '$line'")
            }
            i++
        }
        throw HeaderParseException("Unterminated struct starting at line ${start + 1}")
    }

    private fun parseField(line: String, lineNumber: Int, doc: List<String>): CField {
        val decl = line.removeSuffix(";").trim()

        val functionPointer = Regex("^.*\\(\\s*\\*\\s*(\\w+)\\s*\\)\\s*\\(").find(decl)
        if (functionPointer != null) {
            return CField(functionPointer.groupValues[1], CType.Pointer(CType.Void), doc)
        }

        val arrayMatch = Regex("^(.*?)\\s*(\\**)\\s*(\\w+)\\[(\\d+)]$").find(decl)
        if (arrayMatch != null) {
            val (typeText, stars, name, length) = arrayMatch.destructured
            val element = pointerize(parseTypeName(typeText, lineNumber), stars.length)
            return CField(name, CType.Array(element, length.toInt()), doc)
        }

        val match = Regex("^(.*?)\\s*(\\**)\\s*(\\w+)$").find(decl)
            ?: throw HeaderParseException("Unparseable field at line $lineNumber: '$decl'")
        val (typeText, stars, name) = match.destructured
        return CField(name, pointerize(parseTypeName(typeText, lineNumber), stars.length), doc)
    }

    private fun parseAlias(text: String): CAlias {
        val decl = text.removeSuffix(";").removePrefix("typedef").trim()

        val functionPointer = Regex("^.*\\(\\s*\\*\\s*(\\w+)\\s*\\)\\s*\\(").find(decl)
        if (functionPointer != null) {
            return CAlias(functionPointer.groupValues[1], CType.Pointer(CType.Void))
        }

        val match = Regex("^(.*?)\\s*(\\**)\\s*(\\w+)$").find(decl)
            ?: throw HeaderParseException("Unparseable typedef: '$text'")
        val (typeText, stars, name) = match.destructured
        return CAlias(name, pointerize(parseTypeName(typeText, 0), stars.length))
    }

    private fun parseFunction(text: String, doc: List<String>): CFunction {
        val decl = text.removeSuffix(";").trim()
        val open = decl.indexOf('(')
        if (open < 0) throw HeaderParseException("Unrecognised top-level declaration: '$text'")

        val signature = decl.substring(0, open).trim()
        val argText = decl.substring(open + 1, decl.lastIndexOf(')')).trim()

        val nameMatch = Regex("^(.*?)\\s*(\\**)\\s*(\\w+)$").find(signature)
            ?: throw HeaderParseException("Unparseable function signature: '$signature'")
        val (returnText, stars, name) = nameMatch.destructured

        val params = if (argText.isEmpty() || argText == "void") {
            emptyList()
        } else {
            splitParams(argText).mapIndexed { index, param -> parseParam(param, index) }
        }

        return CFunction(name, pointerize(parseTypeName(returnText, 0), stars.length), params, doc)
    }

    private fun splitParams(text: String): List<String> {
        val parts = mutableListOf<String>()
        var depth = 0
        val current = StringBuilder()
        text.forEach { ch ->
            when {
                ch == '(' -> { depth++; current.append(ch) }
                ch == ')' -> { depth--; current.append(ch) }
                ch == ',' && depth == 0 -> { parts += current.toString(); current.clear() }
                else -> current.append(ch)
            }
        }
        if (current.isNotBlank()) parts += current.toString()
        return parts.map { it.trim() }
    }

    private fun parseParam(text: String, index: Int): CParam {
        val match = Regex("^(.*?)\\s*(\\**)\\s*(\\w+)$").find(text)
            ?: throw HeaderParseException("Unparseable parameter: '$text'")
        val (typeText, stars, name) = match.destructured

        // A bare type with no parameter name, e.g. `lore_alloc_fn`.
        if (typeText.isBlank() && stars.isEmpty()) {
            return CParam("arg$index", parseTypeName(name, 0))
        }
        return CParam(name, pointerize(parseTypeName(typeText, 0), stars.length))
    }

    private fun pointerize(type: CType, depth: Int): CType =
        (0 until depth).fold(type) { acc, _ -> CType.Pointer(acc) }

    private fun parseTypeName(text: String, line: Int): CType {
        val name = text.trim().removePrefix("const ").trim().removeSuffix(" const").trim()
        return when {
            name == "void" -> CType.Void
            name.startsWith("struct ") -> CType.StructRef(name.removePrefix("struct ").trim())
            name.startsWith("union ") -> CType.StructRef(name.removePrefix("union ").trim())
            name.startsWith("enum ") -> CType.EnumRef(name.removePrefix("enum ").trim())
            name in PRIMITIVES -> CType.Primitive(name)
            name.startsWith("unsigned ") || name.startsWith("signed ") -> CType.Primitive(name)
            Regex("^\\w+$").matches(name) -> CType.Named(name)
            else -> throw HeaderParseException("Unrecognised type '$name'${if (line > 0) " at line $line" else ""}")
        }
    }
}
