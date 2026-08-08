package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.intellij.ui.JBColor
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.JBFont
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.awt.BasicStroke
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Cursor
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Rectangle
import java.awt.RenderingHints
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import java.awt.event.MouseWheelEvent
import java.awt.geom.Path2D
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JSlider
import javax.swing.SwingUtilities

/**
 * The repository as swimlanes: one per branch, time left to right, a node per
 * revision badged with its author, and a turn wherever a branch was cut or
 * merged back.
 *
 * Navigation is the point as much as the drawing: the graph is wider than any
 * window, so it pans by dragging empty space and zooms about the cursor.
 */
class LoreBranchGraphPanel(
    private val onSelectCommit: (LoreBranchGraphLayout.Node?) -> Unit,
    private val onCommitMenu: (LoreBranchGraphLayout.Node, Point) -> Unit,
    private val onBranchMenu: (String, Point) -> Unit,
) : JPanel(BorderLayout()) {

    private var graph = LoreBranchGraphLayout.Graph(emptyList(), emptyList(), emptyList())
    private var current: String = ""
    private var selected: String? = null
    private var hovered: String? = null

    private val canvas = Canvas()
    private val scroll: JScrollPane = ScrollPaneFactory.createScrollPane(canvas, true)

    private val zoom = JSlider(MIN_ZOOM, MAX_ZOOM, DEFAULT_ZOOM).apply {
        toolTipText = LoreLensBundle.message("graph.zoom.tip")
        addChangeListener { rescale() }
    }

    private val empty = JBLabel(LoreLensBundle.message("graph.empty")).apply {
        foreground = UIUtil.getContextHelpForeground()
        border = JBUI.Borders.empty(12)
    }

    init {
        add(
            JPanel(FlowLayout(FlowLayout.LEFT, JBUI.scale(6), 0)).apply {
                add(
                    JBLabel(LoreLensBundle.message("graph.zoom")).apply {
                        foreground = UIUtil.getContextHelpForeground()
                    },
                )
                add(zoom)
                add(
                    JBLabel(LoreLensBundle.message("graph.hint")).apply {
                        foreground = UIUtil.getContextHelpForeground()
                        font = JBFont.small()
                    },
                )
            },
            BorderLayout.NORTH,
        )
        add(scroll, BorderLayout.CENTER)
        canvas.install()
    }

    fun show(graph: LoreBranchGraphLayout.Graph, currentBranch: String) {
        this.graph = graph
        this.current = currentBranch
        selected = null
        hovered = null

        remove(empty)
        if (graph.nodes.isEmpty()) add(empty, BorderLayout.SOUTH)

        canvas.revalidate()
        canvas.repaint()
    }

    fun select(hash: String?) {
        selected = hash
        canvas.repaint()
    }

    private fun scale(): Double = zoom.value / 100.0

    /** Keeps whatever is under the middle of the view there after a rescale. */
    private fun rescale(anchor: Point? = null) {
        val view = scroll.viewport
        val focus = anchor ?: Point(
            view.viewPosition.x + view.width / 2,
            view.viewPosition.y + view.height / 2,
        )
        val before = canvas.preferredSize
        canvas.revalidate()
        canvas.repaint()

        SwingUtilities.invokeLater {
            val after = canvas.preferredSize
            if (before.width <= 0 || after.width <= 0) return@invokeLater

            val ratioX = after.width.toDouble() / before.width
            val ratioY = after.height.toDouble() / before.height
            val x = (focus.x * ratioX - view.width / 2).toInt().coerceAtLeast(0)
            val y = (focus.y * ratioY - view.height / 2).toInt().coerceAtLeast(0)
            view.viewPosition = Point(x, y)
        }
    }

    private inner class Canvas : JComponent() {

        private var dragFrom: Point? = null

        init {
            isOpaque = true
            background = UIUtil.getListBackground()
        }

        fun install() {
            addMouseListener(object : MouseAdapter() {
                override fun mousePressed(event: MouseEvent) {
                    val node = nodeAt(event.point)

                    if (SwingUtilities.isRightMouseButton(event)) {
                        // Right-click acts on what is under the cursor, so the
                        // selection moves there first.
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
                    val view = scroll.viewport
                    val at = view.viewPosition
                    view.viewPosition = Point(
                        (at.x + from.x - event.x).coerceIn(0, maxOf(0, width - view.width)),
                        (at.y + from.y - event.y).coerceIn(0, maxOf(0, height - view.height)),
                    )
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
                when {
                    // Zoom about the cursor: zooming about the origin is useless
                    // once the graph is wider than the window.
                    event.isControlDown -> {
                        val focus = Point(event.x, event.y)
                        val step = if (event.wheelRotation < 0) ZOOM_STEP else -ZOOM_STEP
                        zoom.value = (zoom.value + step).coerceIn(MIN_ZOOM, MAX_ZOOM)
                        rescale(focus)
                    }

                    event.isShiftDown -> scrollBy(event.unitsToScroll * JBUI.scale(12), 0)
                    else -> scrollBy(0, event.unitsToScroll * JBUI.scale(12))
                }
            }
        }

        private fun scrollBy(dx: Int, dy: Int) {
            val view = scroll.viewport
            val at = view.viewPosition
            view.viewPosition = Point(
                (at.x + dx).coerceIn(0, maxOf(0, width - view.width)),
                (at.y + dy).coerceIn(0, maxOf(0, height - view.height)),
            )
        }

        override fun getPreferredSize(): Dimension {
            val step = step()
            return Dimension(
                LANE_LABEL + step * graph.columns.coerceAtLeast(1) + step,
                (laneHeight() * graph.lanes.size.coerceAtLeast(1)) + JBUI.scale(16),
            )
        }

        fun nodeAt(point: Point): LoreBranchGraphLayout.Node? {
            val reach = radius() + JBUI.scale(3)
            return graph.nodes.firstOrNull { node ->
                point.distance(xOf(node).toDouble(), yOf(node).toDouble()) <= reach
            }
        }

        /** Which lane's label, if any, the point falls on. */
        fun laneAt(point: Point): String? {
            if (point.x > LANE_LABEL) return null
            val lane = ((point.y - JBUI.scale(8)) / laneHeight().coerceAtLeast(1))
            return graph.lanes.getOrNull(lane)
        }

        override fun paintComponent(g: Graphics) {
            g.color = background
            g.fillRect(0, 0, width, height)

            val g2 = g as Graphics2D
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

            if (graph.nodes.isEmpty()) return

            val clip = g2.clipBounds ?: Rectangle(0, 0, width, height)

            paintLaneLines(g2)
            paintLinks(g2, clip)
            paintNodes(g2, clip)
            // Labels last and anchored to the viewport, so a lane can still be
            // identified after scrolling a long way right.
            paintLaneLabels(g2)
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

        private fun paintLaneLabels(g2: Graphics2D) {
            val left = scroll.viewport.viewPosition.x

            g2.font = JBFont.small()
            val metrics = g2.fontMetrics

            graph.lanes.forEachIndexed { lane, name ->
                val y = laneY(lane)
                val label = if (name == current) {
                    "$name ${LoreLensBundle.message("graph.current")}"
                } else {
                    name
                }
                val width = metrics.stringWidth(label)
                val x = left + JBUI.scale(6)

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

        private fun paintLinks(g2: Graphics2D, clip: Rectangle) {
            g2.stroke = BasicStroke(JBUI.scale(3) / 2f)
            val corner = JBUI.scale(6).toDouble()

            graph.links.forEach { link ->
                val fromX = xOf(link.from).toDouble()
                val toX = xOf(link.to).toDouble()
                if (maxOf(fromX, toX) < clip.x || minOf(fromX, toX) > clip.x + clip.width) return@forEach

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

        private fun paintNodes(g2: Graphics2D, clip: Rectangle) {
            val radius = radius()
            val showBadges = radius >= JBUI.scale(7)

            graph.nodes.forEach { node ->
                val x = xOf(node)
                if (x + radius < clip.x || x - radius > clip.x + clip.width) return@forEach

                val y = yOf(node)
                val emphasis = node.hash == hovered || node.hash == selected
                val size = if (emphasis) radius + JBUI.scale(2) else radius

                g2.color = LoreAuthorColours.colourOf(node.author)
                g2.fillOval(x - size, y - size, size * 2, size * 2)

                if (node.isMerge || emphasis) {
                    g2.color = if (node.hash == selected) {
                        UIUtil.getListSelectionBackground(true)
                    } else {
                        JBColor(Color.WHITE, Color.WHITE)
                    }
                    g2.stroke = BasicStroke(JBUI.scale(3) / 2f)
                    g2.drawOval(x - size, y - size, size * 2, size * 2)
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
        const val ZOOM_STEP = 10

        const val MIN_ZOOM = 25
        const val MAX_ZOOM = 200
        const val DEFAULT_ZOOM = 100

        val LANE_LABEL = JBUI.scale(24)

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
