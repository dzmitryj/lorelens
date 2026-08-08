package com.dzmitryj.lorelens.model

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

/** Unified diff text for one file. */
data class LoreFilePatch(val path: String, val patch: String, val action: LoreFileAction)

/**
 * One file changed between two revisions. Carries no patch text, but does
 * carry both sides' content addresses: content is only reachable by address
 * once a file has been moved or deleted, and null marks the side that does
 * not exist.
 */
data class LoreRevisionChange(
    val path: String,
    val action: LoreFileAction,
    val oldAddress: String? = null,
    val newAddress: String? = null,
)

/**
 * One appearance of a file in history. [action] is exact rather than inferred:
 * Lore records MOVE and COPY rather than leaving them to rename detection.
 */
data class LoreHistoryRecord(
    val path: String,
    val revision: LoreRevisionId,
    val number: Long,
    val size: Long,
    val action: LoreFileAction,
    val metadata: LoreMetadata,
    /** Content address. Equal addresses mean identical bytes, so a diff between
     *  two revisions sharing one is provably empty. */
    val address: String,
) {
    val message: String? get() = metadata.message
    val subject: String? get() = metadata.subject
    val author: String? get() = metadata.author
    val timestampMillis: Long? get() = metadata.timestampMillis
}

enum class LoreBranchLocation {
    LOCAL,
    REMOTE,
    ;

    companion object {
        fun of(value: Int): LoreBranchLocation = if (value == 1) REMOTE else LOCAL
    }
}

/** Where a branch was cut: the branch it came from, and the revision. */
data class LoreBranchPoint(val branch: LoreBranchId, val revision: LoreRevisionId)

/** Opaque 16-byte branch identifier. */
@JvmInline
value class LoreBranchId(val bytes: ByteArray) {
    val hex: String get() = bytes.joinToString("") { "%02x".format(it) }

    val isNone: Boolean get() = bytes.all { it.toInt() == 0 }

    override fun toString(): String = if (isNone) "none" else hex.take(12)
}

data class LoreBranch(
    val id: LoreBranchId,
    val name: String,
    val category: String,
    val location: LoreBranchLocation,
    val latest: LoreRevisionId,
    val creator: String,
    val createdMillis: Long,
    val isCurrent: Boolean,
    val isArchived: Boolean,
    /**
     * Where this branch was cut, nearest first. Carries the parent branch as
     * well as the revision, which is what makes the hierarchy knowable.
     */
    val branchPoints: List<LoreBranchPoint>,
) {
    /** The branch this one was cut from, if it is in the listing. */
    val parentBranch: LoreBranchId? get() = branchPoints.firstOrNull()?.branch?.takeIf { !it.isNone }
}

/** What a merge would touch, and where it would stop. */
data class LoreMergePreview(val changed: List<String>, val conflicted: List<String>) {
    val isClean: Boolean get() = conflicted.isEmpty()
}
