package lore.codegen

data class LoreErrorCode(val code: Int, val name: String, val message: String)

/**
 * Reads the `#[ffi_code(N)]` registry out of lore-base/src/error.rs. These are
 * the codes a Complete event's status carries; lore.h exports only the narrower
 * lore_error_code_t, which is numbered independently.
 */
object ErrorCodeParser {

    // Some variants pass format arguments after the literal, so match only the
    // leading string rather than requiring the attribute to end right after it.
    private val ERROR_ATTRIBUTE = Regex("""^#\[error\("((?:[^"\\]|\\.)*)"""")
    private val FFI_CODE = Regex("""^#\[ffi_code\((\d+)\)]""")
    private val DECLARATION = Regex("""^pub (?:struct|enum) (\w+)""")

    fun parse(source: String): List<LoreErrorCode> {
        val codes = mutableListOf<LoreErrorCode>()
        var message: String? = null
        var code: Int? = null

        source.lineSequence().forEach { raw ->
            val line = raw.trim()
            ERROR_ATTRIBUTE.find(line)?.let { message = it.groupValues[1] }
            FFI_CODE.find(line)?.let { code = it.groupValues[1].toInt() }

            DECLARATION.find(line)?.let { declaration ->
                val pending = code
                if (pending != null) {
                    codes += LoreErrorCode(pending, declaration.groupValues[1], message.orEmpty())
                }
                code = null
                message = null
            }
        }

        if (codes.isEmpty()) error("No #[ffi_code(...)] entries found; the registry format has changed")

        val duplicates = codes.groupBy { it.code }.filterValues { it.size > 1 }
        if (duplicates.isNotEmpty()) {
            error("Duplicate ffi codes: ${duplicates.keys.sorted()}")
        }
        return codes.sortedBy { it.code }
    }
}

class ErrorCodeEmitter(private val interfaceVersion: String, private val codes: List<LoreErrorCode>) {

    fun emit(): String = buildString {
        appendLine(banner(interfaceVersion))
        appendLine("package $GENERATED_PACKAGE")
        appendLine()
        appendLine("/**")
        appendLine(" * Status codes carried by a Complete event and returned by operation")
        appendLine(" * functions. Distinct from [lore_error_code_t], which is the storage-layer")
        appendLine(" * enum in lore.h and is numbered independently.")
        appendLine(" */")
        appendLine("enum class LoreStatus(val code: Int, val message: String) {")
        appendLine("    SUCCESS(0, \"success\"),")
        codes.forEach { entry ->
            appendLine("    ${screamingSnake(entry.name)}(${entry.code}, \"${entry.message.replace("\"", "\\\"")}\"),")
        }
        appendLine("    ;")
        appendLine()
        appendLine("    companion object {")
        appendLine("        private val byCode = entries.associateBy { it.code }")
        appendLine()
        appendLine("        fun of(code: Int): LoreStatus? = byCode[code]")
        appendLine("    }")
        appendLine("}")
    }

    private fun screamingSnake(name: String): String =
        name.replace(Regex("([a-z0-9])([A-Z])"), "$1_$2").uppercase()
}
