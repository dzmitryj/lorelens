package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.api.LoreHistoryEntry
import com.intellij.ui.ColoredTableCellRenderer
import com.intellij.ui.JBColor
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.speedSearch.SpeedSearchUtil
import com.intellij.util.text.DateFormatUtil
import java.awt.Color
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
    /** The revision this checkout is sitting on. */
    val here: Boolean = false,
    /** Branches whose tip is this revision, rendered as chips on the row. */
    val tips: List<String> = emptyList(),
    /** For a merge, the branch it pulled in and the branch it landed on. */
    val merged: Pair<String, String>? = null,
    /** The branch this revision was made on, when the view spans branches. */
    val branch: String? = null,
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

        fun columns(
            graph: ColumnInfo<LogRow, *>,
            rows: () -> List<LogRow>,
        ): Array<ColumnInfo<LogRow, *>> =
            arrayOf(graph, RefColumn(rows), RevisionColumn(), DateColumn(), AuthorColumn(), MessageColumn())
    }
}

private class RevisionColumn : LoreLogColumn(LoreLensBundle.message("log.column.revision")) {

    override fun getComparator(): Comparator<LogRow> = compareBy { it.entry.number }

    // A max string caps the column; without one JTable splits the width evenly
    // and the message, which is the only column worth reading, gets a quarter.
    override fun getMaxStringValue(): String = "999999  ${LoreLensBundle.message("log.you.are.here")}"

    override fun getAdditionalWidth(): Int = PADDING

    override fun ColoredTableCellRenderer.render(row: LogRow) {
        append(
            row.entry.number.toString(),
            if (row.here) SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES else attributes(row),
        )
        if (row.here) {
            append(
                "  ${LoreLensBundle.message("log.you.are.here")}",
                SimpleTextAttributes(SimpleTextAttributes.STYLE_BOLD, HERE),
            )
        } else if (!row.synced) {
            append(
                "  ${LoreLensBundle.message("log.not.synced")}",
                SimpleTextAttributes.GRAYED_ITALIC_ATTRIBUTES,
            )
        }
    }

    private companion object {
        /** The branch graph's convention: white marks where the checkout sits. */
        val HERE = JBColor(0x2470B3, 0x548AF7)
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
        // A merge says which branch went where, which the message often does not.
        row.merged?.let { (from, into) ->
            append(
                "${LoreLensBundle.message("log.merged", from, into)}  ",
                SimpleTextAttributes.GRAYED_BOLD_ATTRIBUTES,
            )
        }

        append(row.entry.subject.orEmpty(), attributes(row))
        row.entry.metadata.body?.let { body ->
            append("  ${body.lineSequence().first()}", SimpleTextAttributes.GRAYED_ATTRIBUTES)
        }
    }
}

/**
 * Which branch each row was made on, in the colour of the lane its line is
 * drawn in, so the column and the graph read as one thing. A branch tip gets a
 * chip; other rows get the name in small type.
 */
private class RefColumn(
    private val rows: () -> List<LogRow>,
) : LoreLogColumn(LoreLensBundle.message("log.column.refs")) {

    // Sized to the labels actually on screen. A fixed worst case reserved a
    // column's width that stayed empty on every row but a branch tip, and
    // pushed the message -- the only column worth reading -- off the edge.
    override fun getMaxStringValue(): String =
        rows()
            .flatMap { row -> listOf(row.tips.joinToString(" ") { " $it " }, row.branch.orEmpty()) }
            .maxByOrNull { it.length }
            ?.ifEmpty { " " } ?: " "

    override fun getAdditionalWidth(): Int = PADDING

    override fun ColoredTableCellRenderer.render(row: LogRow) {
        val colour = row.branch?.let { LoreBranchColours.colourOf(it) }

        if (row.tips.isNotEmpty()) {
            row.tips.forEach { name ->
                append(" $name ", chip(colour))
                append(" ", SimpleTextAttributes.REGULAR_ATTRIBUTES)
            }
            return
        }

        row.branch?.let { name ->
            append(name, SimpleTextAttributes(SimpleTextAttributes.STYLE_SMALLER, colour))
        }
    }

    private fun chip(colour: Color?): SimpleTextAttributes =
        SimpleTextAttributes(
            colour ?: JBColor(0xD5E8D4, 0x39503B),
            JBColor(Color.WHITE, Color.WHITE),
            null,
            SimpleTextAttributes.STYLE_SMALLER,
        )
}
