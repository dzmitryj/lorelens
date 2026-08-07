package com.dzmitryj.lorevcs.api

import com.dzmitryj.lorevcs.ffi.EventPump
import com.dzmitryj.lorevcs.ffi.LoreArgs
import com.dzmitryj.lorevcs.ffi.LoreResult
import com.dzmitryj.lorevcs.ffi.generated.LoreEvent
import com.dzmitryj.lorevcs.ffi.generated.LoreFunctions
import com.dzmitryj.lorevcs.ffi.generated.lore_repository_clone_args_t
import com.dzmitryj.lorevcs.ffi.generated.lore_revision_sync_args_t
import java.lang.foreign.Arena
import java.nio.file.Path

object LoreSyncApi {

    /** @param revision empty syncs to the branch tip. */
    fun sync(
        root: Path,
        revision: String = "",
        observer: ((LoreEvent) -> Unit)? = null,
    ): LoreResult = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(root)
        val options = arena.allocate(lore_revision_sync_args_t.LAYOUT)
        if (revision.isNotEmpty()) {
            args.writeString(lore_revision_sync_args_t.revision(options), revision)
        }

        LoreClient.require(
            EventPump.call(arena, observer) { callback ->
                LoreFunctions.lore_revision_sync.invokeExact(globals, options, callback) as Int
            },
            "sync",
        )
    }

    /**
     * Clones into [destination]. The shared store is on by default: with several
     * working directories of one project on a machine, a shared
     * content-addressed store is the difference between hundreds of gigabytes
     * and terabytes.
     */
    fun clone(
        destination: Path,
        url: String,
        useSharedStore: Boolean = true,
        observer: ((LoreEvent) -> Unit)? = null,
    ): LoreResult = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(destination)
        val options = arena.allocate(lore_repository_clone_args_t.LAYOUT)
        args.writeString(lore_repository_clone_args_t.repository_url(options), url)
        lore_repository_clone_args_t.use_shared_store(options, if (useSharedStore) 1 else 0)

        LoreClient.require(
            EventPump.call(arena, observer) { callback ->
                LoreFunctions.lore_repository_clone.invokeExact(globals, options, callback) as Int
            },
            "clone $url",
        )
    }
}
