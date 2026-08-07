package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.api.LoreStatusApi
import com.dzmitryj.lorelens.lock.LoreLockService
import com.dzmitryj.lorelens.repo.LoreRootFinder
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.ListPopup
import com.intellij.openapi.vcs.changes.ui.ChangesViewContentManager
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.StatusBarWidgetFactory
import com.intellij.openapi.wm.impl.status.EditorBasedStatusBarPopup
import java.awt.datatransfer.StringSelection

private const val WIDGET_ID = "LoreLensStatusBarWidget"

class LoreStatusBarWidgetFactory : StatusBarWidgetFactory {

    override fun getId(): String = WIDGET_ID

    override fun getDisplayName(): String = LoreLensBundle.message("widget.name")

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
                if (held > 0) append("  ").append(LoreLensBundle.message("widget.locks", held))
            }
            WidgetState(LoreLensBundle.message("widget.tooltip", status.revision.short), text, true)
        } catch (e: RuntimeException) {
            WidgetState.HIDDEN
        }
    }

    override fun createPopup(context: DataContext): ListPopup? {
        val root = LoreRootFinder.mappedRoots(project).firstOrNull() ?: return null
        val status = runCatching { LoreStatusApi.status(root.toNioPath(), scan = false).revision }
            .getOrNull() ?: return null

        val group = DefaultActionGroup().apply {
            val held = LoreLockService.getInstance(project).heldByMe()
            if (held > 0) addSeparator(LoreLensBundle.message("widget.popup.locks", held))

            ActionManager.getInstance().getAction("LoreLens.FullRescan")?.let(::add)
            add(ShowLogAction())
            add(CopyRevisionAction(status.revision.hex))
        }

        return JBPopupFactory.getInstance().createActionGroupPopup(
            LoreLensBundle.message("widget.popup.title", status.branchName, status.revisionNumber),
            group,
            context,
            JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
            false,
        )
    }

    override fun createInstance(project: Project): StatusBarWidget = LoreStatusBarWidget(project)

    private inner class ShowLogAction :
        AnAction(LoreLensBundle.message("widget.popup.show.log"), null, AllIcons.Vcs.Branch) {

        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun actionPerformed(e: AnActionEvent) {
            ChangesViewContentManager.getToolWindowFor(project, LOG_TAB)?.activate {
                ChangesViewContentManager.getInstance(project).selectContent(LOG_TAB)
            }
        }
    }

    private class CopyRevisionAction(private val hex: String) :
        AnAction(LoreLensBundle.message("widget.popup.copy.revision"), null, AllIcons.Actions.Copy) {

        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

        override fun actionPerformed(e: AnActionEvent) =
            CopyPasteManager.getInstance().setContents(StringSelection(hex))
    }

    private companion object {
        const val LOG_TAB = "LoreLens"
    }
}
