package com.dzmitryj.lorevcs.api

import com.dzmitryj.lorevcs.ffi.EventPump
import com.dzmitryj.lorevcs.ffi.LoreArgs
import com.dzmitryj.lorevcs.ffi.generated.AuthUserInfoEvent
import com.dzmitryj.lorevcs.ffi.generated.LockFileQueryEvent
import com.dzmitryj.lorevcs.ffi.generated.LockFileStatusEvent
import com.dzmitryj.lorevcs.ffi.generated.LoreFunctions
import com.dzmitryj.lorevcs.ffi.generated.lore_auth_user_info_args_t
import com.dzmitryj.lorevcs.ffi.generated.lore_lock_file_acquire_args_t
import com.dzmitryj.lorevcs.ffi.generated.lore_lock_file_query_args_t
import com.dzmitryj.lorevcs.ffi.generated.lore_lock_file_release_args_t
import com.dzmitryj.lorevcs.ffi.generated.lore_lock_file_status_args_t
import com.dzmitryj.lorevcs.model.LoreLock
import java.lang.foreign.Arena
import java.nio.file.Path

object LoreLockApi {

    private fun absolute(root: Path, paths: List<String>) =
        paths.map { root.resolve(it).toString() }

    fun acquire(root: Path, paths: List<String>) {
        if (paths.isEmpty()) return

        Arena.ofConfined().use { arena ->
            val args = LoreArgs(arena)
            val globals = args.globals(root)
            val options = arena.allocate(lore_lock_file_acquire_args_t.LAYOUT)
            args.writeStrings(lore_lock_file_acquire_args_t.paths(options), absolute(root, paths))

            LoreClient.require(
                EventPump.call(arena) { callback ->
                    LoreFunctions.lore_lock_file_acquire.invokeExact(globals, options, callback) as Int
                },
                "acquire lock",
            )
        }
    }

    fun release(root: Path, paths: List<String>) {
        if (paths.isEmpty()) return

        Arena.ofConfined().use { arena ->
            val args = LoreArgs(arena)
            val globals = args.globals(root)
            val options = arena.allocate(lore_lock_file_release_args_t.LAYOUT)
            args.writeStrings(lore_lock_file_release_args_t.paths(options), absolute(root, paths))

            LoreClient.require(
                EventPump.call(arena) { callback ->
                    LoreFunctions.lore_lock_file_release.invokeExact(globals, options, callback) as Int
                },
                "release lock",
            )
        }
    }

    fun status(root: Path, paths: List<String>): List<LoreLock> {
        if (paths.isEmpty()) return emptyList()

        return Arena.ofConfined().use { arena ->
            val args = LoreArgs(arena)
            val globals = args.globals(root)
            val options = arena.allocate(lore_lock_file_status_args_t.LAYOUT)
            args.writeStrings(lore_lock_file_status_args_t.paths(options), absolute(root, paths))

            LoreClient.require(
                EventPump.call(arena) { callback ->
                    LoreFunctions.lore_lock_file_status.invokeExact(globals, options, callback) as Int
                },
                "lock status",
            ).filter<LockFileStatusEvent>().map { LoreLock(normalize(root, it.path), it.owner, it.locked_at) }
        }
    }

    /** Every lock on the current branch, which is what the cache is built from. */
    fun query(root: Path): List<LoreLock> = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(root)
        val options = arena.allocate(lore_lock_file_query_args_t.LAYOUT)

        LoreClient.require(
            EventPump.call(arena) { callback ->
                LoreFunctions.lore_lock_file_query.invokeExact(globals, options, callback) as Int
            },
            "query locks",
        ).filter<LockFileQueryEvent>().map { LoreLock(normalize(root, it.path), it.owner, it.locked_at) }
    }

    /** Empty user_ids resolves the current user locally, with no server round trip. */
    fun currentUserId(root: Path): String? = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(root)
        val options = arena.allocate(lore_auth_user_info_args_t.LAYOUT)

        runCatching {
            LoreClient.require(
                EventPump.call(arena) { callback ->
                    LoreFunctions.lore_auth_user_info.invokeExact(globals, options, callback) as Int
                },
                "current user",
            ).filter<AuthUserInfoEvent>().firstOrNull()?.id
        }.getOrNull()
    }

    private fun normalize(root: Path, path: String): String =
        runCatching { root.relativize(Path.of(path)).toString().replace('\\', '/') }
            .getOrNull()
            ?.takeIf { it.isNotEmpty() && !it.startsWith("..") }
            ?: path.replace('\\', '/')
}
