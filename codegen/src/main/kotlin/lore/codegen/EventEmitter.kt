package lore.codegen

private const val EVENT_STRUCT = "lore_event_t"
private const val EVENT_ENUM = "lore_event_id_t"
private const val STRING_STRUCT = "lore_string_t"
private const val BYTES_STRUCT = "lore_bytes_t"

/**
 * Emits the tagged-union dispatch: one Kotlin type per event arm, plus data
 * classes for every struct reachable from a payload, all built by copying.
 */
class EventEmitter(private val header: CHeader, private val types: TypeMapper) {

    private val event = header.struct(EVENT_STRUCT)
    private val tags = header.enums.first { it.name == EVENT_ENUM }
    private val arms = event.fields.single { it.type is CType.InlineUnion }
        .let { (it.type as CType.InlineUnion).fields }

    private val payloadStructs = linkedSetOf<String>()

    fun emit(): String {
        val armsByName = arms.associateBy { it.name }
        val ordered = tags.constants.map { constant ->
            val member = memberName(constant.name)
            constant to (armsByName[member] ?: error("No union member '$member' for ${constant.name}"))
        }

        ordered.forEach { (_, field) -> collectPayload(field.type) }

        val classes = payloadStructs.map { name -> dataClass(header.struct(name)) }

        return buildString {
            appendLine(banner(header.interfaceVersion))
            appendLine("package $GENERATED_PACKAGE")
            appendLine()
            appendLine("import com.dzmitryj.lorevcs.ffi.LoreCopy")
            appendLine("import java.lang.foreign.MemorySegment")
            appendLine("import java.lang.foreign.ValueLayout")
            appendLine()
            appendLine("sealed interface LoreEvent")
            appendLine()
            appendLine("data class UnknownEvent(val tag: Int) : LoreEvent")
            appendLine()

            ordered.forEach { (constant, field) ->
                append(kdocOf(constant.doc))
                appendLine(
                    dataClassBody(
                        name = eventClassName(constant.name),
                        struct = header.struct(structName(field.type)),
                        superType = " : LoreEvent",
                    )
                )
            }

            classes.forEach { appendLine(it) }

            appendLine("object LoreEventReader {")
            appendLine()
            appendLine("    fun read(event: MemorySegment): LoreEvent {")
            appendLine("        val payload = $EVENT_STRUCT.union(event)")
            appendLine("        return when ($EVENT_STRUCT.tag(event)) {")
            ordered.forEach { (constant, field) ->
                val payloadStruct = structName(field.type)
                appendLine(
                    "            ${constant.value} -> ${eventClassName(constant.name)}(" +
                        "${readArguments(header.struct(payloadStruct), "payload.asSlice(0L, $payloadStruct.SIZE)")})"
                )
            }
            appendLine("            else -> UnknownEvent($EVENT_STRUCT.tag(event))")
            appendLine("        }")
            appendLine("    }")
            appendLine("}")
        }
    }

    private fun kdocOf(doc: List<String>): String =
        if (doc.isEmpty()) "" else "/**\n" + doc.joinToString("\n") { " * $it" } + "\n */\n"

    private fun collectPayload(type: CType) {
        val name = structName(type)
        collectStruct(name)
    }

    private fun collectStruct(name: String) {
        val struct = header.struct(name)
        struct.fields.forEach { field ->
            val resolved = types.resolve(field.type)
            if (resolved is CType.StructRef && !isSpecial(resolved.name)) {
                if (payloadStructs.add(resolved.name)) collectStruct(resolved.name)
            }
            arrayElement(resolved)?.let { element ->
                if (element is CType.StructRef && !isSpecial(element.name)) {
                    if (payloadStructs.add(element.name)) collectStruct(element.name)
                }
            }
        }
    }

    private fun isSpecial(name: String): Boolean =
        name == STRING_STRUCT || name == BYTES_STRUCT || isArrayStruct(name) || isFixedByteArray(name)

    private fun isArrayStruct(name: String): Boolean {
        val struct = header.structs.firstOrNull { it.name == name } ?: return false
        if (struct.fields.size != 2) return false
        val (first, second) = struct.fields
        return first.name == "ptr" && types.resolve(first.type) is CType.Pointer && second.name == "count"
    }

    private fun isFixedByteArray(name: String): Boolean {
        val struct = header.structs.firstOrNull { it.name == name } ?: return false
        val only = struct.fields.singleOrNull() ?: return false
        val type = only.type
        return type is CType.Array && types.sizeOf(type.element) == 1L
    }

    /** For an array struct, the element type its `ptr` points at. */
    private fun arrayElement(type: CType): CType? {
        if (type !is CType.StructRef || !isArrayStruct(type.name)) return null
        val pointer = types.resolve(header.struct(type.name).fields[0].type) as CType.Pointer
        return types.resolve(pointer.target)
    }

    private fun structName(type: CType): String = when (val resolved = types.resolve(type)) {
        is CType.StructRef -> resolved.name
        else -> error("Expected a struct, got $resolved")
    }

    private fun dataClass(struct: CStruct): String =
        dataClassBody(payloadClassName(struct.name), struct, superType = "")

    private fun dataClassBody(name: String, struct: CStruct, superType: String): String {
        val properties = struct.fields.mapNotNull { field ->
            kotlinType(field.type)?.let { type -> "    val ${safe(field.name)}: $type," }
        }
        return if (properties.isEmpty()) {
            "class $name$superType\n"
        } else {
            "data class $name(\n${properties.joinToString("\n")}\n)$superType\n"
        }
    }

    private fun readArguments(struct: CStruct, accessor: String): String =
        struct.fields.mapNotNull { field ->
            kotlinType(field.type)?.let { _ ->
                "${safe(field.name)} = ${readExpression(field, "${struct.name}.${safe(field.name)}($accessor)")}"
            }
        }.joinToString(", ")

    private fun kotlinType(type: CType): String? {
        types.carrierType(type)?.let { carrier ->
            return if (carrier == "MemorySegment") null else carrier
        }
        val resolved = types.resolve(type)
        if (resolved !is CType.StructRef) return null

        return when {
            resolved.name == STRING_STRUCT -> "String"
            resolved.name == BYTES_STRUCT -> "ByteArray"
            isFixedByteArray(resolved.name) -> "ByteArray"
            isArrayStruct(resolved.name) -> {
                val element = arrayElement(resolved) ?: return null
                elementKotlinType(element)?.let { "List<$it>" }
            }
            else -> payloadClassName(resolved.name)
        }
    }

    private fun elementKotlinType(element: CType): String? = when {
        element is CType.StructRef && element.name == STRING_STRUCT -> "String"
        element is CType.StructRef && isFixedByteArray(element.name) -> "ByteArray"
        element is CType.StructRef -> payloadClassName(element.name)
        else -> types.carrierType(element)?.takeIf { it != "MemorySegment" }
    }

    private fun readExpression(field: CField, accessor: String): String {
        val resolved = types.resolve(field.type)
        if (types.carrierType(field.type) != null) return accessor

        resolved as CType.StructRef
        return when {
            resolved.name == STRING_STRUCT -> "LoreCopy.string($accessor)"
            resolved.name == BYTES_STRUCT -> "LoreCopy.bytes($accessor, ${BYTES_STRUCT}.len($accessor))"
            isFixedByteArray(resolved.name) -> {
                val inner = header.struct(resolved.name).fields.single()
                "LoreCopy.fixedBytes(${resolved.name}.${safe(inner.name)}($accessor))"
            }
            isArrayStruct(resolved.name) -> {
                val element = arrayElement(resolved)!!
                "LoreCopy.array($accessor, ${types.sizeOf(element)}L) { ${elementReader(element)} }"
            }
            else -> "${payloadClassName(resolved.name)}(${readArguments(header.struct(resolved.name), accessor)})"
        }
    }

    private fun elementReader(element: CType): String = when {
        element is CType.StructRef && element.name == STRING_STRUCT -> "LoreCopy.string(it)"
        element is CType.StructRef && isFixedByteArray(element.name) -> {
            val inner = header.struct(element.name).fields.single()
            "LoreCopy.fixedBytes(${element.name}.${safe(inner.name)}(it))"
        }
        element is CType.StructRef ->
            "${payloadClassName(element.name)}(${readArguments(header.struct(element.name), "it")})"
        else -> "it.get(${types.layoutExpression(element)}, 0L)"
    }

    private fun memberName(constantName: String): String =
        constantName.removePrefix("LORE_EVENT_").lowercase()

    private fun eventClassName(constantName: String): String =
        pascal(constantName.removePrefix("LORE_EVENT_").lowercase()) + "Event"

    private fun payloadClassName(structName: String): String =
        pascal(structName.removePrefix("lore_").removeSuffix("_t"))

    private fun pascal(snake: String): String =
        snake.split('_').filter { it.isNotEmpty() }.joinToString("") { part ->
            part.replaceFirstChar { it.uppercase() }
        }
}
