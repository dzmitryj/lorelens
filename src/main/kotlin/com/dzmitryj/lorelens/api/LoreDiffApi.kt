package com.dzmitryj.lorelens.api

import com.dzmitryj.lorelens.ffi.EventPump
import com.dzmitryj.lorelens.ffi.LoreArgs
import com.dzmitryj.lorelens.ffi.generated.FileDiffEvent
import com.dzmitryj.lorelens.ffi.generated.FileHistoryEvent
import com.dzmitryj.lorelens.ffi.generated.LoreFunctions
import com.dzmitryj.lorelens.ffi.generated.RevisionDiffFileEvent
import com.dzmitryj.lorelens.ffi.generated.lore_file_diff_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_file_history_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_revision_diff_args_t
import com.dzmitryj.lorelens.model.LoreFileAction
import com.dzmitryj.lorelens.model.LoreFilePatch
import com.dzmitryj.lorelens.model.LoreHistoryRecord
import com.dzmitryj.lorelens.model.LoreMetadata
import com.dzmitryj.lorelens.model.LoreRevisionChange
import com.dzmitryj.lorelens.model.LoreRevisionId
import java.lang.foreign.Arena
import java.nio.file.Path

/**
 * Diff and per-file history.
 *
 * Only lore_file_diff produces text: lore_revision_diff reports which files
 * changed but carries no patch. So the shape of every revision-level feature is
 * "revision diff for the file list, then file diff per path".
 */
object LoreDiffApi {

    const val DEFAULT_CONTEXT_LINES = 3

    /**
     * Unified diff text for [paths].
     *
     * Both revisions default to empty, which diffs the current revision against
     * the working tree. Supplying both diffs two committed revisions without
     * touching the working tree at all.
     */
    fun fileDiff(
        root: Path,
        paths: List<String>,
        sourceRevision: String = "",
        targetRevision: String = "",
        contextLines: Int = DEFAULT_CONTEXT_LINES,
        ignoreWhitespace: Boolean = false,
    ): List<LoreFilePatch> {
        if (paths.isEmpty()) return emptyList()

        return Arena.ofConfined().use { arena ->
            val args = LoreArgs(arena)
            val globals = args.globals(root)
            val options = arena.allocate(lore_file_diff_args_t.LAYOUT)

            args.writeStrings(lore_file_diff_args_t.paths(options), paths.map { root.resolve(it).toString() })
            if (sourceRevision.isNotEmpty()) {
                args.writeString(lore_file_diff_args_t.source_revision(options), sourceRevision)
            }
            if (targetRevision.isNotEmpty()) {
                args.writeString(lore_file_diff_args_t.target_revision(options), targetRevision)
            }
            lore_file_diff_args_t.context_lines(options, contextLines)
            if (ignoreWhitespace) {
                lore_file_diff_args_t.ignore_whitespace_eol(options, 1)
                lore_file_diff_args_t.ignore_whitespace_inline(options, 1)
            }

            LoreClient.require(
                EventPump.call(arena) { callback ->
                    LoreFunctions.lore_file_diff.invokeExact(globals, options, callback) as Int
                },
                "diff",
            ).filter<FileDiffEvent>().map {
                LoreFilePatch(relative(root, it.path), it.patch, LoreFileAction.of(it.action))
            }
        }
    }

    /** The files that changed between two revisions. No patch text; see [fileDiff]. */
    fun revisionDiff(root: Path, source: String, target: String = ""): List<LoreRevisionChange> =
        Arena.ofConfined().use { arena ->
            val args = LoreArgs(arena)
            val globals = args.globals(root)
            val options = arena.allocate(lore_revision_diff_args_t.LAYOUT)
            args.writeString(lore_revision_diff_args_t.revision_source(options), source)
            if (target.isNotEmpty()) {
                args.writeString(lore_revision_diff_args_t.revision_target(options), target)
            }

            LoreClient.require(
                EventPump.call(arena) { callback ->
                    LoreFunctions.lore_revision_diff.invokeExact(globals, options, callback) as Int
                },
                "revision diff",
            ).filter<RevisionDiffFileEvent>().map {
                LoreRevisionChange(relative(root, it.path), LoreFileAction.of(it.action))
            }
        }

    /**
     * History of one file. MOVE and COPY are first-class actions in Lore rather
     * than heuristically detected, so following a rename is exact.
     */
    fun fileHistory(root: Path, path: String, limit: Int = 50): List<LoreHistoryRecord> =
        Arena.ofConfined().use { arena ->
            val args = LoreArgs(arena)
            val globals = args.globals(root)
            val options = arena.allocate(lore_file_history_args_t.LAYOUT)
            args.writeString(lore_file_history_args_t.path(options), path)
            lore_file_history_args_t.length(options, limit)

            val result = LoreClient.require(
                EventPump.call(arena) { callback ->
                    LoreFunctions.lore_file_history.invokeExact(globals, options, callback) as Int
                },
                "file history $path",
            )

            val metadata = LoreHistoryApi.metadataByRevision(result.events)
            result.filter<FileHistoryEvent>().map { entry ->
                val revision = LoreRevisionId(entry.revision)
                LoreHistoryRecord(
                    path = relative(root, entry.path),
                    revision = revision,
                    number = entry.revision_number,
                    size = entry.size,
                    action = LoreFileAction.of(entry.action),
                    metadata = metadata[revision.hex] ?: LoreMetadata.EMPTY,
                )
            }
        }

    private fun relative(root: Path, path: String): String =
        runCatching { root.relativize(Path.of(path)).toString().replace('\\', '/') }
            .getOrNull()
            ?.takeIf { it.isNotEmpty() && !it.startsWith("..") }
            ?: path.replace('\\', '/')
}
