package com.dzmitryj.lorelens.model

import com.dzmitryj.lorelens.ffi.generated.Metadata
import com.intellij.openapi.diagnostic.logger

private val log = logger<LoreMetadata>()

/**
 * Revision metadata, flattened to strings.
 *
 * Every variant is rendered rather than filtered: an earlier version kept only
 * StringValue, which silently discarded the numeric timestamp and the
 * cherry-picked-from hash. Metadata that cannot be rendered is logged, never
 * dropped in silence.
 */
@JvmInline
value class LoreMetadata(val values: Map<String, String>) {

    val message: String? get() = values[MESSAGE]?.takeIf { it.isNotBlank() }

    /** First line of the message. Empty messages exist in real repositories. */
    val subject: String? get() = message?.lineSequence()?.firstOrNull()?.trim()?.takeIf { it.isNotEmpty() }

    /** Everything after the subject, blank lines collapsed. */
    val body: String?
        get() = message
            ?.lineSequence()
            ?.drop(1)
            ?.joinToString("\n")
            ?.trim()
            ?.takeIf { it.isNotEmpty() }

    /** Who wrote the change. */
    val author: String? get() = values[CREATED_BY]

    /** Who committed it, which differs from [author] for a cherry-pick. */
    val committer: String? get() = values[COMMITTED_BY]

    val branch: String? get() = values[BRANCH]

    val cherryPickedFrom: String? get() = values[CHERRY_PICKED_FROM]

    val revertedFrom: String? get() = values[REVERTED_FROM]

    /** Epoch millis, or null when absent or unparseable. */
    val timestampMillis: Long? get() = values[TIMESTAMP]?.let(::parseTimestamp)

    companion object {
        const val MESSAGE = "message"
        const val TIMESTAMP = "timestamp"
        const val CREATED_BY = "created-by"
        const val COMMITTED_BY = "committed-by"
        const val BRANCH = "branch"
        const val CHERRY_PICKED_FROM = "cherry-picked-from"
        const val REVERTED_FROM = "reverted-from"

        val EMPTY = LoreMetadata(emptyMap())

        /** Renders any metadata variant to a string, or null if it carries no value. */
        fun render(key: String, value: Metadata): String? = when (value) {
            is Metadata.StringValue -> value.value
            is Metadata.NumericValue -> value.value.toString()
            is Metadata.BooleanValue -> (value.value.toInt() != 0).toString()
            is Metadata.HashValue -> value.value.toHex()
            is Metadata.ContextValue -> value.value.toHex()
            is Metadata.AddressValue -> "${value.value.hash.toHex()}-${value.value.context.toHex()}"
            is Metadata.BinaryValue -> null
            is Metadata.Unknown -> null.also {
                log.warn("Unrecognised metadata variant ${value.tag} for key '$key'")
            }
        }

        private fun ByteArray.toHex(): String = joinToString("") { "%02x".format(it) }

        /**
         * Lore writes timestamps as RFC 1123 text, but the metadata type may be
         * numeric epoch seconds or millis depending on the writer, so both are
         * accepted.
         */
        private fun parseTimestamp(raw: String): Long? {
            raw.toLongOrNull()?.let { return if (it > 100_000_000_000L) it else it * 1_000L }

            return runCatching {
                java.time.Instant.from(
                    java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.parse(raw),
                ).toEpochMilli()
            }.getOrNull()
        }
    }
}
