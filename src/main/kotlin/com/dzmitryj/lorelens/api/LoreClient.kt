package com.dzmitryj.lorelens.api

import com.dzmitryj.lorelens.ffi.EventPump
import com.dzmitryj.lorelens.ffi.LoreArgs
import com.dzmitryj.lorelens.ffi.LoreCallException
import com.dzmitryj.lorelens.ffi.LoreResult
import com.dzmitryj.lorelens.ffi.generated.ErrorEvent
import com.dzmitryj.lorelens.ffi.generated.LoreBuildInfo
import com.dzmitryj.lorelens.ffi.generated.LoreFunctions
import com.dzmitryj.lorelens.ffi.generated.lore_repository_create_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_repository_info_args_t
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.nio.file.Path

class LoreVersionMismatchException(val loaded: String, val expected: String) : RuntimeException(
    "Loaded liblore reports $loaded but the bindings were generated against $expected"
)

/**
 * Typed entry point for the rest of the plugin. Nothing above this layer should
 * touch MemorySegment, Arena or the generated bindings.
 */
object LoreClient {

    fun version(): String {
        val pointer = LoreFunctions.lore_version.invokeExact() as MemorySegment
        return pointer.reinterpret(Long.MAX_VALUE).getString(0)
    }

    /**
     * Fails fast when the bundled library and the committed bindings disagree,
     * which would otherwise surface as a struct layout mismatch at some
     * arbitrary later call.
     *
     * lore_version() carries build metadata (`0.8.6+373`) that
     * LORE_INTERFACE_VERSION does not, so only the release part is compared.
     */
    fun verifyVersion() {
        val loaded = version()
        if (loaded.substringBefore('+') != LoreBuildInfo.INTERFACE_VERSION) {
            throw LoreVersionMismatchException(loaded, LoreBuildInfo.INTERFACE_VERSION)
        }
    }

    fun repositoryInfo(root: Path): LoreResult = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(root)
        val options = arena.allocate(lore_repository_info_args_t.LAYOUT)

        EventPump.call(arena) { callback ->
            LoreFunctions.lore_repository_info.invokeExact(globals, options, callback) as Int
        }
    }

    fun createRepository(directory: Path, url: String) = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(directory)
        val options = arena.allocate(lore_repository_create_args_t.LAYOUT)
        args.writeString(lore_repository_create_args_t.repository_url(options), url)

        require(
            EventPump.call(arena) { callback ->
                LoreFunctions.lore_repository_create.invokeExact(globals, options, callback) as Int
            },
            "create repository at $url",
        )
    }

    /**
     * @param notable whether a successful call is worth showing in the console.
     *   Off by default: status, hash, file writes and diffs run per file and per
     *   batch, and reporting each one flooded the console -- and through it the
     *   EDT -- badly enough to slow the editor down. Mutations opt in; failures
     *   are always reported.
     */
    fun require(result: LoreResult, what: String, notable: Boolean = false): LoreResult {
        if (result.succeeded) {
            if (notable) LoreOperationLog.succeeded(what)
            return result
        }

        val code = result.statusOrNull()?.name ?: "code ${result.status ?: result.returnCode}"
        // Lore names the offending field in the error event, which is the only
        // place the actual reason appears.
        val detail = result.filter<ErrorEvent>()
            .map { it.error_inner }
            .filter { it.isNotBlank() }
            .distinct()
            .joinToString("; ")

        val message = buildString {
            append("$what failed: $code")
            if (detail.isNotEmpty()) append(" -- $detail")
        }
        LoreOperationLog.failed(message)
        throw LoreCallException(result, message)
    }
}
