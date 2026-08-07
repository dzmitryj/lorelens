package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.api.LoreDiffApi
import com.dzmitryj.lorelens.api.LoreHistoryApi
import com.dzmitryj.lorelens.api.LoreHistoryEntry
import com.dzmitryj.lorelens.changes.LoreContentRevision
import com.dzmitryj.lorelens.changes.LoreRevisionNumber
import com.dzmitryj.lorelens.model.LoreFileAction
import com.dzmitryj.lorelens.repo.LoreRootFinder
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.FileStatus
import com.intellij.openapi.vcs.LocalFilePath
import com.intellij.openapi.vcs.changes.Change
import com.intellij.openapi.vcs.changes.CurrentContentRevision
import com.intellij.openapi.vcs.changes.ui.ChangesViewContentProvider
import com.intellij.openapi.vcs.changes.ui.SimpleChangesBrowser
import com.intellij.ui.ColoredTableCellRenderer
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.content.Content
import com.intellij.ui.table.JBTable
import com.intellij.util.text.DateFormatUtil
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.nio.file.Path
import javax.swing.JPanel
import javax.swing.JTable
import javax.swing.ListSelectionModel
import javax.swing.table.AbstractTableModel

private const val REVISION_COLUMN = 0
private const val DATE_COLUMN = 1
private const val AUTHOR_COLUMN = 2
private const val MESSAGE_COLUMN = 3

/**
 * A plain history table rather than a VcsLogProvider. Lore's revision chain is
 * linear, so the Log tab's graph machinery would be paid for and unused, and it
 * persists an index that a decoding mistake would corrupt.
 */
class LoreLogTab(private val project: Project) : ChangesViewContentProvider {

    private val log = logger<LoreLogTab>()
    private val model = HistoryModel()
    private lateinit var changes: SimpleChangesBrowser

    override fun initTabContent(content: Content) {
        val table = JBTable(model).apply {
            setShowGrid(false)
            selectionModel.selectionMode = ListSelectionModel.SINGLE_SELECTION
            emptyText.text = LoreLensBundle.message("log.empty")
            // A JLabel has no line-box model, so a raw multi-line message paints
            // its subject and body as one run. This renders them deliberately.
            setDefaultRenderer(Any::class.java, EntryRenderer())
            columnModel.getColumn(REVISION_COLUMN).preferredWidth = JBUI.scale(60)
            columnModel.getColumn(DATE_COLUMN).preferredWidth = JBUI.scale(140)
            columnModel.getColumn(AUTHOR_COLUMN).preferredWidth = JBUI.scale(180)
            columnModel.getColumn(MESSAGE_COLUMN).preferredWidth = JBUI.scale(900)
        }

        changes = SimpleChangesBrowser(project, emptyList())
        table.selectionModel.addListSelectionListener { event ->
            if (!event.valueIsAdjusting) showChangedFiles(model.entryAt(table.selectedRow))
        }

        val splitter = OnePixelSplitter(true, "LoreLens.Log.Proportion", 0.6f).apply {
            firstComponent = ScrollPaneFactory.createScrollPane(table)
            secondComponent = changes
        }

        content.component = JPanel(BorderLayout()).apply {
            add(
                ActionManager.getInstance()
                    .createActionToolbar("LoreLensLog", DefaultActionGroup(RefreshAction()), true)
                    .also { it.targetComponent = this }
                    .component,
                BorderLayout.NORTH,
            )
            add(splitter, BorderLayout.CENTER)
        }

        refresh()
    }

    private fun refresh() {
        ApplicationManager.getApplication().executeOnPooledThread {
            val entries = LoreRootFinder.mappedRoots(project).flatMap { root ->
                runCatching { LoreHistoryApi.history(root.toNioPath(), HISTORY_LIMIT) }
                    .onFailure { log.warn("Cannot read Lore history for ${root.path}", it) }
                    .getOrDefault(emptyList())
                    .map { root.toNioPath() to it }
            }
            ApplicationManager.getApplication().invokeLater { model.setEntries(entries) }
        }
    }

    /** Loads the selected revision's changed files into the lower pane. */
    private fun showChangedFiles(selected: Pair<Path, LoreHistoryEntry>?) {
        if (selected == null) {
            changes.setChangesToDisplay(emptyList())
            return
        }

        val (root, entry) = selected
        ApplicationManager.getApplication().executeOnPooledThread {
            val built = runCatching { buildChanges(root, entry) }
                .onFailure { log.warn("Cannot read changes for revision ${entry.number}", it) }
                .getOrDefault(emptyList())
            ApplicationManager.getApplication().invokeLater { changes.setChangesToDisplay(built) }
        }
    }

    private fun buildChanges(root: Path, entry: LoreHistoryEntry): List<Change> {
        val revision = LoreRevisionNumber(entry.revision, entry.number)

        return LoreDiffApi.revisionDiff(root, entry.revision.hex).map { changed ->
            val filePath = LocalFilePath(root.resolve(changed.path).toString(), false)
            val before = LoreContentRevision(root, filePath, changed.path, revision)

            when (changed.action) {
                LoreFileAction.ADD -> Change(null, CurrentContentRevision(filePath), FileStatus.ADDED)
                LoreFileAction.DELETE -> Change(before, null, FileStatus.DELETED)
                else -> Change(before, CurrentContentRevision(filePath), FileStatus.MODIFIED)
            }
        }
    }

    private inner class RefreshAction : AnAction(
        LoreLensBundle.message("log.refresh"),
        null,
        com.intellij.icons.AllIcons.Actions.Refresh,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

        override fun actionPerformed(e: AnActionEvent) = refresh()
    }

    private class EntryRenderer : ColoredTableCellRenderer() {

        override fun customizeCellRenderer(
            table: JTable,
            value: Any?,
            selected: Boolean,
            hasFocus: Boolean,
            row: Int,
            column: Int,
        ) {
            val entry = value as? LoreHistoryEntry ?: return

            when (column) {
                REVISION_COLUMN -> append(entry.number.toString(), SimpleTextAttributes.REGULAR_ATTRIBUTES)

                DATE_COLUMN -> entry.timestampMillis?.let {
                    append(DateFormatUtil.formatPrettyDateTime(it), SimpleTextAttributes.GRAYED_ATTRIBUTES)
                }

                AUTHOR_COLUMN -> {
                    append(entry.author.orEmpty(), SimpleTextAttributes.REGULAR_ATTRIBUTES)
                    val committer = entry.metadata.committer
                    if (committer != null && committer != entry.author) {
                        append("  committed by $committer", SimpleTextAttributes.GRAYED_ATTRIBUTES)
                    }
                }

                MESSAGE_COLUMN -> {
                    append(entry.subject.orEmpty(), SimpleTextAttributes.REGULAR_ATTRIBUTES)
                    entry.metadata.body?.let { body ->
                        append("  ${body.lineSequence().first()}", SimpleTextAttributes.GRAYED_ATTRIBUTES)
                    }
                }
            }
        }
    }

    private class HistoryModel : AbstractTableModel() {

        private var entries: List<Pair<Path, LoreHistoryEntry>> = emptyList()

        fun setEntries(value: List<Pair<Path, LoreHistoryEntry>>) {
            entries = value
            fireTableDataChanged()
        }

        fun entryAt(row: Int): Pair<Path, LoreHistoryEntry>? = entries.getOrNull(row)

        override fun getRowCount(): Int = entries.size

        override fun getColumnCount(): Int = 4

        override fun getColumnName(column: Int): String = when (column) {
            REVISION_COLUMN -> LoreLensBundle.message("log.column.revision")
            DATE_COLUMN -> LoreLensBundle.message("log.column.date")
            AUTHOR_COLUMN -> LoreLensBundle.message("log.column.author")
            else -> LoreLensBundle.message("log.column.message")
        }

        /** The whole entry, so the renderer can style each column from it. */
        override fun getValueAt(rowIndex: Int, columnIndex: Int): Any = entries[rowIndex].second
    }

    private companion object {
        const val HISTORY_LIMIT = 200
    }
}
