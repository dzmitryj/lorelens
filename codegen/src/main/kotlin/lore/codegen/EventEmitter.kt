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
    private val taggedUnions = linkedSetOf<String>()

    /**
     * Every field this emitter could not represent, as "struct.field: ctype".
     *
     * Fields are skipped rather than failed because some genuinely carry
     * nothing worth exposing -- raw pointers, `_unused` placeholders. The
     * problem is that a skip is invisible: metadata values disappeared this way
     * once, and revision parents a second time. Main writes this list out and a
     * test pins it, so a new skip fails the build instead of going unnoticed.
     */
    val droppedFields = sortedSetOf<String>()

    /**
     * A struct of exactly a tag plus an anonymous union, like lore_metadata_t.
     * Without this the union member is dropped and the value silently
     * disappears, which is how metadata values were lost before.
     */
    private fun isTaggedUnion(struct: CStruct): Boolean =
        struct.name != EVENT_STRUCT &&
            struct.fields.size == 2 &&
            struct.fields[1].type is CType.InlineUnion &&
            types.resolve(struct.fields[0].type) is CType.EnumRef

    private fun tagEnumOf(struct: CStruct): CEnum {
        val ref = types.resolve(struct.fields[0].type) as CType.EnumRef
        return header.enums.first { it.name == ref.name }
    }

    /** Pairs each union member with the tag constant whose name ends in it. */
    private fun armsOf(struct: CStruct): List<Pair<CEnumConstant, CField>> {
        val constants = tagEnumOf(struct).constants
        val members = (struct.fields[1].type as CType.InlineUnion).fields

        return members.map { member ->
            val constant = constants.firstOrNull {
                it.name.lowercase().endsWith("_${member.name.lowercase()}")
            } ?: error("No tag constant for union member '${member.name}' of ${struct.name}")
            constant to member
        }
    }

    private fun emitTaggedUnion(struct: CStruct): String = buildString {
        val name = payloadClassName(struct.name)
        val arms = armsOf(struct)

        val tag = "${struct.name}.${safe(struct.fields[0].name)}(struct)"

        append(kdocOf(struct.doc))
        appendLine("sealed interface $name {")
        appendLine("    data class Unknown(val tag: Int) : $name")
        arms.forEach { (_, member) ->
            // Suffixed so an arm cannot shadow the payload class it wraps, or a
            // Kotlin built-in like String.
            val armName = "${pascal(member.name)}Value"
            val type = armKotlinType(member) ?: return@forEach
            appendLine("    data class $armName(val value: $type) : $name")
        }
        appendLine("}")
        appendLine()

        appendLine("private fun read$name(struct: MemorySegment): $name =")
        appendLine("    when ($tag) {")
        arms.forEach { (constant, member) ->
            val armName = "${pascal(member.name)}Value"
            if (armKotlinType(member) == null) return@forEach
            appendLine("        ${constant.value} -> $name.$armName(${armRead(struct, member)})")
        }
        appendLine("        else -> $name.Unknown($tag)")
        appendLine("    }")
        appendLine()
    }

    private fun armKotlinType(member: CField): String? =
        types.carrierType(member.type)?.takeIf { it != "MemorySegment" } ?: kotlinType(member.type)

    /** A union arm starts at the union's own offset, so scalars read at zero. */
    private fun armRead(struct: CStruct, member: CField): String {
        val union = "${struct.name}.union(struct)"
        val carrier = types.carrierType(member.type)

        return if (carrier != null && carrier != "MemorySegment") {
            "$union.get(${types.layoutExpression(member.type)}, 0L)"
        } else {
            readExpression(member, "$union.asSlice(0L, ${types.sizeOf(member.type)}L)")
        }
    }

    fun emit(): String {
        val armsByName = arms.associateBy { it.name }
        val ordered = tags.constants.map { constant ->
            val member = memberName(constant.name)
            constant to (armsByName[member] ?: error("No union member '$member' for ${constant.name}"))
        }

        ordered.forEach { (_, field) -> collectPayload(field.type) }

        val classes = payloadStructs.map { name -> dataClass(header.struct(name)) } +
            taggedUnions.map { name -> emitTaggedUnion(header.struct(name)) }

        return buildString {
            appendLine(banner(header.interfaceVersion))
            appendLine("package $GENERATED_PACKAGE")
            appendLine()
            appendLine("import com.dzmitryj.lorelens.ffi.LoreCopy")
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
        val fields = if (isTaggedUnion(struct)) {
            (struct.fields[1].type as CType.InlineUnion).fields
        } else {
            struct.fields
        }

        fields.forEach { field ->
            val resolved = types.resolve(field.type)
            collectType(resolved)
            arrayElement(resolved)?.let(::collectType)
        }
    }

    private fun collectType(type: CType) {
        if (type !is CType.StructRef || isSpecial(type.name)) return

        val struct = header.structs.firstOrNull { it.name == type.name } ?: return
        if (isTaggedUnion(struct)) {
            if (taggedUnions.add(type.name)) collectStruct(type.name)
            return
        }
        if (payloadStructs.add(type.name)) collectStruct(type.name)
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
            val type = kotlinType(field.type)
            if (type == null) {
                droppedFields += "${struct.name}.${field.name}: ${describe(field.type)}"
                return@mapNotNull null
            }
            "    val ${safe(field.name)}: $type,"
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

        // A fixed-length array declared inline, such as `lore_hash_t parent[2]`.
        // Distinct from the pointer-and-count array structs handled below.
        if (resolved is CType.Array) {
            return elementKotlinType(resolved.element)?.let { "List<$it>" }
        }
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

    private fun isTaggedUnionRef(type: CType): Boolean {
        val resolved = types.resolve(type)
        return resolved is CType.StructRef &&
            header.structs.firstOrNull { it.name == resolved.name }?.let { isTaggedUnion(it) } == true
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

        if (resolved is CType.Array) {
            val size = types.sizeOf(resolved.element)
            return "LoreCopy.inlineArray($accessor, ${resolved.length}, ${size}L) { ${elementReader(resolved.element)} }"
        }

        resolved as CType.StructRef
        return when {
            isTaggedUnionRef(resolved) -> "read${payloadClassName(resolved.name)}($accessor)"
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

    private fun describe(type: CType): String = when (type) {
        is CType.StructRef -> type.name
        is CType.EnumRef -> type.name
        is CType.Array -> "${describe(type.element)}[${type.length}]"
        is CType.Pointer -> "${describe(type.target)}*"
        is CType.InlineUnion -> "union"
        else -> type.toString()
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
