package lore.codegen

const val GENERATED_PACKAGE = "com.dzmitryj.lorevcs.ffi.generated"

private val KOTLIN_KEYWORDS = setOf(
    "as", "break", "class", "continue", "do", "else", "false", "for", "fun", "if", "in",
    "interface", "is", "null", "object", "package", "return", "super", "this", "throw",
    "true", "try", "typealias", "typeof", "val", "var", "when", "while",
)

fun safe(name: String): String = if (name in KOTLIN_KEYWORDS) "`$name`" else name

fun banner(headerVersion: String): String = """
    // Generated from lore.h $headerVersion by :codegen. Do not edit.
""".trimIndent()

private fun kdoc(doc: List<String>, indent: String): String {
    if (doc.isEmpty()) return ""
    val body = doc.joinToString("\n") { "$indent * $it" }
    return "$indent/**\n$body\n$indent */\n"
}

class EnumEmitter(private val header: CHeader) {

    fun emit(): String = buildString {
        appendLine(banner(header.interfaceVersion))
        appendLine("package $GENERATED_PACKAGE")
        appendLine()

        header.enums.sortedBy { it.name }.forEach { enum ->
            append(kdoc(enum.doc, ""))
            appendLine("object ${safe(enum.name)} {")
            enum.constants.forEach { constant ->
                append(kdoc(constant.doc, "    "))
                appendLine("    const val ${safe(constant.name)}: Int = ${constant.value}")
            }
            appendLine("}")
            appendLine()
        }
    }
}

class LayoutEmitter(private val header: CHeader, private val types: TypeMapper) {

    fun emit(): String = buildString {
        appendLine(banner(header.interfaceVersion))
        appendLine("package $GENERATED_PACKAGE")
        appendLine()
        appendLine("import java.lang.foreign.MemoryLayout")
        appendLine("import java.lang.foreign.MemorySegment")
        appendLine("import java.lang.foreign.StructLayout")
        appendLine("import java.lang.foreign.ValueLayout")
        appendLine()

        val concrete = header.structs.filterNot { it.opaque }.sortedBy { it.name }
        concrete.forEach { struct -> append(emitStruct(struct)) }

        appendLine("/** Every generated layout, so a test can force-initialise them all. */")
        appendLine("object AllLayouts {")
        appendLine("    val layouts: List<StructLayout> = listOf(")
        concrete.forEach { appendLine("        ${safe(it.name)}.LAYOUT,") }
        appendLine("    )")
        appendLine("}")
    }

    private fun emitStruct(struct: CStruct): String = buildString {
        val plan = types.layoutOf(struct.fields)

        append(kdoc(struct.doc, ""))
        appendLine("object ${safe(struct.name)} {")
        appendLine("    const val SIZE: Long = ${plan.size}L")
        appendLine("    const val ALIGNMENT: Long = ${plan.alignment}L")
        appendLine()
        appendLine("    val LAYOUT: StructLayout = MemoryLayout.structLayout(")
        plan.entries.forEach { entry ->
            when (entry) {
                is PlanEntry.Padding -> appendLine("        MemoryLayout.paddingLayout(${entry.bytes}),")
                is PlanEntry.Member -> {
                    val name = entry.field.name.ifEmpty { "union" }
                    appendLine("        ${types.layoutExpression(entry.field.type)}.withName(\"$name\"),")
                }
            }
        }
        appendLine("    ).withName(\"${struct.name}\") as StructLayout")

        plan.members.forEach { member ->
            appendLine()
            append(emitAccessors(member))
        }

        appendLine("}")
        appendLine()
    }

    private fun emitAccessors(member: PlanEntry.Member): String = buildString {
        val field = member.field
        val name = field.name.ifEmpty { "union" }
        val offset = member.offset
        val carrier = types.carrierType(field.type)

        append(kdoc(field.doc, "    "))
        appendLine("    const val OFFSET_$name: Long = ${offset}L")

        if (carrier != null) {
            val layout = types.layoutExpression(field.type)
            appendLine("    fun ${safe(name)}(struct: MemorySegment): $carrier =")
            appendLine("        struct.get($layout, OFFSET_$name)")
            appendLine("    fun ${safe(name)}(struct: MemorySegment, value: $carrier) {")
            appendLine("        struct.set($layout, OFFSET_$name, value)")
            appendLine("    }")
        } else {
            val size = types.sizeOf(field.type)
            appendLine("    fun ${safe(name)}(struct: MemorySegment): MemorySegment =")
            appendLine("        struct.asSlice(OFFSET_$name, ${size}L)")
        }
    }
}

class FunctionEmitter(private val header: CHeader, private val types: TypeMapper) {

    fun emit(): String = buildString {
        appendLine(banner(header.interfaceVersion))
        appendLine("package $GENERATED_PACKAGE")
        appendLine()
        appendLine("import com.dzmitryj.lorevcs.ffi.LoreLinker")
        appendLine("import java.lang.foreign.FunctionDescriptor")
        appendLine("import java.lang.foreign.MemoryLayout")
        appendLine("import java.lang.foreign.ValueLayout")
        appendLine("import java.lang.invoke.MethodHandle")
        appendLine()
        appendLine("/**")
        appendLine(" * Handles bind lazily so a symbol missing from the loaded library fails at the")
        appendLine(" * call site that needs it rather than at load time.")
        appendLine(" */")
        appendLine("object LoreFunctions {")

        header.functions.sortedBy { it.name }.forEach { function ->
            appendLine()
            append(kdoc(function.doc, "    "))
            appendLine("    val ${safe(function.name)}: MethodHandle by lazy {")
            appendLine("        LoreLinker.downcall(")
            appendLine("            \"${function.name}\",")
            appendLine("            ${descriptor(function)},")
            appendLine("        )")
            appendLine("    }")
        }

        appendLine("}")
    }

    private fun descriptor(function: CFunction): String {
        val params = function.params.joinToString(", ") { types.layoutExpression(it.type) }
        return if (function.returnType == CType.Void) {
            "FunctionDescriptor.ofVoid($params)"
        } else {
            val returns = types.layoutExpression(function.returnType)
            if (params.isEmpty()) "FunctionDescriptor.of($returns)" else "FunctionDescriptor.of($returns, $params)"
        }
    }
}
