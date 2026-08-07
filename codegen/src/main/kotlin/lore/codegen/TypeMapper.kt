package lore.codegen

/**
 * Resolves C types to their 64-bit layout. All four platforms Lore ships are
 * LP64/LLP64 with natural alignment, so one table covers them; the C probe in CI
 * is what proves that assumption against a real compiler.
 */
class TypeMapper(private val header: CHeader) {

    private data class Scalar(val size: Long, val layout: String)

    private val scalars = mapOf(
        "bool" to Scalar(1, "ValueLayout.JAVA_BOOLEAN"),
        "char" to Scalar(1, "ValueLayout.JAVA_BYTE"),
        "int8_t" to Scalar(1, "ValueLayout.JAVA_BYTE"),
        "uint8_t" to Scalar(1, "ValueLayout.JAVA_BYTE"),
        "int16_t" to Scalar(2, "ValueLayout.JAVA_SHORT"),
        "uint16_t" to Scalar(2, "ValueLayout.JAVA_SHORT"),
        "int" to Scalar(4, "ValueLayout.JAVA_INT"),
        "int32_t" to Scalar(4, "ValueLayout.JAVA_INT"),
        "uint32_t" to Scalar(4, "ValueLayout.JAVA_INT"),
        "float" to Scalar(4, "ValueLayout.JAVA_FLOAT"),
        "int64_t" to Scalar(8, "ValueLayout.JAVA_LONG"),
        "uint64_t" to Scalar(8, "ValueLayout.JAVA_LONG"),
        "intptr_t" to Scalar(8, "ValueLayout.JAVA_LONG"),
        "uintptr_t" to Scalar(8, "ValueLayout.JAVA_LONG"),
        "size_t" to Scalar(8, "ValueLayout.JAVA_LONG"),
        "double" to Scalar(8, "ValueLayout.JAVA_DOUBLE"),
    )

    private val enumScalar = Scalar(4, "ValueLayout.JAVA_INT")
    private val pointerScalar = Scalar(8, "ValueLayout.ADDRESS")

    private val sizeCache = mutableMapOf<String, Long>()
    private val alignCache = mutableMapOf<String, Long>()

    /** Collapses aliases and enum references down to something with a concrete layout. */
    fun resolve(type: CType): CType = when (type) {
        is CType.Named -> {
            val alias = header.alias(type.name)
            when {
                alias != null -> resolve(alias.type)
                header.isEnum(type.name) -> CType.EnumRef(type.name)
                else -> CType.StructRef(type.name)
            }
        }
        else -> type
    }

    fun sizeOf(type: CType): Long = when (val t = resolve(type)) {
        is CType.Primitive -> scalar(t.name).size
        is CType.Pointer -> pointerScalar.size
        is CType.EnumRef -> enumScalar.size
        is CType.Array -> sizeOf(t.element) * t.length
        is CType.InlineUnion -> unionSize(t.fields)
        is CType.StructRef -> structSize(t.name)
        CType.Void -> error("void has no size")
        is CType.Named -> error("unreachable")
    }

    fun alignOf(type: CType): Long = when (val t = resolve(type)) {
        is CType.Primitive -> scalar(t.name).size
        is CType.Pointer -> pointerScalar.size
        is CType.EnumRef -> enumScalar.size
        is CType.Array -> alignOf(t.element)
        is CType.InlineUnion -> t.fields.maxOf { alignOf(it.type) }
        is CType.StructRef -> structAlign(t.name)
        CType.Void -> error("void has no alignment")
        is CType.Named -> error("unreachable")
    }

    /** Field offsets and the trailing padding needed to reach the struct's own size. */
    fun layoutOf(fields: List<CField>): StructPlan {
        var offset = 0L
        val entries = mutableListOf<PlanEntry>()

        fields.forEach { field ->
            val align = alignOf(field.type)
            val padding = (align - offset % align) % align
            if (padding > 0) {
                entries += PlanEntry.Padding(padding)
                offset += padding
            }
            entries += PlanEntry.Member(field, offset)
            offset += sizeOf(field.type)
        }

        val structAlign = fields.maxOfOrNull { alignOf(it.type) } ?: 1L
        val tail = (structAlign - offset % structAlign) % structAlign
        if (tail > 0) {
            entries += PlanEntry.Padding(tail)
            offset += tail
        }

        return StructPlan(entries, size = offset, alignment = structAlign)
    }

    fun layoutExpression(type: CType): String = when (val t = resolve(type)) {
        is CType.Primitive -> scalar(t.name).layout
        is CType.Pointer -> pointerScalar.layout
        is CType.EnumRef -> enumScalar.layout
        is CType.StructRef -> "${objectName(t.name)}.LAYOUT"
        is CType.Array -> "MemoryLayout.sequenceLayout(${t.length}, ${layoutExpression(t.element)})"
        is CType.InlineUnion -> unionExpression(t.fields)
        CType.Void -> error("void has no layout")
        is CType.Named -> error("unreachable")
    }

    /** The Kotlin type a VarHandle for this field reads and writes. */
    fun carrierType(type: CType): String? = when (val t = resolve(type)) {
        is CType.Primitive -> when (scalar(t.name).layout) {
            "ValueLayout.JAVA_BOOLEAN" -> "Boolean"
            "ValueLayout.JAVA_BYTE" -> "Byte"
            "ValueLayout.JAVA_SHORT" -> "Short"
            "ValueLayout.JAVA_INT" -> "Int"
            "ValueLayout.JAVA_LONG" -> "Long"
            "ValueLayout.JAVA_FLOAT" -> "Float"
            "ValueLayout.JAVA_DOUBLE" -> "Double"
            else -> null
        }
        is CType.Pointer -> "MemorySegment"
        is CType.EnumRef -> "Int"
        else -> null
    }

    private fun unionExpression(fields: List<CField>): String {
        val members = fields.joinToString(",\n") { field ->
            val padded = padUnionMember(field, unionSize(fields))
            "        $padded"
        }
        return "MemoryLayout.unionLayout(\n$members,\n    )"
    }

    // FFM requires every union member to be exactly the union's size, so short
    // members are wrapped with explicit trailing padding.
    private fun padUnionMember(field: CField, unionSize: Long): String {
        val expression = layoutExpression(field.type)
        val size = sizeOf(field.type)
        val named = "$expression.withName(\"${field.name}\")"
        return if (size == unionSize) {
            named
        } else {
            "MemoryLayout.structLayout($named, MemoryLayout.paddingLayout(${unionSize - size}))" +
                ".withName(\"${field.name}\")"
        }
    }

    private fun unionSize(fields: List<CField>): Long {
        val align = fields.maxOf { alignOf(it.type) }
        val largest = fields.maxOf { sizeOf(it.type) }
        return largest + (align - largest % align) % align
    }

    private fun scalar(name: String): Scalar =
        scalars[name] ?: error("No layout for primitive '$name'")

    private fun structSize(name: String): Long = sizeCache.getOrPut(name) {
        layoutOf(header.struct(name).fields).size
    }

    private fun structAlign(name: String): Long = alignCache.getOrPut(name) {
        layoutOf(header.struct(name).fields).alignment
    }

    companion object {
        fun objectName(cName: String): String = cName
    }
}

sealed interface PlanEntry {
    data class Member(val field: CField, val offset: Long) : PlanEntry
    data class Padding(val bytes: Long) : PlanEntry
}

data class StructPlan(val entries: List<PlanEntry>, val size: Long, val alignment: Long) {
    val members: List<PlanEntry.Member> get() = entries.filterIsInstance<PlanEntry.Member>()
}
