package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.api.LoreHistoryEntry
import com.intellij.ui.ColoredTableCellRenderer
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.speedSearch.SpeedSearchUtil
import com.intellij.util.text.DateFormatUtil
import com.intellij.util.ui.ColumnInfo
import java.nio.file.Path
import javax.swing.JTable
import javax.swing.table.TableCellRenderer

/** One row: the repository it came from, and the revision. */
typealias LogRow = Pair<Path, LoreHistoryEntry>

val LogRow.entry: LoreHistoryEntry get() = second

/**
 * Columns render through SimpleColoredComponent rather than the default
 * JLabel-based renderer, which has no line-box model and would paint a
 * multi-line commit message as one unreadable run.
 */
abstract class LoreLogColumn(name: String) : ColumnInfo<LogRow, LogRow>(name) {

    override fun valueOf(item: LogRow): LogRow = item

    override fun getRenderer(item: LogRow?): TableCellRenderer = renderer

    private val renderer = object : ColoredTableCellRenderer() {
        override fun customizeCellRenderer(
            table: JTable,
            value: Any?,
            selected: Boolean,
            hasFocus: Boolean,
            row: Int,
            column: Int,
        ) {
            @Suppress("UNCHECKED_CAST")
            val item = value as? LogRow ?: return
            render(item.entry)
            SpeedSearchUtil.applySpeedSearchHighlighting(table, this, true, selected)
        }
    }

    protected abstract fun ColoredTableCellRenderer.render(entry: LoreHistoryEntry)

    companion object {
        val ALL: Array<ColumnInfo<LogRow, *>> = arrayOf(
            RevisionColumn(), DateColumn(), AuthorColumn(), MessageColumn(),
        )
    }
}

private class RevisionColumn : LoreLogColumn(LoreLensBundle.message("log.column.revision")) {

    override fun getComparator(): Comparator<LogRow> = compareBy { it.entry.number }

    override fun getPreferredStringValue(): String = "999999"

    override fun ColoredTableCellRenderer.render(entry: LoreHistoryEntry) {
        append(entry.number.toString(), SimpleTextAttributes.REGULAR_ATTRIBUTES)
    }
}

private class DateColumn : LoreLogColumn(LoreLensBundle.message("log.column.date")) {

    override fun getComparator(): Comparator<LogRow> = compareBy { it.entry.timestampMillis ?: 0L }

    override fun getPreferredStringValue(): String = "Yesterday 12:00"

    override fun ColoredTableCellRenderer.render(entry: LoreHistoryEntry) {
        entry.timestampMillis?.let {
            append(DateFormatUtil.formatPrettyDateTime(it), SimpleTextAttributes.GRAYED_ATTRIBUTES)
        }
    }
}

private class AuthorColumn : LoreLogColumn(LoreLensBundle.message("log.column.author")) {

    override fun getComparator(): Comparator<LogRow> = compareBy { it.entry.author.orEmpty() }

    override fun getPreferredStringValue(): String = "firstname.lastname@example.com"

    override fun ColoredTableCellRenderer.render(entry: LoreHistoryEntry) {
        append(entry.author.orEmpty(), SimpleTextAttributes.REGULAR_ATTRIBUTES)

        val committer = entry.metadata.committer
        if (committer != null && committer != entry.author) {
            append(
                LoreLensBundle.message("log.committed.by", committer),
                SimpleTextAttributes.GRAYED_ATTRIBUTES,
            )
        }
    }
}

private class MessageColumn : LoreLogColumn(LoreLensBundle.message("log.column.message")) {

    override fun getComparator(): Comparator<LogRow> = compareBy { it.entry.subject.orEmpty() }

    override fun ColoredTableCellRenderer.render(entry: LoreHistoryEntry) {
        append(entry.subject.orEmpty(), SimpleTextAttributes.REGULAR_ATTRIBUTES)
        entry.metadata.body?.let { body ->
            append("  ${body.lineSequence().first()}", SimpleTextAttributes.GRAYED_ATTRIBUTES)
        }
    }
}
