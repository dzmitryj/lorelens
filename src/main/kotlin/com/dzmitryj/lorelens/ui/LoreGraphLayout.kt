package com.dzmitryj.lorelens.ui

/**
 * Assigns each revision a lane, so the log can draw the shape of the history
 * rather than a column of dots.
 *
 * Walks newest to oldest holding one open lane per revision still waiting to be
 * drawn. A revision takes the leftmost lane expecting it, its first parent
 * inherits that lane, and any second parent opens one of its own -- which is
 * what makes a merge fork and its branch rejoin further down.
 *
 * Pure: no Swing here, so the arithmetic can be tested without a table.
 */
object LoreGraphLayout {

    /**
     * @param lane the column this revision's node sits in.
     * @param edges lanes carried through this row, each with the lane it is
     *   heading for on the row below, so a line can slant between them.
     * @param width lanes occupied at this row, which sets the column's size.
     */
    data class Row(
        val lane: Int,
        val edges: List<Edge>,
        val width: Int,
    )

    /** A line passing this row, from [fromLane] above to [toLane] below. */
    data class Edge(val fromLane: Int, val toLane: Int, val merge: Boolean)

    /**
     * @param revisions newest first, each with the hashes of its parents.
     */
    fun layout(revisions: List<Pair<String, List<String>>>): List<Row> {
        // Lane N is waiting for this hash; null once nothing is heading there.
        val awaiting = mutableListOf<String?>()
        val rows = mutableListOf<Row>()

        revisions.forEach { (hash, parents) ->
            var lane = awaiting.indexOf(hash)
            if (lane < 0) {
                lane = awaiting.indexOfFirst { it == null }.takeIf { it >= 0 }
                    ?: awaiting.size.also { awaiting += null }
                awaiting[lane] = hash
            }

            // Every other lane still waiting for something carries straight down.
            val before = awaiting.toList()

            val first = parents.firstOrNull()
            awaiting[lane] = first

            // A lane whose revision has already been drawn elsewhere is finished.
            before.indices.forEach { index ->
                if (index != lane && before[index] == hash) awaiting[index] = null
            }

            parents.drop(1).forEach { other ->
                val existing = awaiting.indexOf(other)
                if (existing < 0) {
                    val free = awaiting.indexOfFirst { it == null }.takeIf { it >= 0 }
                        ?: awaiting.size.also { awaiting += null }
                    awaiting[free] = other
                }
            }

            val after = awaiting.toList()
            val edges = mutableListOf<Edge>()

            before.forEachIndexed { index, waiting ->
                if (waiting == null || index == lane) return@forEachIndexed
                val destination = after.indexOf(waiting)
                if (destination >= 0) edges += Edge(index, destination, merge = false)
            }

            // The node's own outgoing lines: one per parent.
            parents.forEachIndexed { index, parent ->
                val destination = after.indexOf(parent)
                if (destination >= 0) edges += Edge(lane, destination, merge = index > 0)
            }

            // Lanes are never removed from the list, only emptied, so the width
            // has to count what is occupied rather than how far the list grew.
            val occupied = before.indices.filter { before[it] != null } +
                after.indices.filter { after[it] != null } +
                lane
            rows += Row(lane, edges, (occupied.max()) + 1)
        }

        return rows
    }
}
