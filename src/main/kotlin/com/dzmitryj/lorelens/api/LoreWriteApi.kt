package com.dzmitryj.lorelens.api

import com.dzmitryj.lorelens.ffi.EventPump
import com.dzmitryj.lorelens.ffi.LoreArgs
import com.dzmitryj.lorelens.ffi.LoreResult
import com.dzmitryj.lorelens.ffi.generated.LoreFunctions
import com.dzmitryj.lorelens.ffi.generated.lore_branch_push_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_file_reset_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_file_stage_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_file_stage_move_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_file_unstage_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_revision_commit_args_t
import java.lang.foreign.Arena
import java.nio.file.Path

/**
 * Mutating verbs. Paths are repository-relative and made absolute here, matching
 * the convention the filesystem verbs use.
 */
object LoreWriteApi {

    private fun absolute(root: Path, paths: List<String>) =
        paths.map { root.resolve(it).toString() }

    /**
     * Stages the given paths explicitly. Never stage a directory instead: a
     * directory path stages only files already marked dirty, which would
     * silently omit anything the dirty tracker missed.
     */
    fun stage(root: Path, paths: List<String>): LoreResult = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(root)
        val options = arena.allocate(lore_file_stage_args_t.LAYOUT)
        args.writeStrings(lore_file_stage_args_t.paths(options), absolute(root, paths))

        LoreClient.require(
            EventPump.call(arena) { callback ->
                LoreFunctions.lore_file_stage.invokeExact(globals, options, callback) as Int
            },
            "stage",
            notable = true,
        )
    }

    /** Records a rename so it renders as one, rather than an add plus a delete. */
    fun stageMove(root: Path, from: String, to: String): LoreResult = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(root)
        val options = arena.allocate(lore_file_stage_move_args_t.LAYOUT)
        args.writeString(lore_file_stage_move_args_t.from_path(options), root.resolve(from).toString())
        args.writeString(lore_file_stage_move_args_t.to_path(options), root.resolve(to).toString())

        LoreClient.require(
            EventPump.call(arena) { callback ->
                LoreFunctions.lore_file_stage_move.invokeExact(globals, options, callback) as Int
            },
            "stage move $from to $to",
        )
    }

    fun unstage(root: Path, paths: List<String>): LoreResult = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(root)
        val options = arena.allocate(lore_file_unstage_args_t.LAYOUT)
        args.writeStrings(lore_file_unstage_args_t.paths(options), absolute(root, paths))

        LoreClient.require(
            EventPump.call(arena) { callback ->
                LoreFunctions.lore_file_unstage.invokeExact(globals, options, callback) as Int
            },
            "unstage",
            notable = true,
        )
    }

    fun commit(root: Path, message: String): LoreResult = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(root)
        val options = arena.allocate(lore_revision_commit_args_t.LAYOUT)
        args.writeString(lore_revision_commit_args_t.message(options), message)

        LoreClient.require(
            EventPump.call(arena) { callback ->
                LoreFunctions.lore_revision_commit.invokeExact(globals, options, callback) as Int
            },
            "commit",
            notable = true,
        )
    }

    /** Restores the given paths to their content at [revision]. */
    fun reset(root: Path, paths: List<String>, revision: String = ""): LoreResult =
        Arena.ofConfined().use { arena ->
            val args = LoreArgs(arena)
            val globals = args.globals(root)
            val options = arena.allocate(lore_file_reset_args_t.LAYOUT)
            args.writeStrings(lore_file_reset_args_t.paths(options), absolute(root, paths))
            if (revision.isNotEmpty()) {
                args.writeString(lore_file_reset_args_t.revision(options), revision)
            }

            LoreClient.require(
                EventPump.call(arena) { callback ->
                    LoreFunctions.lore_file_reset.invokeExact(globals, options, callback) as Int
                },
                "reset",
                notable = true,
            )
        }

    fun push(root: Path): LoreResult = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(root)
        val options = arena.allocate(lore_branch_push_args_t.LAYOUT)

        LoreClient.require(
            EventPump.call(arena) { callback ->
                LoreFunctions.lore_branch_push.invokeExact(globals, options, callback) as Int
            },
            "push",
            notable = true,
        )
    }
}
