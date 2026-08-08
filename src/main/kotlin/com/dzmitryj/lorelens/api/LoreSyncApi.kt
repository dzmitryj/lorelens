package com.dzmitryj.lorelens.api

import com.dzmitryj.lorelens.ffi.EventPump
import com.dzmitryj.lorelens.ffi.LoreArgs
import com.dzmitryj.lorelens.ffi.LoreResult
import com.dzmitryj.lorelens.ffi.generated.LoreEvent
import com.dzmitryj.lorelens.ffi.generated.LoreFunctions
import com.dzmitryj.lorelens.ffi.generated.lore_repository_clone_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_revision_sync_args_t
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
            notable = true,
        )
    }

    /**
     * Clones into [destination]. The shared store is on by default: with several
     * working directories of one project on a machine, a shared
     * content-addressed store is the difference between hundreds of gigabytes
     * and terabytes.
     *
     * @param viewFilterFile path to a client-side view filter file, which is the
     *   only point at which v0.8.6 accepts one. There is no API to read or
     *   change the view of an existing checkout.
     */
    fun clone(
        destination: Path,
        url: String,
        useSharedStore: Boolean = true,
        viewFilterFile: String = "",
        observer: ((LoreEvent) -> Unit)? = null,
    ): LoreResult = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(destination)
        val options = arena.allocate(lore_repository_clone_args_t.LAYOUT)
        args.writeString(lore_repository_clone_args_t.repository_url(options), url)
        lore_repository_clone_args_t.use_shared_store(options, if (useSharedStore) 1 else 0)
        if (viewFilterFile.isNotEmpty()) {
            args.writeString(lore_repository_clone_args_t.view(options), viewFilterFile)
        }

        LoreClient.require(
            EventPump.call(arena, observer) { callback ->
                LoreFunctions.lore_repository_clone.invokeExact(globals, options, callback) as Int
            },
            "clone $url",
        )
    }
}
