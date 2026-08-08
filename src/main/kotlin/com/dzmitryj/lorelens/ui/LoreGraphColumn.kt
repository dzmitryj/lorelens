package com.dzmitryj.lorelens.ui

import com.intellij.ui.JBColor
import com.intellij.util.ui.ColumnInfo
import com.intellij.util.ui.JBFont
import com.intellij.util.ui.JBUI
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.geom.Path2D
import javax.swing.JComponent
import javax.swing.JTable
import javax.swing.table.TableCellRenderer

/**
 * The shape of the history beside it: a node per revision carrying who wrote it,
 * and a curve per lane, so a merge reads as two strands joining.
 */
class LoreGraphColumn(
    private val rows: () -> List<LoreGraphLayout.Row>,
    private val authorOf: (Int) -> String?,
    private val isMerge: (Int) -> Boolean,
) : ColumnInfo<LogRow, LogRow>("") {

    override fun valueOf(item: LogRow): LogRow = item

    override fun getRenderer(item: LogRow?): TableCellRenderer = renderer

    /** Sized to the widest row so the lanes never clip. */
    override fun getWidth(table: JTable): Int {
        val lanes = rows().maxOfOrNull { it.width } ?: 1
        return JBUI.scale(LANE * lanes.coerceIn(1, MAX_LANES) + PAD)
    }

    private val renderer = TableCellRenderer { table, _, selected, _, viewRow, _ ->
        val index = table.convertRowIndexToModel(viewRow)
        GraphCell(rows().getOrNull(index), authorOf(index), isMerge(index), selected, table)
    }

    private class GraphCell(
        private val row: LoreGraphLayout.Row?,
        private val author: String?,
        private val merge: Boolean,
        selected: Boolean,
        table: JTable,
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
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
            g2.stroke = BasicStroke(JBUI.scale(3) / 2f)

            val lane = JBUI.scale(LANE)
            val middle = height / 2

            // Edges first so a node always sits on top of its own lines.
            model.edges.forEach { edge ->
                g2.color = laneColour(edge.fromLane)
                val from = (lane / 2 + edge.fromLane * lane).toDouble()
                val to = (lane / 2 + edge.toLane * lane).toDouble()

                if (from == to) {
                    g2.drawLine(from.toInt(), 0, to.toInt(), height)
                } else {
                    // A curve rather than a dogleg: the bend is what makes a
                    // fork read as leaving its lane.
                    val path = Path2D.Double()
                    path.moveTo(from, 0.0)
                    path.curveTo(from, middle.toDouble(), to, middle.toDouble(), to, height.toDouble())
                    g2.draw(path)
                }
            }

            val centre = lane / 2 + model.lane * lane
            val radius = JBUI.scale(NODE)

            g2.color = LoreAuthorColours.colourOf(author)
            g2.fillOval(centre - radius, middle - radius, radius * 2, radius * 2)

            if (merge) {
                g2.color = JBColor(Color.WHITE, Color.WHITE)
                g2.drawOval(centre - radius, middle - radius, radius * 2, radius * 2)
            }

            g2.color = JBColor(Color.WHITE, Color.WHITE)
            g2.font = JBFont.small()
            val initial = LoreAuthorColours.initialOf(author)
            val metrics = g2.fontMetrics
            g2.drawString(
                initial,
                centre - metrics.stringWidth(initial) / 2,
                middle + metrics.ascent / 2 - JBUI.scale(1),
            )
        }

        private fun laneColour(lane: Int): Color = LANE_COLOURS[lane % LANE_COLOURS.size]
    }

    private companion object {
        const val LANE = 20
        const val PAD = 10
        const val NODE = 7

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
