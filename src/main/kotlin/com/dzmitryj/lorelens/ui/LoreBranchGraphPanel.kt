package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.ui.JBColor
import com.intellij.ui.PopupHandler
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.JBFont
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Point
import java.awt.RenderingHints
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.geom.Path2D
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JSlider

/**
 * The repository as swimlanes: one per branch, time left to right, a node per
 * revision badged with its author, and a curve wherever a branch was cut or
 * merged back.
 */
class LoreBranchGraphPanel(
    private val onSelect: (String) -> Unit,
    private val actions: () -> ActionGroup,
) : JPanel(BorderLayout()) {

    private var graph = LoreBranchGraphLayout.Graph(emptyList(), emptyList(), emptyList())
    private var current: String = ""

    private val canvas = Canvas()

    private val zoom = JSlider(MIN_ZOOM, MAX_ZOOM, DEFAULT_ZOOM).apply {
        toolTipText = LoreLensBundle.message("graph.zoom.tip")
        addChangeListener {
            canvas.revalidate()
            canvas.repaint()
        }
    }

    private val empty = JBLabel(LoreLensBundle.message("graph.empty")).apply {
        foreground = UIUtil.getContextHelpForeground()
        border = JBUI.Borders.empty(12)
    }

    init {
        add(
            JPanel(FlowLayout(FlowLayout.LEFT, JBUI.scale(6), 0)).apply {
                add(JBLabel(LoreLensBundle.message("graph.zoom")).apply {
                    foreground = UIUtil.getContextHelpForeground()
                })
                add(zoom)
            },
            BorderLayout.NORTH,
        )
        add(ScrollPaneFactory.createScrollPane(canvas, true), BorderLayout.CENTER)

        PopupHandler.installPopupMenu(canvas, actions(), "LoreLensBranchGraph")
        canvas.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(event: MouseEvent) {
                // Selecting on press, before the popup opens, so a right-click
                // menu always acts on the node under the cursor.
                canvas.nodeAt(event.point)?.let { onSelect(it.hash) }
            }
        })
    }

    fun show(graph: LoreBranchGraphLayout.Graph, currentBranch: String) {
        this.graph = graph
        this.current = currentBranch

        remove(empty)
        if (graph.nodes.isEmpty()) add(empty, BorderLayout.SOUTH)

        canvas.revalidate()
        canvas.repaint()
    }

    private fun scale(): Double = zoom.value / 100.0

    private inner class Canvas : JComponent() {

        init {
            isOpaque = true
            background = UIUtil.getListBackground()
        }

        override fun getPreferredSize(): Dimension {
            val step = step()
            return Dimension(
                LANE_LABEL + step * graph.columns.coerceAtLeast(1) + step,
                (laneHeight() * graph.lanes.size.coerceAtLeast(1)) + JBUI.scale(16),
            )
        }

        fun nodeAt(point: Point): LoreBranchGraphLayout.Node? {
            val radius = radius()
            return graph.nodes.firstOrNull { node ->
                val x = xOf(node)
                val y = yOf(node)
                point.distance(x.toDouble(), y.toDouble()) <= radius + JBUI.scale(2)
            }
        }

        override fun paintComponent(g: Graphics) {
            g.color = background
            g.fillRect(0, 0, width, height)

            val g2 = g as Graphics2D
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

            if (graph.nodes.isEmpty()) return

            paintLanes(g2)
            paintLinks(g2)
            paintNodes(g2)
        }

        private fun paintLanes(g2: Graphics2D) {
            g2.stroke = java.awt.BasicStroke(JBUI.scale(3) / 2f)

            graph.lanes.forEachIndexed { lane, name ->
                val y = laneY(lane)
                val colour = laneColour(lane)
                val nodes = graph.nodes.filter { it.lane == lane }
                if (nodes.isEmpty()) return@forEachIndexed

                g2.color = colour
                g2.drawLine(xOf(nodes.first()), y, xOf(nodes.last()), y)

                // The name rides on the lane's own line, as in a swimlane chart,
                // rather than in a column that would push the graph rightwards.
                val label = if (name == current) "$name ${LoreLensBundle.message("graph.current")}" else name
                g2.font = JBFont.small()
                val metrics = g2.fontMetrics
                val textWidth = metrics.stringWidth(label)
                val boxX = LANE_LABEL - textWidth - JBUI.scale(10)
                val boxY = y - metrics.height / 2 - JBUI.scale(1)

                g2.color = colour
                g2.fillRoundRect(
                    boxX,
                    boxY,
                    textWidth + JBUI.scale(8),
                    metrics.height + JBUI.scale(2),
                    JBUI.scale(6),
                    JBUI.scale(6),
                )
                g2.color = JBColor(Color.WHITE, Color.WHITE)
                g2.drawString(label, boxX + JBUI.scale(4), y + metrics.ascent / 2 - JBUI.scale(1))
            }
        }

        private fun paintLinks(g2: Graphics2D) {
            g2.stroke = java.awt.BasicStroke(JBUI.scale(3) / 2f)

            graph.links.forEach { link ->
                g2.color = laneColour(link.to.lane)
                val fromX = xOf(link.from).toDouble()
                val fromY = yOf(link.from).toDouble()
                val toX = xOf(link.to).toDouble()
                val toY = yOf(link.to).toDouble()

                // Right angles with a rounded corner: the line runs along the
                // lane it is leaving, turns once, and runs into the lane it is
                // joining. Reads as a track rather than a wire.
                val corner = JBUI.scale(6).toDouble()
                val turn = (toX - corner).coerceAtLeast(fromX)
                val down = if (toY > fromY) corner else -corner

                val path = Path2D.Double()
                path.moveTo(fromX, fromY)
                path.lineTo(turn - corner, fromY)
                path.quadTo(turn, fromY, turn, fromY + down)
                path.lineTo(turn, toY - down)
                path.quadTo(turn, toY, turn + corner, toY)
                path.lineTo(toX, toY)
                g2.draw(path)
            }
        }

        private fun paintNodes(g2: Graphics2D) {
            val radius = radius()
            val showBadges = radius >= JBUI.scale(7)

            graph.nodes.forEach { node ->
                val x = xOf(node)
                val y = yOf(node)
                val colour = LoreAuthorColours.colourOf(node.author)

                g2.color = colour
                g2.fillOval(x - radius, y - radius, radius * 2, radius * 2)

                // Merges get a ring so they read without reading the message.
                if (node.isMerge) {
                    g2.color = JBColor(Color.WHITE, Color.WHITE)
                    g2.stroke = java.awt.BasicStroke(JBUI.scale(3) / 2f)
                    g2.drawOval(x - radius, y - radius, radius * 2, radius * 2)
                }

                if (!showBadges) return@forEach

                g2.color = JBColor(Color.WHITE, Color.WHITE)
                g2.font = JBFont.small()
                val initial = LoreAuthorColours.initialOf(node.author)
                val metrics = g2.fontMetrics
                g2.drawString(
                    initial,
                    x - metrics.stringWidth(initial) / 2,
                    y + metrics.ascent / 2 - JBUI.scale(1),
                )
            }
        }

        private fun step(): Int = (JBUI.scale(STEP) * scale()).toInt().coerceAtLeast(JBUI.scale(3))

        private fun radius(): Int = (JBUI.scale(RADIUS) * scale()).toInt().coerceAtLeast(JBUI.scale(2))

        private fun laneHeight(): Int =
            (JBUI.scale(LANE_HEIGHT) * scale()).toInt().coerceAtLeast(JBUI.scale(10))

        private fun laneY(lane: Int): Int = JBUI.scale(8) + laneHeight() / 2 + lane * laneHeight()

        private fun xOf(node: LoreBranchGraphLayout.Node): Int = LANE_LABEL + node.column * step()

        private fun yOf(node: LoreBranchGraphLayout.Node): Int = laneY(node.lane)

        private fun laneColour(lane: Int): Color = LANE_COLOURS[lane % LANE_COLOURS.size]
    }

    private companion object {
        const val STEP = 22
        const val RADIUS = 9
        const val LANE_HEIGHT = 44

        const val MIN_ZOOM = 25
        const val MAX_ZOOM = 200
        const val DEFAULT_ZOOM = 100

        val LANE_LABEL = JBUI.scale(140)

        val LANE_COLOURS: List<Color> = listOf(
            JBColor(0x5B8C3E, 0x6FA85A),
            JBColor(0x6A4BA8, 0x8B6FD0),
            JBColor(0xA8752A, 0xC79A50),
            JBColor(0xA83E5B, 0xC76B84),
            JBColor(0x2A7EA8, 0x54A2C7),
            JBColor(0x2F7D6A, 0x57A392),
        )
    }
}
