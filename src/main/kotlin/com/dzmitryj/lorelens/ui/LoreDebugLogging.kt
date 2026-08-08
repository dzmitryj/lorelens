package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.api.LoreLogApi
import com.dzmitryj.lorelens.api.LoreOperationLog
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.application.PathManager
import java.nio.file.Path

/**
 * The switch behind the console's Debug Logging toggle.
 *
 * On, it does two things: liblore writes a rolling debug log -- transport,
 * store and remote traffic that never appear in the event stream -- and the
 * console shows every call rather than only the notable ones. Persisted, and
 * re-applied at startup, so a session being debugged stays debugged.
 */
object LoreDebugLogging {

    private const val KEY = "lorelens.debug.logging"

    val logPath: Path get() = Path.of(PathManager.getLogPath(), "lore-native.log")

    var isEnabled: Boolean
        get() = PropertiesComponent.getInstance().getBoolean(KEY, false)
        set(value) {
            PropertiesComponent.getInstance().setValue(KEY, value)
            apply(value)
        }

    /** Called once at startup so a persisted "on" survives a restart. */
    fun applyPersisted() {
        if (isEnabled) apply(true)
    }

    private fun apply(enabled: Boolean) {
        if (enabled) {
            if (LoreLogApi.enableDebug(logPath)) {
                LoreOperationLog.succeeded("debug logging on: $logPath")
            }
        } else {
            LoreLogApi.disable()
            LoreOperationLog.succeeded("debug logging off")
        }
    }
}
