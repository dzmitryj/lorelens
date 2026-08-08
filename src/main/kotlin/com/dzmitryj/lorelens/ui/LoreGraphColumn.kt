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
    private val rows: () -> List<LoreHistoryLanes.Row>,
    private val authorOf: (Int) -> String?,
    private val isMerge: (Int) -> Boolean,
) : ColumnInfo<LogRow, LogRow>("") {

    override fun valueOf(item: LogRow): LogRow = item

    override fun getRenderer(item: LogRow?): TableCellRenderer = renderer

    private var measured: List<LoreHistoryLanes.Row>? = null
    private var lanes = 1

    /**
     * As wide as the lanes in use, not as wide as the cap. Reserving the cap
     * left most of the column empty and squeezed out everything to its right.
     * Measured once per layout: the supplier hands back a new list each time.
     */
    override fun getWidth(table: JTable): Int {
        val laid = rows()
        if (laid !== measured) {
            measured = laid
            lanes = (laid.maxOfOrNull { it.width } ?: 1).coerceIn(1, LoreHistoryLanes.MAX_LANES)
        }
        return JBUI.scale(LANE * lanes + PAD)
    }

    // One component, reconfigured per cell. A renderer that allocates is a
    // renderer that runs the garbage collector during scrolling.
    private val cell = GraphCell()

    private val renderer = TableCellRenderer { table, _, selected, _, viewRow, _ ->
        val index = table.convertRowIndexToModel(viewRow)
        cell.configure(rows().getOrNull(index), authorOf(index), isMerge(index), selected, table)
        cell
    }

    private class GraphCell : JComponent() {

        private var row: LoreHistoryLanes.Row? = null
        private var author: String? = null
        private var merge: Boolean = false

        init {
            isOpaque = true
        }

        fun configure(
            row: LoreHistoryLanes.Row?,
            author: String?,
            merge: Boolean,
            selected: Boolean,
            table: JTable,
        ) {
            this.row = row
            this.author = author
            this.merge = merge
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

            fun x(at: Int) = (lane / 2 + at * lane).toDouble()

            // A strand crossing the row untouched. Drawn edge to edge, so it
            // meets the same lane in the rows above and below.
            model.through.forEach { crossing ->
                g2.color = laneColour(crossing)
                g2.drawLine(x(crossing).toInt(), 0, x(crossing).toInt(), height)
            }

            // Half a cell at a time: the top edge down to the node, then the
            // node down to the bottom edge. Both halves land on the boundary,
            // which is what keeps the line whole from row to row.
            fun strand(segment: LoreHistoryLanes.Segment, from: Double, to: Double) {
                g2.color = laneColour(segment.colour)
                val start = x(segment.from)
                val end = x(segment.to)
                if (start == end) {
                    g2.drawLine(start.toInt(), from.toInt(), start.toInt(), to.toInt())
                    return
                }

                val bend = from + (to - from) / 2
                val path = Path2D.Double()
                path.moveTo(start, from)
                path.curveTo(start, bend, end, bend, end, to)
                g2.draw(path)
            }

            model.incoming.forEach { strand(it, 0.0, middle.toDouble()) }
            model.outgoing.forEach { strand(it, middle.toDouble(), height.toDouble()) }

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
