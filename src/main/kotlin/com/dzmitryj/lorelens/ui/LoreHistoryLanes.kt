package com.dzmitryj.lorelens.ui

/**
 * Lanes for the History column, one per branch.
 *
 * The previous model laid rows out by parent chain alone, so a lane could only
 * exist between a merge and its other parent -- everything else collapsed into
 * a single column and branches were invisible except as stubs. Lanes come from
 * which branch a revision is on, so a branch runs as a line for as long as it
 * has revisions on screen.
 *
 * Pure: no Swing, so the arithmetic is testable.
 */
object LoreHistoryLanes {

    /** A revision in display order, newest first. */
    data class Input(val hash: String, val parents: List<String>, val branch: String?)

    /**
     * @param lane the column this row's node sits in.
     * @param occupied lanes with a line running through this row.
     * @param joins turns leaving this row's node for a parent on another lane.
     */
    data class Row(
        val lane: Int,
        val occupied: List<Int>,
        val joins: List<Join>,
        val width: Int,
    )

    data class Join(val fromLane: Int, val toLane: Int)

    /**
     * @param order branch names in hierarchy order, which fixes the lane a
     *   branch gets so it does not move as rows scroll past.
     * @param maxLanes beyond this the column stops being readable and lanes
     *   fold into the last one.
     */
    fun layout(rows: List<Input>, order: List<String>, maxLanes: Int = MAX_LANES): List<Row> {
        if (rows.isEmpty()) return emptyList()

        val rank = order.withIndex().associate { (index, name) -> name to index }
        // Anything unattributed shares lane zero rather than each taking one.
        fun laneOf(branch: String?): Int =
            (rank[branch] ?: 0).coerceIn(0, maxLanes - 1)

        val lanes = rows.map { laneOf(it.branch) }
        val indexOf = rows.withIndex().associate { (index, row) -> row.hash to index }

        // A lane runs from its first row to its last, so it draws as one line
        // rather than reappearing wherever a revision happens to land.
        val spans = mutableMapOf<Int, IntRange>()
        lanes.forEachIndexed { index, lane ->
            val span = spans[lane]
            spans[lane] = if (span == null) index..index else minOf(span.first, index)..maxOf(span.last, index)
        }

        val joins = rows.mapIndexed { index, row ->
            row.parents
                .mapNotNull { indexOf[it] }
                .map { lanes[it] }
                .filter { it != lanes[index] }
                .distinct()
                .map { target ->
                    // The join reaches up to here, so the lane it enters has to
                    // be drawn from this row down.
                    val span = spans.getValue(target)
                    spans[target] = minOf(span.first, index)..span.last
                    Join(lanes[index], target)
                }
        }

        return rows.indices.map { index ->
            val occupied = spans.filterValues { index in it }.keys.sorted()
            Row(
                lane = lanes[index],
                occupied = occupied,
                joins = joins[index],
                width = (occupied.maxOrNull() ?: lanes[index]) + 1,
            )
        }
    }

    const val MAX_LANES = 5
}
