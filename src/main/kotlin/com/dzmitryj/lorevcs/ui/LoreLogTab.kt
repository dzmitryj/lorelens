package com.dzmitryj.lorevcs.ui

import com.dzmitryj.lorevcs.LoreBundle
import com.dzmitryj.lorevcs.api.LoreHistoryApi
import com.dzmitryj.lorevcs.api.LoreHistoryEntry
import com.dzmitryj.lorevcs.repo.LoreRootFinder
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.ui.ChangesViewContentProvider
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import javax.swing.JPanel
import javax.swing.table.AbstractTableModel
import java.awt.BorderLayout

/**
 * A plain history table rather than a VcsLogProvider. Lore's revision chain is
 * linear, so the Log tab's graph machinery would be paid for and unused, and it
 * persists an index to disk that a decoding mistake would corrupt.
 */
class LoreLogTab(private val project: Project) : ChangesViewContentProvider {

    private val log = logger<LoreLogTab>()
    private val model = HistoryModel()

    override fun initTabContent(content: com.intellij.ui.content.Content) {
        val table = JBTable(model).apply {
            setShowGrid(false)
            emptyText.text = LoreBundle.message("log.empty")
        }

        val panel = JPanel(BorderLayout()).apply {
            add(
                ActionManager.getInstance()
                    .createActionToolbar("LoreLog", DefaultActionGroup(RefreshAction()), true)
                    .also { it.targetComponent = this }
                    .component,
                BorderLayout.NORTH,
            )
            add(ScrollPaneFactory.createScrollPane(table), BorderLayout.CENTER)
            border = JBUI.Borders.empty()
            background = UIUtil.getTreeBackground()
        }

        content.component = panel
        refresh()
    }

    private fun refresh() {
        ApplicationManager.getApplication().executeOnPooledThread {
            val entries = LoreRootFinder.mappedRoots(project).flatMap { root ->
                runCatching { LoreHistoryApi.history(root.toNioPath()) }
                    .onFailure { log.warn("Cannot read Lore history for ${root.path}", it) }
                    .getOrDefault(emptyList())
            }
            ApplicationManager.getApplication().invokeLater { model.setEntries(entries) }
        }
    }

    private inner class RefreshAction : AnAction(
        LoreBundle.message("log.refresh"),
        null,
        com.intellij.icons.AllIcons.Actions.Refresh,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

        override fun actionPerformed(e: AnActionEvent) = refresh()
    }

    private class HistoryModel : AbstractTableModel() {

        private var entries: List<LoreHistoryEntry> = emptyList()

        fun setEntries(value: List<LoreHistoryEntry>) {
            entries = value
            fireTableDataChanged()
        }

        override fun getRowCount(): Int = entries.size

        override fun getColumnCount(): Int = 3

        override fun getColumnName(column: Int): String = when (column) {
            0 -> LoreBundle.message("log.column.revision")
            1 -> LoreBundle.message("log.column.signature")
            else -> LoreBundle.message("log.column.message")
        }

        override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
            val entry = entries[rowIndex]
            return when (columnIndex) {
                0 -> entry.number
                1 -> entry.revision.short
                else -> entry.message.orEmpty()
            }
        }
    }
}
