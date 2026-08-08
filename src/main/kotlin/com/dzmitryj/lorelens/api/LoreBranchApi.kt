package com.dzmitryj.lorelens.api

import com.dzmitryj.lorelens.ffi.EventPump
import com.dzmitryj.lorelens.ffi.LoreArgs
import com.dzmitryj.lorelens.ffi.LoreResult
import com.dzmitryj.lorelens.ffi.generated.BranchListEntryEvent
import com.dzmitryj.lorelens.ffi.generated.LoreFunctions
import com.dzmitryj.lorelens.ffi.generated.lore_branch_list_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_branch_merge_abort_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_branch_merge_resolve_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_branch_merge_resolve_mine_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_branch_merge_resolve_theirs_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_branch_merge_unresolve_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_branch_switch_args_t
import com.dzmitryj.lorelens.model.LoreBranch
import com.dzmitryj.lorelens.model.LoreBranchLocation
import com.dzmitryj.lorelens.model.LoreRevisionId
import java.lang.foreign.Arena
import java.nio.file.Path

object LoreBranchApi {

    fun list(root: Path, includeArchived: Boolean = false): List<LoreBranch> =
        Arena.ofConfined().use { arena ->
            val args = LoreArgs(arena)
            val globals = args.globals(root)
            val options = arena.allocate(lore_branch_list_args_t.LAYOUT)
            lore_branch_list_args_t.archived(options, if (includeArchived) 1 else 0)

            LoreClient.require(
                EventPump.call(arena) { callback ->
                    LoreFunctions.lore_branch_list.invokeExact(globals, options, callback) as Int
                },
                "branch list",
            ).filter<BranchListEntryEvent>().map { it.toModel() }
        }

    /**
     * @param reset discards local modifications so they match the incoming
     *   revision. Destructive, so it is never passed without asking.
     */
    fun switch(
        root: Path,
        branch: String,
        revision: String = "",
        reset: Boolean = false,
    ): LoreResult = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(root)
        val options = arena.allocate(lore_branch_switch_args_t.LAYOUT)
        args.writeString(lore_branch_switch_args_t.branch(options), branch)
        if (revision.isNotEmpty()) {
            args.writeString(lore_branch_switch_args_t.revision(options), revision)
        }
        lore_branch_switch_args_t.reset(options, if (reset) 1 else 0)

        LoreClient.require(
            EventPump.call(arena) { callback ->
                LoreFunctions.lore_branch_switch.invokeExact(globals, options, callback) as Int
            },
            "switch to $branch",
        )
    }

    fun resolve(root: Path, paths: List<String>): LoreResult =
        resolveWith(root, paths, "resolve") { globals, options, callback ->
            LoreFunctions.lore_branch_merge_resolve.invokeExact(globals, options, callback) as Int
        }

    fun resolveMine(root: Path, paths: List<String>): LoreResult =
        resolveWith(root, paths, "resolve as mine") { globals, options, callback ->
            LoreFunctions.lore_branch_merge_resolve_mine.invokeExact(globals, options, callback) as Int
        }

    fun resolveTheirs(root: Path, paths: List<String>): LoreResult =
        resolveWith(root, paths, "resolve as theirs") { globals, options, callback ->
            LoreFunctions.lore_branch_merge_resolve_theirs.invokeExact(globals, options, callback) as Int
        }

    fun unresolve(root: Path, paths: List<String>): LoreResult =
        resolveWith(root, paths, "unresolve") { globals, options, callback ->
            LoreFunctions.lore_branch_merge_unresolve.invokeExact(globals, options, callback) as Int
        }

    fun abortMerge(root: Path): LoreResult = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(root)
        val options = arena.allocate(lore_branch_merge_abort_args_t.LAYOUT)

        LoreClient.require(
            EventPump.call(arena) { callback ->
                LoreFunctions.lore_branch_merge_abort.invokeExact(globals, options, callback) as Int
            },
            "abort merge",
        )
    }

    /**
     * The four resolve verbs take the same single `paths` array, and their arg
     * structs are layout-identical, so one allocation shape serves all of them.
     */
    private fun resolveWith(
        root: Path,
        paths: List<String>,
        operation: String,
        invoke: (java.lang.foreign.MemorySegment, java.lang.foreign.MemorySegment, java.lang.foreign.MemorySegment) -> Int,
    ): LoreResult = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(root)
        val options = arena.allocate(lore_branch_merge_resolve_args_t.LAYOUT)
        args.writeStrings(lore_branch_merge_resolve_args_t.paths(options), paths)

        LoreClient.require(
            EventPump.call(arena) { callback -> invoke(globals, options, callback) },
            operation,
        )
    }

    private fun BranchListEntryEvent.toModel() = LoreBranch(
        name = name,
        category = category,
        location = LoreBranchLocation.of(location),
        latest = LoreRevisionId(latest),
        creator = creator,
        createdMillis = created,
        isCurrent = is_current.toInt() != 0,
        isArchived = archived.toInt() != 0,
        branchPoints = stack.map { LoreRevisionId(it.revision) },
    )
}
