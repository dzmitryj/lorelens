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

    /**
     * Fixed rather than measured. Scanning every row to find the widest ran on
     * each layout pass, and the lanes are capped anyway.
     */
    override fun getWidth(table: JTable): Int =
        JBUI.scale(LANE * LoreGraphLayout.MAX_LANES + PAD)

    // One component, reconfigured per cell. A renderer that allocates is a
    // renderer that runs the garbage collector during scrolling.
    private val cell = GraphCell()

    private val renderer = TableCellRenderer { table, _, selected, _, viewRow, _ ->
        val index = table.convertRowIndexToModel(viewRow)
        cell.configure(rows().getOrNull(index), authorOf(index), isMerge(index), selected, table)
        cell
    }

    private class GraphCell : JComponent() {

        private var row: LoreGraphLayout.Row? = null
        private var author: String? = null
        private var merge: Boolean = false

        init {
            isOpaque = true
        }

        fun configure(
            row: LoreGraphLayout.Row?,
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

            // Only lines that touch this row: its own lane carrying through,
            // and anything joining or leaving it. Drawing a through-line for
            // every branch that happens to be open is what made this a barcode.
            model.edges.forEach { edge ->
                val joins = edge.fromLane == model.lane || edge.toLane == model.lane
                if (!joins && edge.fromLane != edge.toLane) return@forEach

                g2.color = laneColour(edge.fromLane)
                val from = lane / 2 + edge.fromLane * lane
                val to = lane / 2 + edge.toLane * lane

                if (from == to) {
                    g2.drawLine(from, 0, to, height)
                } else {
                    // Right angles with a rounded corner, which reads as a track
                    // rather than a wire and costs a fraction of a curve.
                    val path = Path2D.Double()
                    path.moveTo(from.toDouble(), 0.0)
                    path.lineTo(from.toDouble(), (middle - CORNER).toDouble())
                    path.quadTo(from.toDouble(), middle.toDouble(), (from + (to - from).coerceIn(-CORNER, CORNER)).toDouble(), middle.toDouble())
                    path.lineTo((to - (to - from).coerceIn(-CORNER, CORNER)).toDouble(), middle.toDouble())
                    path.quadTo(to.toDouble(), middle.toDouble(), to.toDouble(), (middle + CORNER).toDouble())
                    path.lineTo(to.toDouble(), height.toDouble())
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
