package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.api.LoreBranchApi
import com.dzmitryj.lorelens.lock.LoreLockService
import com.dzmitryj.lorelens.repo.LoreBranchSwitcher
import com.dzmitryj.lorelens.repo.LoreRepositoryState
import com.dzmitryj.lorelens.repo.LoreRootFinder
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionGroup
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
import java.nio.file.Path

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

    /**
     * Runs on every editor switch, so it reads the cached revision rather than
     * asking Lore each time. Hidden only when there is no Lore root at all: a
     * failed read leaves the widget in place showing what it last knew.
     */
    override fun getWidgetState(file: com.intellij.openapi.vfs.VirtualFile?): WidgetState {
        val root = LoreRootFinder.mappedRoots(project).firstOrNull() ?: return WidgetState.HIDDEN
        val status = LoreRepositoryState.getInstance(project).of(root.toNioPath())
            ?: return WidgetState(
                LoreLensBundle.message("widget.name"),
                LoreLensBundle.message("widget.unknown"),
                false,
            )

        val held = LoreLockService.getInstance(project).heldByMe()
        val text = buildString {
            append(status.branchName)
            append(" @").append(status.revisionNumber)
            if (held > 0) append("  ").append(LoreLensBundle.message("widget.locks", held))
        }
        return WidgetState(LoreLensBundle.message("widget.tooltip", status.revision.short), text, true)
    }

    override fun createPopup(context: DataContext): ListPopup? {
        val root = LoreRootFinder.mappedRoots(project).firstOrNull() ?: return null
        val status = LoreRepositoryState.getInstance(project).of(root.toNioPath()) ?: return null

        val group = DefaultActionGroup().apply {
            val held = LoreLockService.getInstance(project).heldByMe()
            if (held > 0) addSeparator(LoreLensBundle.message("widget.popup.locks", held))

            add(SwitchBranchAction(root.toNioPath(), status.branchName))
            addSeparator()
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

    /** Nested so the branch list is only fetched when the popup is opened. */
    private inner class SwitchBranchAction(private val root: Path, private val current: String) :
        ActionGroup(LoreLensBundle.message("widget.popup.switch.branch"), true) {

        init {
            templatePresentation.icon = AllIcons.Vcs.Branch
        }

        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

        override fun getChildren(e: AnActionEvent?): Array<AnAction> =
            runCatching { LoreBranchApi.list(root) }
                .getOrDefault(emptyList())
                .filterNot { it.isArchived || it.name == current }
                .map { branch -> SwitchToAction(root, branch.name) }
                .toTypedArray()
    }

    private inner class SwitchToAction(private val root: Path, private val branch: String) :
        AnAction(branch, null, null) {

        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun actionPerformed(e: AnActionEvent) =
            LoreBranchSwitcher.switch(project, root, branch)
    }

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
