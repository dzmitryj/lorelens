package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.api.LoreDiffApi
import com.dzmitryj.lorelens.api.LoreHistoryApi
import com.dzmitryj.lorelens.api.LoreHistoryEntry
import com.dzmitryj.lorelens.changes.LoreContentRevision
import com.dzmitryj.lorelens.changes.LoreRevisionNumber
import com.dzmitryj.lorelens.model.LoreFileAction
import com.dzmitryj.lorelens.model.LoreRevisionChain
import com.dzmitryj.lorelens.repo.LoreRootFinder
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.FileStatus
import com.intellij.openapi.vcs.LocalFilePath
import com.intellij.openapi.vcs.changes.Change
import com.intellij.openapi.vcs.changes.actions.diff.ShowDiffAction
import com.intellij.openapi.vcs.changes.ui.ChangesViewContentProvider
import com.intellij.openapi.vcs.changes.ui.SimpleAsyncChangesBrowser
import com.intellij.openapi.vcs.vfs.ContentRevisionVirtualFile
import com.intellij.ui.DoubleClickListener
import com.intellij.ui.FilterComponent
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.PopupHandler
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.TableSpeedSearch
import com.intellij.ui.components.JBLoadingPanel
import com.intellij.ui.content.Content
import com.intellij.ui.table.TableView
import com.intellij.util.ui.ListTableModel
import java.awt.BorderLayout
import java.awt.event.MouseEvent
import java.nio.file.Path
import javax.swing.JPanel
import javax.swing.ListSelectionModel

/**
 * A plain history table rather than a VcsLogProvider. Lore's revision chain is
 * linear, so the Log tab's graph machinery would be paid for and unused, and it
 * persists an index that a decoding mistake would corrupt.
 */
class LoreLogTab(private val project: Project) : ChangesViewContentProvider {

    private val log = logger<LoreLogTab>()
    private val model = ListTableModel(LoreLogColumn.ALL, emptyList<LogRow>())
    private val table = TableView(model)

    private lateinit var changes: SimpleAsyncChangesBrowser
    private lateinit var loading: JBLoadingPanel

    private var all: List<LogRow> = emptyList()

    override fun initTabContent(content: Content) {
        table.apply {
            setShowGrid(false)
            // Two rows can be selected to compare them.
            selectionModel.selectionMode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
            emptyText.text = LoreLensBundle.message("log.empty")
        }
        TableSpeedSearch.installOn(table)

        changes = SimpleAsyncChangesBrowser(project, false, false)
        table.selectionModel.addListSelectionListener { event ->
            if (!event.valueIsAdjusting) showChangedFiles()
        }

        val actions = DefaultActionGroup(
            RefreshAction(),
            CompareRevisionsAction(),
            OpenAtRevisionAction(),
        )
        PopupHandler.installRowSelectionTablePopup(table, actions, "LoreLensLog")
        object : DoubleClickListener() {
            override fun onDoubleClick(event: MouseEvent): Boolean {
                changes.showDiff()
                return true
            }
        }.installOn(table)

        val splitter = OnePixelSplitter(true, "LoreLens.Log.Proportion", 0.6f).apply {
            firstComponent = ScrollPaneFactory.createScrollPane(table)
            secondComponent = changes
        }

        loading = JBLoadingPanel(BorderLayout(), content).apply {
            add(splitter, BorderLayout.CENTER)
        }

        content.component = JPanel(BorderLayout()).apply {
            add(
                JPanel(BorderLayout()).apply {
                    add(
                        ActionManager.getInstance()
                            .createActionToolbar("LoreLensLog", actions, true)
                            .also { it.targetComponent = this }
                            .component,
                        BorderLayout.WEST,
                    )
                    add(LogFilter(), BorderLayout.EAST)
                },
                BorderLayout.NORTH,
            )
            add(loading, BorderLayout.CENTER)
        }

        refresh()
    }

    private fun refresh() {
        loading.startLoading()
        ApplicationManager.getApplication().executeOnPooledThread {
            val entries = LoreRootFinder.mappedRoots(project).flatMap { root ->
                runCatching { LoreHistoryApi.history(root.toNioPath(), HISTORY_LIMIT) }
                    .onFailure { log.warn("Cannot read Lore history for ${root.path}", it) }
                    .getOrDefault(emptyList())
                    .map { root.toNioPath() to it }
            }
            ApplicationManager.getApplication().invokeLater {
                all = entries
                model.items = entries
                loading.stopLoading()
            }
        }
    }

    private fun applyFilter(text: String) {
        val needle = text.trim().lowercase()
        model.items = if (needle.isEmpty()) all else all.filter { row ->
            val entry = row.entry
            entry.subject.orEmpty().lowercase().contains(needle) ||
                entry.author.orEmpty().lowercase().contains(needle) ||
                entry.number.toString().contains(needle)
        }
    }

    /** Loads what the selected revision changed into the lower pane. */
    private fun showChangedFiles() {
        val row = table.selectedRow
        val selected = model.items.getOrNull(row)
        if (selected == null) {
            changes.setChangesToDisplay(emptyList())
            return
        }

        val (root, entry) = selected
        val parent = LoreRevisionChain.parentOf(model.items, row)?.entry

        ApplicationManager.getApplication().executeOnPooledThread {
            val built = runCatching { buildChanges(root, parent, entry) }
                .onFailure { log.warn("Cannot read changes for revision ${entry.number}", it) }
                .getOrDefault(emptyList())
            ApplicationManager.getApplication().invokeLater { changes.setChangesToDisplay(built) }
        }
    }

    /**
     * A revision's changes are what it did to its parent. Pairing against the
     * working tree instead would report "this revision versus whatever is on
     * disk now", which is a different and usually wrong question.
     */
    private fun buildChanges(
        root: Path,
        parent: LoreHistoryEntry?,
        entry: LoreHistoryEntry,
    ): List<Change> = changesBetween(root, parent, entry)

    private fun changesBetween(
        root: Path,
        older: LoreHistoryEntry?,
        newer: LoreHistoryEntry,
    ): List<Change> {
        val newerRevision = LoreRevisionNumber(newer.revision, newer.number)
        val olderRevision = older?.let { LoreRevisionNumber(it.revision, it.number) }

        return LoreDiffApi.revisionDiff(root, older?.revision?.hex.orEmpty(), newer.revision.hex)
            .map { changed ->
                val filePath = LocalFilePath(root.resolve(changed.path).toString(), false)
                val after = LoreContentRevision(root, filePath, changed.path, newerRevision)
                val before = olderRevision?.let { LoreContentRevision(root, filePath, changed.path, it) }

                when (changed.action) {
                    LoreFileAction.ADD -> Change(null, after, FileStatus.ADDED)
                    LoreFileAction.DELETE -> Change(before, null, FileStatus.DELETED)
                    else -> Change(before, after, FileStatus.MODIFIED)
                }
            }
    }

    private inner class LogFilter : FilterComponent("LoreLens.Log.Filter", 10) {
        override fun filter() = applyFilter(filter ?: "")
    }

    private inner class RefreshAction : AnAction(
        LoreLensBundle.message("log.refresh"),
        null,
        com.intellij.icons.AllIcons.Actions.Refresh,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun actionPerformed(e: AnActionEvent) = refresh()
    }

    /** Enabled only for exactly two selected revisions. */
    private inner class CompareRevisionsAction : AnAction(
        LoreLensBundle.message("log.compare"),
        null,
        com.intellij.icons.AllIcons.Actions.Diff,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun update(e: AnActionEvent) {
            e.presentation.isEnabled = table.selectedObjects.size == 2
        }

        override fun actionPerformed(e: AnActionEvent) {
            val selected = table.selectedObjects.takeIf { it.size == 2 } ?: return
            val (newer, older) = selected.sortedByDescending { it.entry.number }
            val root = newer.first

            ApplicationManager.getApplication().executeOnPooledThread {
                val built = runCatching { changesBetween(root, older.entry, newer.entry) }
                    .onFailure { log.warn("Cannot compare revisions", it) }
                    .getOrDefault(emptyList())
                if (built.isEmpty()) return@executeOnPooledThread

                ApplicationManager.getApplication().invokeLater {
                    ShowDiffAction.showDiffForChange(project, built)
                }
            }
        }
    }

    /** Opens the file as it stood at the selected revision, read only. */
    private inner class OpenAtRevisionAction : AnAction(
        LoreLensBundle.message("log.open.at.revision"),
        null,
        com.intellij.icons.AllIcons.Actions.MenuOpen,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun update(e: AnActionEvent) {
            e.presentation.isEnabled = changes.selectedChanges.isNotEmpty()
        }

        override fun actionPerformed(e: AnActionEvent) {
            changes.selectedChanges
                .mapNotNull { it.beforeRevision ?: it.afterRevision }
                .forEach { revision ->
                    val file = ContentRevisionVirtualFile.create(revision)
                    FileEditorManager.getInstance(project).openFile(file, true)
                }
        }
    }

    private companion object {
        const val HISTORY_LIMIT = 200
    }
}
