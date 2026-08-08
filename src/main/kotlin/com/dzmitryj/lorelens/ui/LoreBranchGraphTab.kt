package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.api.LoreBranchApi
import com.dzmitryj.lorelens.api.LoreDiffApi
import com.dzmitryj.lorelens.api.LoreHistoryApi
import com.dzmitryj.lorelens.api.LoreHistoryEntry
import com.dzmitryj.lorelens.changes.LoreContentRevision
import com.dzmitryj.lorelens.changes.LoreRevisionNumber
import com.dzmitryj.lorelens.model.LoreBranchTree
import com.dzmitryj.lorelens.model.LoreFileAction
import com.dzmitryj.lorelens.model.LoreRevisionId
import com.dzmitryj.lorelens.repo.LoreBranchSwitcher
import com.dzmitryj.lorelens.repo.LoreMerger
import com.dzmitryj.lorelens.repo.LoreRepositoryState
import com.dzmitryj.lorelens.repo.LoreRootFinder
import com.dzmitryj.lorelens.update.LoreSyncSession
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.vcs.FileStatus
import com.intellij.openapi.vcs.LocalFilePath
import com.intellij.openapi.vcs.changes.Change
import com.intellij.openapi.vcs.changes.VcsDirtyScopeManager
import com.intellij.openapi.vcs.changes.actions.diff.ShowDiffAction
import com.intellij.openapi.vcs.changes.ui.ChangesViewContentProvider
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.awt.RelativePoint
import com.intellij.ui.content.Content
import java.awt.BorderLayout
import java.awt.Point
import java.awt.datatransfer.StringSelection
import java.nio.file.Path
import javax.swing.JPanel

/**
 * The branch graph: how the branches relate over time, which is what the
 * swimlanes were always showing. Loads its own data now that it is a tab in its
 * own right rather than a passenger in History.
 */
class LoreBranchGraphTab(private val project: Project) : ChangesViewContentProvider {

    private val log = logger<LoreBranchGraphTab>()
    private val details = LoreCommitDetailsPanel()

    private var entries: Map<String, LoreHistoryEntry> = emptyMap()
    private var lanes: List<String> = emptyList()

    /** Keyed on the branch tips, so reopening the tab is free until one moves. */
    private var loadedKey: String = ""

    private val graph = LoreBranchGraphPanel(
        onSelectCommit = ::showDetails,
        onCommitMenu = ::commitMenu,
        onBranchMenu = ::branchMenu,
    )

    override fun initTabContent(content: Content) {
        val actions = DefaultActionGroup(RefreshAction(), ResetFocusAction())

        val split = OnePixelSplitter(true, "LoreLens.BranchGraph.Details", 0.72f).apply {
            firstComponent = graph
            secondComponent = details
        }

        content.component = JPanel(BorderLayout()).apply {
            add(
                ActionManager.getInstance()
                    .createActionToolbar("LoreLensBranchGraph", actions, false)
                    .also { it.targetComponent = graph }
                    .component,
                BorderLayout.WEST,
            )
            add(split, BorderLayout.CENTER)
        }

        refresh(force = true)
    }

    private fun root(): Path? = LoreRootFinder.mappedRoots(project).firstOrNull()?.toNioPath()

    private fun refresh(force: Boolean) {
        val path = root() ?: return

        ApplicationManager.getApplication().executeOnPooledThread {
            val found = runCatching { LoreBranchApi.list(path).filterNot { it.isArchived } }
                .onFailure { log.warn("Cannot list Lore branches for $path", it) }
                .getOrDefault(emptyList())

            val key = found.joinToString(",") { "${it.name}@${it.latest.hex}" }
            if (!force && key == loadedKey) return@executeOnPooledThread
            loadedKey = key

            val current = LoreRepositoryState.getInstance(project).of(path)?.branchName.orEmpty()

            val walked = LoreBranchWalks.attribute(path, found, HISTORY_LIMIT)
            val head = LoreRepositoryState.getInstance(project).of(path)?.revision?.hex

            // Synced is what the checkout reaches through the whole graph, the
            // same rule History uses; the walks' own first-parent flag stays as
            // the fallback when there is no checkout revision.
            val parents = walked.entries.mapValues { (_, entry) -> entry.parents.map { it.hex } }
            val sync = head?.let { LoreLogOrder.reachable(it, parents) }?.takeIf { it.isNotEmpty() }
            val attributed = walked.attributed.map { input ->
                if (sync == null) input else input.copy(synced = input.hash in sync)
            }

            val order = LoreBranchTree.order(found)
            LoreBranchColours.assign(order)
            val laid = LoreBranchGraphLayout.layout(attributed, order = order)

            ApplicationManager.getApplication().invokeLater {
                entries = walked.entries
                lanes = laid.lanes
                graph.show(laid, current, head)
                details.show(null, emptyList())
            }
        }
    }

    private fun showDetails(node: LoreBranchGraphLayout.Node?) {
        val entry = node?.let { entries[it.hash] }
        val row = entry?.let { LogRow(root() ?: return, it, synced = true) }
        details.show(row, listOfNotNull(node?.let { lanes.getOrNull(it.lane) }))
    }

    private fun commitMenu(node: LoreBranchGraphLayout.Node, at: Point) {
        val entry = entries[node.hash] ?: return

        val group = DefaultActionGroup(
            ShowDiffForRevisionAction(entry),
            SyncToRevisionAction(entry),
            CopyHashAction(entry),
            CopyMessageAction(entry),
        )
        showPopup(LoreLensBundle.message("graph.commit.title", entry.number), group, at)
    }

    private fun branchMenu(branch: String, at: Point) {
        val group = DefaultActionGroup(
            SwitchBranchAction(branch),
            MergeBranchAction(branch),
        )
        showPopup(branch, group, at)
    }

    private fun showPopup(title: String, group: DefaultActionGroup, at: Point) {
        JBPopupFactory.getInstance()
            .createActionGroupPopup(
                title,
                group,
                com.intellij.openapi.actionSystem.DataContext.EMPTY_CONTEXT,
                JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                false,
            )
            .show(RelativePoint(graph, at))
    }

    private inner class ResetFocusAction : AnAction(
        LoreLensBundle.message("graph.reset"),
        null,
        AllIcons.General.FitContent,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun actionPerformed(e: AnActionEvent) = graph.resetFocus()
    }

    private inner class RefreshAction :
        AnAction(LoreLensBundle.message("log.refresh"), null, AllIcons.Actions.Refresh) {

        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun actionPerformed(e: AnActionEvent) = refresh(force = true)
    }

    /** The revision against its first parent, which is what it changed. */
    private inner class ShowDiffForRevisionAction(private val entry: LoreHistoryEntry) :
        AnAction(LoreLensBundle.message("graph.show.diff"), null, AllIcons.Actions.Diff) {

        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

        override fun actionPerformed(e: AnActionEvent) {
            val path = root() ?: return
            val parent = entry.parents.firstOrNull()

            ApplicationManager.getApplication().executeOnPooledThread {
                val changes = runCatching { changesOf(path, parent, entry) }
                    .onFailure { log.warn("Cannot read changes for r${entry.number}", it) }
                    .getOrDefault(emptyList())
                if (changes.isEmpty()) return@executeOnPooledThread

                ApplicationManager.getApplication().invokeLater {
                    ShowDiffAction.showDiffForChange(project, changes)
                }
            }
        }
    }

    private fun changesOf(root: Path, parent: LoreRevisionId?, entry: LoreHistoryEntry): List<Change> {
        val newer = LoreRevisionNumber(entry.revision, entry.number)
        val older = parent?.let { LoreRevisionNumber(it, entry.number - 1) }

        return LoreDiffApi.revisionDiff(root, parent?.hex.orEmpty(), entry.revision.hex).map { changed ->
            val filePath = LocalFilePath(root.resolve(changed.path).toString(), false)
            val after = LoreContentRevision(root, filePath, changed.path, newer)
            val before = older?.let { LoreContentRevision(root, filePath, changed.path, it) }

            when (changed.action) {
                LoreFileAction.ADD -> Change(null, after, FileStatus.ADDED)
                LoreFileAction.DELETE -> Change(before, null, FileStatus.DELETED)
                else -> Change(before, after, FileStatus.MODIFIED)
            }
        }
    }

    private inner class SyncToRevisionAction(private val entry: LoreHistoryEntry) :
        AnAction(LoreLensBundle.message("log.sync.revision"), null, AllIcons.Actions.Download) {

        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun actionPerformed(e: AnActionEvent) {
            val path = root() ?: return

            object : Task.Backgroundable(project, LoreLensBundle.message("update.progress", path), true) {
                private var failure: Throwable? = null

                override fun run(indicator: ProgressIndicator) {
                    failure = runCatching { LoreSyncSession.sync(path, entry.revision.hex) }
                        .exceptionOrNull()
                        ?.also { log.warn("Cannot sync $path", it) }
                }

                override fun onFinished() {
                    LoreRepositoryState.getInstance(project).invalidateAll()
                    VcsDirtyScopeManager.getInstance(project).markEverythingDirty()
                    failure?.let {
                        Messages.showErrorDialog(
                            project,
                            it.message ?: LoreLensBundle.message("log.sync.failed"),
                            LoreLensBundle.message("log.sync.failed"),
                        )
                    }
                    refresh(force = true)
                }
            }.queue()
        }
    }

    private inner class CopyHashAction(private val entry: LoreHistoryEntry) :
        AnAction(LoreLensBundle.message("log.copy.revision"), null, AllIcons.Actions.Copy) {

        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

        override fun actionPerformed(e: AnActionEvent) =
            CopyPasteManager.getInstance().setContents(StringSelection(entry.revision.hex))
    }

    private inner class CopyMessageAction(private val entry: LoreHistoryEntry) :
        AnAction(LoreLensBundle.message("log.copy.message"), null, AllIcons.Actions.InlayRenameInComments) {

        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

        override fun actionPerformed(e: AnActionEvent) =
            CopyPasteManager.getInstance()
                .setContents(StringSelection(entry.message ?: entry.subject.orEmpty()))
    }

    private inner class SwitchBranchAction(private val branch: String) :
        AnAction(LoreLensBundle.message("tree.switch"), null, AllIcons.Vcs.Branch) {

        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun actionPerformed(e: AnActionEvent) {
            val path = root() ?: return
            LoreBranchSwitcher.switch(project, path, branch)
        }
    }

    private inner class MergeBranchAction(private val branch: String) :
        AnAction(LoreLensBundle.message("tree.merge"), null, AllIcons.Vcs.Merge) {

        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun actionPerformed(e: AnActionEvent) {
            val path = root() ?: return
            LoreMerger.merge(project, path, branch)
        }
    }

    private companion object {
        const val HISTORY_LIMIT = 200
    }
}

/** Shown wherever a Lore root is mapped. */
class LoreBranchGraphTabVisibility : java.util.function.Predicate<Project> {
    override fun test(project: Project): Boolean =
        LoreRootFinder.mappedRoots(project).isNotEmpty()
}
