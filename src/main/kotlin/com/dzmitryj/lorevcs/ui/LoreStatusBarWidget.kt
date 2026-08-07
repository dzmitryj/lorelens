package com.dzmitryj.lorevcs.ui

import com.dzmitryj.lorevcs.LoreBundle
import com.dzmitryj.lorevcs.api.LoreStatusApi
import com.dzmitryj.lorevcs.lock.LoreLockService
import com.dzmitryj.lorevcs.repo.LoreRootFinder
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.StatusBarWidgetFactory
import com.intellij.openapi.wm.impl.status.EditorBasedStatusBarPopup

private const val WIDGET_ID = "LoreStatusBarWidget"

class LoreStatusBarWidgetFactory : StatusBarWidgetFactory {

    override fun getId(): String = WIDGET_ID

    override fun getDisplayName(): String = LoreBundle.message("widget.name")

    override fun isAvailable(project: Project): Boolean =
        LoreRootFinder.mappedRoots(project).isNotEmpty()

    override fun createWidget(project: Project): StatusBarWidget = LoreStatusBarWidget(project)
}

class LoreStatusBarWidget(project: Project) : EditorBasedStatusBarPopup(project, false) {

    override fun ID(): String = WIDGET_ID

    override fun getWidgetState(file: com.intellij.openapi.vfs.VirtualFile?): WidgetState {
        val root = LoreRootFinder.mappedRoots(project).firstOrNull() ?: return WidgetState.HIDDEN

        return try {
            val status = LoreStatusApi.status(root.toNioPath(), scan = false).revision
                ?: return WidgetState.HIDDEN
            val held = LoreLockService.getInstance(project).heldByMe()

            val text = buildString {
                append(status.branchName)
                append(" @").append(status.revisionNumber)
                if (held > 0) append("  ").append(LoreBundle.message("widget.locks", held))
            }
            WidgetState(LoreBundle.message("widget.tooltip", status.revision.short), text, true)
        } catch (e: RuntimeException) {
            WidgetState.HIDDEN
        }
    }

    override fun createPopup(context: com.intellij.openapi.actionSystem.DataContext) = null

    override fun createInstance(project: Project): StatusBarWidget = LoreStatusBarWidget(project)
}
