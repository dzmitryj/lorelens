package com.dzmitryj.lorelens.api

import com.dzmitryj.lorelens.ffi.EventPump
import com.dzmitryj.lorelens.ffi.LoreArgs
import com.dzmitryj.lorelens.ffi.LoreResult
import com.dzmitryj.lorelens.ffi.generated.LoreFunctions
import com.dzmitryj.lorelens.ffi.generated.lore_revision_revert_abort_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_revision_revert_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_revision_revert_resolve_args_t
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.nio.file.Path

/**
 * Reverting a revision: its inverse applied to the working tree, committed
 * when nothing conflicts. The conflict workflow -- resolve, mine, theirs,
 * unresolve, restart, abort -- is structurally the merge one with a revision
 * in place of a branch, and Lore surfaces its conflicts through the same
 * status flags, so the same UI carries both.
 */
object LoreRevertApi {

    fun revert(root: Path, revision: String, message: String): LoreResult =
        Arena.ofConfined().use { arena ->
            val args = LoreArgs(arena)
            val globals = args.globals(root)
            val options = arena.allocate(lore_revision_revert_args_t.LAYOUT)
            args.writeString(lore_revision_revert_args_t.revision(options), revision)
            args.writeString(lore_revision_revert_args_t.message(options), message)

            LoreClient.require(
                EventPump.call(arena) { callback ->
                    LoreFunctions.lore_revision_revert.invokeExact(globals, options, callback) as Int
                },
                "revert $revision",
                notable = true,
            )
        }

    fun abort(root: Path): LoreResult = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(root)
        val options = arena.allocate(lore_revision_revert_abort_args_t.LAYOUT)

        LoreClient.require(
            EventPump.call(arena) { callback ->
                LoreFunctions.lore_revision_revert_abort.invokeExact(globals, options, callback) as Int
            },
            "abort revert",
            notable = true,
        )
    }

    fun resolve(root: Path, paths: List<String>): LoreResult =
        withPaths(root, paths, "revert resolve") { globals, options, callback ->
            LoreFunctions.lore_revision_revert_resolve.invokeExact(globals, options, callback) as Int
        }

    fun resolveMine(root: Path, paths: List<String>): LoreResult =
        withPaths(root, paths, "revert resolve as mine") { globals, options, callback ->
            LoreFunctions.lore_revision_revert_resolve_mine.invokeExact(globals, options, callback) as Int
        }

    fun resolveTheirs(root: Path, paths: List<String>): LoreResult =
        withPaths(root, paths, "revert resolve as theirs") { globals, options, callback ->
            LoreFunctions.lore_revision_revert_resolve_theirs.invokeExact(globals, options, callback) as Int
        }

    fun unresolve(root: Path, paths: List<String>): LoreResult =
        withPaths(root, paths, "revert unresolve") { globals, options, callback ->
            LoreFunctions.lore_revision_revert_unresolve.invokeExact(globals, options, callback) as Int
        }

    /** Empty paths re-materializes every conflicted file. */
    fun restart(root: Path, paths: List<String> = emptyList()): LoreResult =
        withPaths(root, paths, "restart revert") { globals, options, callback ->
            LoreFunctions.lore_revision_revert_restart.invokeExact(globals, options, callback) as Int
        }

    /**
     * The path-taking revert verbs share one arg shape, same as the merge
     * resolve family.
     */
    private fun withPaths(
        root: Path,
        paths: List<String>,
        operation: String,
        invoke: (MemorySegment, MemorySegment, MemorySegment) -> Int,
    ): LoreResult = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(root)
        val options = arena.allocate(lore_revision_revert_resolve_args_t.LAYOUT)
        if (paths.isNotEmpty()) {
            args.writeStrings(lore_revision_revert_resolve_args_t.paths(options), paths)
        }

        LoreClient.require(
            EventPump.call(arena) { callback -> invoke(globals, options, callback) },
            operation,
            notable = true,
        )
    }
}
