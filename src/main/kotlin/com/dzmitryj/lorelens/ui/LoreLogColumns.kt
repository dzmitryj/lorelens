package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.api.LoreHistoryEntry
import com.intellij.ui.ColoredTableCellRenderer
import com.intellij.ui.JBColor
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.speedSearch.SpeedSearchUtil
import com.intellij.util.text.DateFormatUtil
import com.intellij.util.ui.ColumnInfo
import java.nio.file.Path
import javax.swing.JTable
import javax.swing.table.TableCellRenderer

/**
 * One row: the repository it came from, the revision, and whether this checkout
 * has that revision yet. History is walked from the remote branch tip, so rows
 * above the synced position are real revisions that simply are not here.
 */
data class LogRow(
    val root: Path,
    val entry: LoreHistoryEntry,
    val synced: Boolean,
    /** Branches whose tip is this revision, rendered as chips on the row. */
    val tips: List<String> = emptyList(),
)

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
            val item = value as? LogRow ?: return
            render(item)
            SpeedSearchUtil.applySpeedSearchHighlighting(table, this, true, selected)
        }
    }

    protected abstract fun ColoredTableCellRenderer.render(row: LogRow)

    /** Unsynced revisions read as pending rather than as history. */
    protected fun attributes(row: LogRow): SimpleTextAttributes =
        if (row.synced) SimpleTextAttributes.REGULAR_ATTRIBUTES else SimpleTextAttributes.GRAYED_ATTRIBUTES

    companion object {
        /** Room for the sort arrow and cell insets, which the string width misses. */
        const val PADDING = 24

        val ALL: Array<ColumnInfo<LogRow, *>> = arrayOf(
            RevisionColumn(), DateColumn(), AuthorColumn(), MessageColumn(),
        )
    }
}

private class RevisionColumn : LoreLogColumn(LoreLensBundle.message("log.column.revision")) {

    override fun getComparator(): Comparator<LogRow> = compareBy { it.entry.number }

    // A max string caps the column; without one JTable splits the width evenly
    // and the message, which is the only column worth reading, gets a quarter.
    override fun getMaxStringValue(): String = "999999  ${LoreLensBundle.message("log.not.synced")}"

    override fun getAdditionalWidth(): Int = PADDING

    override fun ColoredTableCellRenderer.render(row: LogRow) {
        append(row.entry.number.toString(), attributes(row))
        if (!row.synced) {
            append(
                "  ${LoreLensBundle.message("log.not.synced")}",
                SimpleTextAttributes.GRAYED_ITALIC_ATTRIBUTES,
            )
        }
    }
}

private class DateColumn : LoreLogColumn(LoreLensBundle.message("log.column.date")) {

    override fun getComparator(): Comparator<LogRow> = compareBy { it.entry.timestampMillis ?: 0L }

    override fun getMaxStringValue(): String = "Yesterday 12:00 PM"

    override fun getAdditionalWidth(): Int = PADDING

    override fun ColoredTableCellRenderer.render(row: LogRow) {
        row.entry.timestampMillis?.let {
            append(DateFormatUtil.formatPrettyDateTime(it), SimpleTextAttributes.GRAYED_ATTRIBUTES)
        }
    }
}

private class AuthorColumn : LoreLogColumn(LoreLensBundle.message("log.column.author")) {

    override fun getComparator(): Comparator<LogRow> = compareBy { it.entry.author.orEmpty() }

    override fun getMaxStringValue(): String = "firstname.lastname@example.com"

    override fun getAdditionalWidth(): Int = PADDING

    override fun ColoredTableCellRenderer.render(row: LogRow) {
        append(row.entry.author.orEmpty(), attributes(row))

        val committer = row.entry.metadata.committer
        if (committer != null && committer != row.entry.author) {
            append(
                LoreLensBundle.message("log.committed.by", committer),
                SimpleTextAttributes.GRAYED_ATTRIBUTES,
            )
        }
    }
}

private class MessageColumn : LoreLogColumn(LoreLensBundle.message("log.column.message")) {

    override fun getComparator(): Comparator<LogRow> = compareBy { it.entry.subject.orEmpty() }

    override fun ColoredTableCellRenderer.render(row: LogRow) {
        // Chips first, as the Log does, so the eye finds the branch before the
        // message rather than hunting for it at the end of a long subject.
        row.tips.forEach { name ->
            append(" $name ", CHIP)
            append(" ", SimpleTextAttributes.REGULAR_ATTRIBUTES)
        }

        if (row.entry.isMerge) {
            append(
                "${LoreLensBundle.message("log.merge")} ",
                SimpleTextAttributes.GRAYED_BOLD_ATTRIBUTES,
            )
        }

        append(row.entry.subject.orEmpty(), attributes(row))
        row.entry.metadata.body?.let { body ->
            append("  ${body.lineSequence().first()}", SimpleTextAttributes.GRAYED_ATTRIBUTES)
        }
    }

    private companion object {
        /** A branch label, painted like the Log's rather than spelled out. */
        val CHIP = SimpleTextAttributes(
            JBColor(0xD5E8D4, 0x39503B),
            JBColor.foreground(),
            null,
            SimpleTextAttributes.STYLE_SMALLER,
        )
    }
}
