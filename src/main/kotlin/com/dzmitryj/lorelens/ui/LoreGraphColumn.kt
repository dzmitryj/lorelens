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

    /**
     * Fixed rather than measured. Scanning every row to find the widest ran on
     * each layout pass, and the lanes are capped anyway.
     */
    override fun getWidth(table: JTable): Int =
        JBUI.scale(LANE * LoreHistoryLanes.MAX_LANES + PAD)

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

            // Every lane with a line running through this row, so a branch
            // reads as a continuous strand rather than appearing only where it
            // was merged.
            model.occupied.forEach { occupied ->
                g2.color = laneColour(occupied)
                val x = lane / 2 + occupied * lane
                g2.drawLine(x, 0, x, height)
            }

            // Turns leaving this node for a parent on another lane: the branch
            // point, drawn below the node because that is where it goes.
            model.joins.forEach { join ->
                g2.color = laneColour(join.toLane)
                val from = lane / 2 + join.fromLane * lane
                val to = lane / 2 + join.toLane * lane

                val path = Path2D.Double()
                path.moveTo(from.toDouble(), middle.toDouble())
                path.lineTo(from.toDouble(), (middle + CORNER).toDouble())
                path.quadTo(
                    from.toDouble(),
                    (middle + CORNER * 2).toDouble(),
                    (from + (to - from).coerceIn(-CORNER, CORNER)).toDouble(),
                    (middle + CORNER * 2).toDouble(),
                )
                path.lineTo((to - (to - from).coerceIn(-CORNER, CORNER)).toDouble(), (middle + CORNER * 2).toDouble())
                path.quadTo(to.toDouble(), (middle + CORNER * 2).toDouble(), to.toDouble(), (middle + CORNER * 3).toDouble())
                path.lineTo(to.toDouble(), height.toDouble())
                g2.draw(path)
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
        val CORNER = JBUI.scale(5)

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
