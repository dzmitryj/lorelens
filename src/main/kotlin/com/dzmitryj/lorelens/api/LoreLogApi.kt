package com.dzmitryj.lorelens.api

import com.dzmitryj.lorelens.ffi.LoreArgs
import com.dzmitryj.lorelens.ffi.generated.LoreFunctions
import com.dzmitryj.lorelens.ffi.generated.lore_log_config_t
import com.dzmitryj.lorelens.ffi.generated.lore_log_level_t
import java.lang.foreign.Arena
import java.nio.file.Path

/**
 * liblore's own logging. Global, not per-repository: one configuration for the
 * process, applied whenever the user flips debug logging.
 */
object LoreLogApi {

    /**
     * Rolling debug log at [path]. What liblore actually did -- transport,
     * store, remote traffic -- is invisible from the event stream, and this
     * file is the only place it appears.
     */
    fun enableDebug(path: Path): Boolean = configure { arena, config ->
        lore_log_config_t.file(config, 1)
        lore_log_config_t.file_rolling(config, 1)
        LoreArgs(arena).writeString(lore_log_config_t.file_path(config), path.toString())
        lore_log_config_t.level(config, lore_log_level_t.LORE_LOG_LEVEL_DEBUG)
        // Bitflags for local, remote and transport; all of them.
        lore_log_config_t.categories(config, ALL_CATEGORIES)
        lore_log_config_t.file_max_size(config, 64 * 1024 * 1024)
        lore_log_config_t.file_max_count(config, 4)
    }

    fun disable(): Boolean = configure { _, config ->
        lore_log_config_t.level(config, lore_log_level_t.LORE_LOG_LEVEL_NONE)
    }

    private fun configure(fill: (Arena, java.lang.foreign.MemorySegment) -> Unit): Boolean =
        Arena.ofConfined().use { arena ->
            val config = arena.allocate(lore_log_config_t.LAYOUT)
            fill(arena, config)
            val code = LoreFunctions.lore_log_configure.invokeExact(config) as Int
            if (code == 0) true else false.also { LoreOperationLog.failed("configure logging failed: code $code") }
        }

    private const val ALL_CATEGORIES = 0x7
}
