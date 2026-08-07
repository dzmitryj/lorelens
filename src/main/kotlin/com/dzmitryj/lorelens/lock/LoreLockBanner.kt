package com.dzmitryj.lorelens.lock

import com.dzmitryj.lorelens.LoreLensBundle
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.EditorNotificationPanel
import com.intellij.ui.EditorNotificationProvider
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.function.Function
import javax.swing.JComponent

class LoreLockBanner : EditorNotificationProvider {

    override fun collectNotificationData(
        project: Project,
        file: VirtualFile,
    ): Function<in FileEditor, out JComponent?>? {
        val service = LoreLockService.getInstance(project)
        val lock = service.lockOf(file) ?: return null
        if (service.stateOf(file) != LockState.LOCKED_BY_OTHER) return null

        return Function {
            EditorNotificationPanel(EditorNotificationPanel.Status.Warning).apply {
                text = LoreLensBundle.message("lock.banner", lock.owner, formatTime(lock.lockedAt))
                createActionLabel(LoreLensBundle.message("lock.banner.refresh")) {
                    service.refreshAll()
                }
            }
        }
    }

    private fun formatTime(epochMillis: Long): String =
        if (epochMillis <= 0) {
            "?"
        } else {
            FORMATTER.format(Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()))
        }

    private companion object {
        val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    }
}
