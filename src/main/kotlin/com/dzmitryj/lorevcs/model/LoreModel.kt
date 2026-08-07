package com.dzmitryj.lorevcs.model

enum class LoreFileAction {
    KEEP, ADD, DELETE, MOVE, COPY, UNKNOWN;

    companion object {
        fun of(raw: Int): LoreFileAction = when (raw) {
            0 -> KEEP
            1 -> ADD
            2 -> DELETE
            3 -> MOVE
            4 -> COPY
            else -> UNKNOWN
        }
    }
}

enum class LoreNodeType {
    DIRECTORY, FILE, LINK, UNKNOWN;

    companion object {
        fun of(raw: Int): LoreNodeType = when (raw) {
            0 -> DIRECTORY
            1 -> FILE
            2 -> LINK
            else -> UNKNOWN
        }
    }
}

/** A revision hash. Zero-valued hashes mean "none" in Lore's status payloads. */
@JvmInline
value class LoreRevisionId(val bytes: ByteArray) {

    val isNone: Boolean get() = bytes.all { it.toInt() == 0 }

    val short: String get() = hex.take(12)

    val hex: String get() = bytes.joinToString("") { "%02x".format(it) }

    override fun toString(): String = if (isNone) "none" else short
}

data class LoreFileStatus(
    val path: String,
    val size: Long,
    val action: LoreFileAction,
    val nodeType: LoreNodeType,
    val staged: Boolean,
    val dirty: Boolean,
    val conflicted: Boolean,
    val conflictUnresolved: Boolean,
    val fromPath: String?,
) {
    /**
     * Lore reports an edit as KEEP with the dirty flag set; ADD, DELETE, MOVE
     * and COPY describe what happens to the path itself.
     */
    val isModified: Boolean get() = action == LoreFileAction.KEEP && dirty
}

data class LoreRevisionStatus(
    val branchName: String,
    val revision: LoreRevisionId,
    val revisionNumber: Long,
    val stagedRevision: LoreRevisionId,
    val localAhead: Boolean,
    val remoteAhead: Boolean,
    val remoteAvailable: Boolean,
)

data class LoreRepositoryStatus(
    val revision: LoreRevisionStatus?,
    val files: List<LoreFileStatus>,
)

data class LoreFileHash(val path: String, val size: Long, val hash: LoreRevisionId)

/** [path] is repository-relative; [owner] is a Lore user id. */
data class LoreLock(val path: String, val owner: String, val lockedAt: Long)
