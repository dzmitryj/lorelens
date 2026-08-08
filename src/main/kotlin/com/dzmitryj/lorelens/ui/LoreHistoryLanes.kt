package com.dzmitryj.lorelens.ui

/**
 * Lanes for the History column, one per branch.
 *
 * A lane is a branch, for the whole view: every revision sits in its branch's
 * lane, and the lane's colour is the branch's colour everywhere it appears.
 * The first-free-slot model reused lanes as strands opened and closed, so the
 * same branch wandered across columns and a merge said nothing about who
 * merged into whom; here the direction is the picture -- an edge between two
 * branches runs down the deeper branch's lane and curves into the other end.
 *
 * Pure: no Swing, so the arithmetic is testable.
 */
object LoreHistoryLanes {

    /** A revision in display order, newest first, children before parents. */
    data class Input(val hash: String, val parents: List<String>, val branch: String?)

    /** A line within one row, running between lane [from] and lane [to]. */
    data class Segment(val from: Int, val to: Int) {
        /** An edge is named after its deeper branch: the one it belongs to. */
        val colour: Int get() = maxOf(from, to)
    }

    /**
     * @param lane the lane this row's node sits in.
     * @param incoming lines from the top edge down to the node's centre.
     * @param outgoing lines from the node's centre down to the bottom edge.
     * @param through lanes crossing the whole row without touching the node.
     * @param width lanes this row needs, so the column can size to the widest.
     */
    data class Row(
        val lane: Int,
        val incoming: List<Segment>,
        val outgoing: List<Segment>,
        val through: List<Int>,
        val width: Int,
    )

    /** The rows, and which branch each lane belongs to. */
    data class Layout(val rows: List<Row>, val branches: List<String?>) {
        companion object {
            val EMPTY = Layout(emptyList(), emptyList())
        }
    }

    /**
     * @param order branch names in hierarchy order -- main first, children
     *   after their parents -- which fixes each branch's lane and colour.
     */
    fun layout(rows: List<Input>, order: List<String>, maxLanes: Int = MAX_LANES): Layout {
        if (rows.isEmpty()) return Layout.EMPTY

        val rank = order.withIndex().associate { (index, name) -> name to index }
        // Only branches actually on screen take lanes, so one busy branch does
        // not push everything else past the cap.
        val present = rows.mapNotNull { row -> row.branch?.let { rank[it] } }.distinct().sorted()
        val laneOf = present.withIndex().associate { (lane, r) -> r to lane }
        fun laneOf(branch: String?): Int =
            (rank[branch]?.let { laneOf[it] } ?: 0).coerceAtMost(maxLanes - 1)

        val laneBranches = present.map { order[it] }.take(maxLanes)

        val lanes = rows.map { laneOf(it.branch) }
        val indexOf = rows.withIndex().associate { (index, row) -> row.hash to index }

        val incoming = List(rows.size) { mutableListOf<Segment>() }
        val outgoing = List(rows.size) { mutableListOf<Segment>() }
        val through = List(rows.size) { sortedSetOf<Int>() }

        rows.forEachIndexed { child, row ->
            row.parents.forEach { hash ->
                val parent = indexOf[hash] ?: return@forEach
                // Children sit above parents; anything else is broken input,
                // and half an edge would be drawn pointing at nothing.
                if (parent <= child) return@forEach

                val edge = maxOf(lanes[child], lanes[parent])
                outgoing[child] += Segment(lanes[child], edge)
                incoming[parent] += Segment(edge, lanes[parent])
                (child + 1 until parent).forEach { mid ->
                    if (lanes[mid] == edge) {
                        // The edge passes a revision sitting in its lane: it has
                        // to thread through the node, or the line breaks there.
                        incoming[mid] += Segment(edge, edge)
                        outgoing[mid] += Segment(edge, edge)
                    } else {
                        through[mid] += edge
                    }
                }
            }
        }

        val laid = rows.indices.map { index ->
            val ins = incoming[index].distinct()
            val outs = outgoing[index].distinct()
            val crossing = through[index].toList()
            val used = crossing + ins.map { it.from } + outs.map { it.to } + lanes[index]
            Row(
                lane = lanes[index],
                incoming = ins,
                outgoing = outs,
                through = crossing,
                width = used.max() + 1,
            )
        }
        return Layout(laid, laneBranches)
    }

    const val MAX_LANES = 8
}
