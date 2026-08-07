package com.dzmitryj.lorevcs

import com.dzmitryj.lorevcs.api.LoreClient
import com.dzmitryj.lorevcs.ffi.EventPump
import com.dzmitryj.lorevcs.ffi.LoreArgs
import com.dzmitryj.lorevcs.ffi.generated.LoreFunctions
import com.dzmitryj.lorevcs.ffi.generated.lore_file_stage_args_t
import com.dzmitryj.lorevcs.ffi.generated.lore_revision_commit_args_t
import java.lang.foreign.Arena
import java.nio.file.Path

/**
 * Stage and commit for fixture setup only. The production implementations land
 * with the write-operations milestone.
 */
object LoreTestRepository {

    fun stage(root: Path, paths: List<String>) = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(root)
        val options = arena.allocate(lore_file_stage_args_t.LAYOUT)
        args.writeStrings(
            lore_file_stage_args_t.paths(options),
            paths.map { root.resolve(it).toString() },
        )

        LoreClient.require(
            EventPump.call(arena) { callback ->
                LoreFunctions.lore_file_stage.invokeExact(globals, options, callback) as Int
            },
            "stage",
        )
    }

    fun commit(root: Path, message: String) = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(root)
        val options = arena.allocate(lore_revision_commit_args_t.LAYOUT)
        args.writeString(lore_revision_commit_args_t.message(options), message)

        LoreClient.require(
            EventPump.call(arena) { callback ->
                LoreFunctions.lore_revision_commit.invokeExact(globals, options, callback) as Int
            },
            "commit",
        )
    }
}
