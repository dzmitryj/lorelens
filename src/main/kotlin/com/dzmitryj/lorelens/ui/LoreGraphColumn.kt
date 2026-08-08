package com.dzmitryj.lorelens.ui

import com.intellij.ui.JBColor
import com.intellij.util.ui.ColumnInfo
import com.intellij.util.ui.JBUI
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Component
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JComponent
import javax.swing.JTable
import javax.swing.table.TableCellRenderer

/**
 * Draws the shape of the history beside it: a node per revision and a line per
 * lane, so a merge reads as two strands joining rather than as a word in the
 * message.
 *
 * Lanes are coloured by index so a branch keeps its colour while it is open.
 */
class LoreGraphColumn(private val rows: () -> List<LoreGraphLayout.Row>) :
    ColumnInfo<LogRow, LogRow>("") {

    override fun valueOf(item: LogRow): LogRow = item

    override fun getRenderer(item: LogRow?): TableCellRenderer = renderer

    /** Sized to the widest row so the lanes never clip. */
    override fun getWidth(table: JTable): Int {
        val lanes = rows().maxOfOrNull { it.width } ?: 1
        return JBUI.scale(LANE * lanes.coerceIn(1, MAX_LANES) + PAD)
    }

    private val renderer = TableCellRenderer { table, _, selected, _, viewRow, _ ->
        GraphCell(rows().getOrNull(table.convertRowIndexToModel(viewRow)), selected, table)
    }

    private class GraphCell(
        private val row: LoreGraphLayout.Row?,
        private val selected: Boolean,
        private val table: JTable,
    ) : JComponent() {

        init {
            isOpaque = true
            background = if (selected) table.selectionBackground else table.background
        }

        override fun paintComponent(g: Graphics) {
            g.color = background
            g.fillRect(0, 0, width, height)

            val model = row ?: return
            val g2 = g as Graphics2D
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g2.stroke = BasicStroke(JBUI.scale(1.5f))

            val lane = JBUI.scale(LANE)
            val middle = height / 2

            // Edges first so a node always sits on top of its own lines.
            model.edges.forEach { edge ->
                g2.color = colour(edge.fromLane)
                val from = lane / 2 + edge.fromLane * lane
                val to = lane / 2 + edge.toLane * lane
                g2.drawLine(from, 0, from, middle)
                g2.drawLine(from, middle, to, height)
            }

            val centre = lane / 2 + model.lane * lane
            val radius = JBUI.scale(DOT)
            g2.color = colour(model.lane)
            g2.fillOval(centre - radius, middle - radius, radius * 2, radius * 2)
        }

        private fun colour(lane: Int): Color = LANE_COLOURS[lane % LANE_COLOURS.size]
    }

    private companion object {
        const val LANE = 14
        const val PAD = 8
        const val DOT = 4

        /** Beyond this the column would crowd out the message. */
        const val MAX_LANES = 8

        val LANE_COLOURS: List<Color> = listOf(
            JBColor(0x4A88C7, 0x548AF7),
            JBColor(0x7A3E9D, 0xB07DD8),
            JBColor(0x00875A, 0x499C54),
            JBColor(0xC7752A, 0xD9955B),
            JBColor(0xB0384A, 0xD1707C),
            JBColor(0x0F7B8A, 0x4CA6B5),
        )
    }
}
