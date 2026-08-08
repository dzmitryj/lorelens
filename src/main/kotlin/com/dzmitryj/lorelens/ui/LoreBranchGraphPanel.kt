package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.JBFont
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.awt.BasicStroke
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Cursor
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Point
import java.awt.RenderingHints
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import java.awt.geom.Path2D
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingUtilities

/**
 * The repository as swimlanes: one per branch, time left to right, a node per
 * revision badged with its author, and a turn wherever a branch was cut or
 * merged back.
 *
 * There is no scroll pane. The graph is always wider than the window, and
 * dragging it is a better fit than scrollbars, so the canvas owns its own pan
 * offset and zoom. Reset Focus in the toolbar puts it back.
 */
class LoreBranchGraphPanel(
    private val onSelectCommit: (LoreBranchGraphLayout.Node?) -> Unit,
    private val onCommitMenu: (LoreBranchGraphLayout.Node, Point) -> Unit,
    private val onBranchMenu: (String, Point) -> Unit,
) : JPanel(BorderLayout()) {

    private var graph = LoreBranchGraphLayout.Graph(emptyList(), emptyList(), emptyList())
    private var current: String = ""
    private var currentRevision: String? = null
    private var selected: String? = null
    private var hovered: String? = null

    private val canvas = Canvas()

    private val empty = JBLabel(LoreLensBundle.message("graph.empty")).apply {
        foreground = UIUtil.getContextHelpForeground()
        border = JBUI.Borders.empty(12)
    }

    init {
        add(canvas, BorderLayout.CENTER)
        canvas.install()
    }

    fun show(
        graph: LoreBranchGraphLayout.Graph,
        currentBranch: String,
        currentRevision: String?,
    ) {
        this.graph = graph
        this.current = currentBranch
        this.currentRevision = currentRevision
        selected = null
        hovered = null

        remove(empty)
        if (graph.nodes.isEmpty()) add(empty, BorderLayout.SOUTH)

        resetFocus()
    }

    /**
     * Back to the start of history at a readable size. Without scrollbars there
     * is no other way back once the graph has been dragged somewhere odd.
     */
    fun resetFocus() {
        canvas.reset()
    }

    private inner class Canvas : JComponent() {

        private var zoom = 1.0
        private var panX = 0
        private var panY = 0
        private var dragFrom: Point? = null

        init {
            isOpaque = true
            background = UIUtil.getListBackground()
        }

        fun reset() {
            zoom = 1.0
            panX = 0
            panY = 0
            repaint()
        }

        fun install() {
            addMouseListener(object : MouseAdapter() {
                override fun mousePressed(event: MouseEvent) {
                    val node = nodeAt(event.point)

                    if (SwingUtilities.isRightMouseButton(event)) {
                        when {
                            node != null -> {
                                selected = node.hash
                                onSelectCommit(node)
                                repaint()
                                onCommitMenu(node, event.point)
                            }

                            else -> laneAt(event.point)?.let { onBranchMenu(it, event.point) }
                        }
                        return
                    }

                    if (node != null) {
                        selected = node.hash
                        onSelectCommit(node)
                        repaint()
                        return
                    }

                    // Empty space is the pan handle; dragging a node would fight
                    // the click that selects it.
                    dragFrom = event.point
                    cursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR)
                }

                override fun mouseReleased(event: MouseEvent) {
                    dragFrom = null
                    cursor = Cursor.getDefaultCursor()
                }

                override fun mouseExited(event: MouseEvent) {
                    hovered = null
                    repaint()
                }
            })

            addMouseMotionListener(object : MouseMotionAdapter() {
                override fun mouseDragged(event: MouseEvent) {
                    val from = dragFrom ?: return
                    panX += event.x - from.x
                    panY += event.y - from.y
                    dragFrom = event.point
                    repaint()
                }

                override fun mouseMoved(event: MouseEvent) {
                    val node = nodeAt(event.point)
                    if (node?.hash == hovered) return

                    hovered = node?.hash
                    toolTipText = node?.let { "r${it.number}  ${it.author.orEmpty()}" }
                    cursor = if (node != null) {
                        Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                    } else {
                        Cursor.getDefaultCursor()
                    }
                    repaint()
                }
            })

            addMouseWheelListener { event ->
                // Zoom about the cursor: about the origin it is useless once the
                // graph runs past the edge of the window.
                val before = zoom
                val step = if (event.wheelRotation < 0) ZOOM_STEP else 1 / ZOOM_STEP
                zoom = (zoom * step).coerceIn(MIN_ZOOM, MAX_ZOOM)
                if (zoom == before) return@addMouseWheelListener

                val ratio = zoom / before
                panX = (event.x - (event.x - panX) * ratio).toInt()
                panY = (event.y - (event.y - panY) * ratio).toInt()
                repaint()
            }
        }

        fun nodeAt(point: Point): LoreBranchGraphLayout.Node? {
            if (point.x < gutter()) return null
            val reach = radius() + JBUI.scale(3)
            return graph.nodes.firstOrNull { node ->
                point.distance(xOf(node).toDouble(), yOf(node).toDouble()) <= reach
            }
        }

        /** Which lane's pill, if any, the point falls on. */
        fun laneAt(point: Point): String? {
            if (point.x > gutter()) return null
            val lane = (point.y - panY - JBUI.scale(8)) / laneHeight().coerceAtLeast(1)
            return graph.lanes.getOrNull(lane)
        }

        override fun paintComponent(g: Graphics) {
            g.color = background
            g.fillRect(0, 0, width, height)

            val g2 = g as Graphics2D
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

            if (graph.nodes.isEmpty()) return

            // The graph is clipped to the right of the gutter so nothing can
            // slide under the branch pills, which is what made them unreadable.
            val gutter = gutter()
            g2.clip = java.awt.Rectangle(gutter, 0, width - gutter, height)
            paintLaneLines(g2)
            paintLinks(g2)
            paintNodes(g2)

            g2.clip = null
            paintPills(g2)
        }

        private fun paintLaneLines(g2: Graphics2D) {
            g2.stroke = BasicStroke(JBUI.scale(3) / 2f)

            graph.lanes.indices.forEach { lane ->
                val nodes = graph.nodes.filter { it.lane == lane }
                if (nodes.isEmpty()) return@forEach

                g2.color = laneColour(lane)
                val y = laneY(lane)
                g2.drawLine(xOf(nodes.first()), y, xOf(nodes.last()), y)
            }
        }

        /** Pinned in their own column, which the graph never draws into. */
        private fun paintPills(g2: Graphics2D) {
            val gutter = gutter()
            g2.color = background
            g2.fillRect(0, 0, gutter, height)

            g2.font = JBFont.small()
            val metrics = g2.fontMetrics

            graph.lanes.forEachIndexed { lane, name ->
                val y = laneY(lane)
                if (y < -laneHeight() || y > height + laneHeight()) return@forEachIndexed

                val label = if (name == current) {
                    "$name ${LoreLensBundle.message("graph.current")}"
                } else {
                    name
                }
                val width = metrics.stringWidth(label)
                val x = JBUI.scale(6)

                g2.color = laneColour(lane)
                g2.fillRoundRect(
                    x,
                    y - metrics.height / 2 - JBUI.scale(1),
                    width + JBUI.scale(10),
                    metrics.height + JBUI.scale(2),
                    JBUI.scale(6),
                    JBUI.scale(6),
                )
                g2.color = JBColor(Color.WHITE, Color.WHITE)
                g2.drawString(label, x + JBUI.scale(5), y + metrics.ascent / 2 - JBUI.scale(1))
            }
        }

        private fun paintLinks(g2: Graphics2D) {
            g2.stroke = BasicStroke(JBUI.scale(3) / 2f)
            val corner = JBUI.scale(6).toDouble()

            graph.links.forEach { link ->
                val fromX = xOf(link.from).toDouble()
                val toX = xOf(link.to).toDouble()
                if (maxOf(fromX, toX) < 0 || minOf(fromX, toX) > width) return@forEach

                val fromY = yOf(link.from).toDouble()
                val toY = yOf(link.to).toDouble()
                g2.color = laneColour(link.to.lane)

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
                if (x + radius < 0 || x - radius > width) return@forEach

                val y = yOf(node)
                if (y + radius < 0 || y - radius > height) return@forEach

                val emphasis = node.hash == hovered || node.hash == selected
                val size = if (emphasis) radius + JBUI.scale(2) else radius

                g2.color = LoreAuthorColours.colourOf(node.author)
                g2.fillOval(x - size, y - size, size * 2, size * 2)

                // A merge is a donut: distinct at a glance, still carries the
                // author colour, and leaves the white ring free to mean one
                // thing only.
                if (node.isMerge) {
                    val hole = (size * 0.45).toInt().coerceAtLeast(JBUI.scale(2))
                    g2.color = background
                    g2.fillOval(x - hole, y - hole, hole * 2, hole * 2)
                }

                g2.stroke = BasicStroke(JBUI.scale(3) / 2f)
                when {
                    // White means one thing: this is where the checkout sits.
                    node.hash == currentRevision -> {
                        g2.color = JBColor(Color.WHITE, Color.WHITE)
                        g2.drawOval(x - size - 1, y - size - 1, size * 2 + 2, size * 2 + 2)
                    }

                    node.hash == selected -> {
                        g2.color = UIUtil.getListSelectionBackground(true)
                        g2.drawOval(x - size, y - size, size * 2, size * 2)
                    }
                }

                if (!showBadges || node.isMerge) return@forEach

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

        private fun gutter(): Int = JBUI.scale(GUTTER)

        private fun step(): Int = (JBUI.scale(STEP) * zoom).toInt().coerceAtLeast(JBUI.scale(3))

        private fun radius(): Int = (JBUI.scale(RADIUS) * zoom).toInt().coerceAtLeast(JBUI.scale(2))

        private fun laneHeight(): Int =
            (JBUI.scale(LANE_HEIGHT) * zoom).toInt().coerceAtLeast(JBUI.scale(10))

        private fun laneY(lane: Int): Int =
            panY + JBUI.scale(8) + laneHeight() / 2 + lane * laneHeight()

        private fun xOf(node: LoreBranchGraphLayout.Node): Int =
            gutter() + JBUI.scale(12) + panX + node.column * step()

        private fun yOf(node: LoreBranchGraphLayout.Node): Int = laneY(node.lane)

        private fun laneColour(lane: Int): Color = LANE_COLOURS[lane % LANE_COLOURS.size]
    }

    private companion object {
        const val STEP = 22
        const val RADIUS = 9
        const val LANE_HEIGHT = 44
        const val GUTTER = 110

        const val ZOOM_STEP = 1.1
        const val MIN_ZOOM = 0.25
        const val MAX_ZOOM = 3.0

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
