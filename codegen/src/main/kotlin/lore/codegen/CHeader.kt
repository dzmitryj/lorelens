package lore.codegen

sealed interface CType {
    data class Primitive(val name: String) : CType
    data class Pointer(val target: CType) : CType
    data class StructRef(val name: String) : CType
    data class EnumRef(val name: String) : CType
    data class Named(val name: String) : CType
    data class Array(val element: CType, val length: Int) : CType
    data class InlineUnion(val fields: List<CField>) : CType
    data object Void : CType
}

data class CField(val name: String, val type: CType, val doc: List<String> = emptyList())

sealed interface CDecl {
    val name: String
}

data class CDefine(override val name: String, val value: String) : CDecl

data class CEnumConstant(val name: String, val value: Long, val doc: List<String> = emptyList())

data class CEnum(
    override val name: String,
    val constants: List<CEnumConstant>,
    val doc: List<String> = emptyList(),
) : CDecl

data class CStruct(
    override val name: String,
    val fields: List<CField>,
    val opaque: Boolean = false,
    val doc: List<String> = emptyList(),
) : CDecl

data class CParam(val name: String, val type: CType)

data class CFunction(
    override val name: String,
    val returnType: CType,
    val params: List<CParam>,
    val doc: List<String> = emptyList(),
) : CDecl

/** `typedef uint32_t lore_node_id_t;` and function-pointer typedefs, which are opaque addresses to us. */
data class CAlias(override val name: String, val type: CType) : CDecl

data class CHeader(
    val defines: List<CDefine>,
    val enums: List<CEnum>,
    val structs: List<CStruct>,
    val aliases: List<CAlias>,
    val functions: List<CFunction>,
) {
    val interfaceVersion: String
        get() = defines.first { it.name == "LORE_INTERFACE_VERSION" }.value.trim('"')

    private val structsByName = structs.associateBy { it.name }
    private val enumNames = enums.mapTo(mutableSetOf()) { it.name }
    private val aliasesByName = aliases.associateBy { it.name }

    fun struct(name: String): CStruct =
        structsByName[name] ?: error("Unknown struct '$name'")

    fun isEnum(name: String): Boolean = name in enumNames

    fun alias(name: String): CAlias? = aliasesByName[name]
}

class HeaderParseException(message: String) : RuntimeException(message)
