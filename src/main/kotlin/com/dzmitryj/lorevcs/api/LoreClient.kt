package com.dzmitryj.lorevcs.api

import com.dzmitryj.lorevcs.ffi.EventPump
import com.dzmitryj.lorevcs.ffi.LoreArgs
import com.dzmitryj.lorevcs.ffi.LoreCallException
import com.dzmitryj.lorevcs.ffi.LoreResult
import com.dzmitryj.lorevcs.ffi.generated.LoreBuildInfo
import com.dzmitryj.lorevcs.ffi.generated.LoreFunctions
import com.dzmitryj.lorevcs.ffi.generated.lore_repository_info_args_t
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

    fun require(result: LoreResult, what: String): LoreResult {
        if (!result.succeeded) {
            val status = result.statusOrNull()
            throw LoreCallException(result, "$what failed: ${status?.name ?: "code ${result.status ?: result.returnCode}"}")
        }
        return result
    }
}
