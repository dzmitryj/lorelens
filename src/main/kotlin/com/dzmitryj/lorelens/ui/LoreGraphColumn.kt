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
        return JBUI.scale(LANE * lanes + MARGIN * 2)
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

            // A margin either side, so the first and last lanes' nodes are not
            // pressed against the column edges.
            fun x(at: Int) = (JBUI.scale(MARGIN) + lane / 2 + at * lane).toDouble()

            // A strand crossing the row untouched. Drawn edge to edge, so it
            // meets the same lane in the rows above and below.
            model.through.forEach { crossing ->
                g2.color = laneColour(crossing)
                g2.drawLine(x(crossing).toInt(), 0, x(crossing).toInt(), height)
            }

            // Half a cell at a time: the top edge down to the node, then the
            // node down to the bottom edge. Both halves land on the boundary,
            // which is what keeps the line whole from row to row. Lane changes
            // are straight runs with one rounded corner, the same shape the
            // branch graph draws, rather than a full-height curve.
            fun vertical(at: Double, from: Double, to: Double) =
                g2.drawLine(at.toInt(), from.toInt(), at.toInt(), to.toInt())

            model.incoming.forEach { segment ->
                g2.color = laneColour(segment.colour)
                val start = x(segment.from)
                val end = x(segment.to)
                if (start == end) {
                    vertical(start, 0.0, middle.toDouble())
                    return@forEach
                }

                // Down the incoming lane, one rounded turn, horizontally into
                // the node at its centre line.
                val corner = corner(start, end, middle.toDouble())
                val towards = if (end > start) corner else -corner
                val path = Path2D.Double()
                path.moveTo(start, 0.0)
                path.lineTo(start, middle - corner)
                path.quadTo(start, middle.toDouble(), start + towards, middle.toDouble())
                path.lineTo(end, middle.toDouble())
                g2.draw(path)
            }

            model.outgoing.forEach { segment ->
                g2.color = laneColour(segment.colour)
                val start = x(segment.from)
                val end = x(segment.to)
                if (start == end) {
                    vertical(start, middle.toDouble(), height.toDouble())
                    return@forEach
                }

                // Horizontally out of the node, one rounded turn, down the
                // parent's lane to the bottom edge.
                val corner = corner(start, end, (height - middle).toDouble())
                val towards = if (end > start) corner else -corner
                val path = Path2D.Double()
                path.moveTo(start, middle.toDouble())
                path.lineTo(end - towards, middle.toDouble())
                path.quadTo(end, middle.toDouble(), end, middle + corner)
                path.lineTo(end, height.toDouble())
                g2.draw(path)
            }

            val centre = x(model.lane).toInt()
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

        /** As round as the room between the lanes and the row edge allows. */
        private fun corner(start: Double, end: Double, room: Double): Double =
            minOf(JBUI.scale(5).toDouble(), Math.abs(end - start) / 2, room)
    }

    private companion object {
        const val LANE = 20
        const val MARGIN = 8
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
