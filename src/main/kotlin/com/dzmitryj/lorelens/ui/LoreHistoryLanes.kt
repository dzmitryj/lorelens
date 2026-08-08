package com.dzmitryj.lorelens.ui

/**
 * Lanes for the History column.
 *
 * Walks the rows newest first holding a lane per open strand: a lane remembers
 * the hash it is waiting for, a row takes the lane waiting for it, and its
 * first parent keeps that lane. Merges converge the lanes waiting on them and
 * open a lane per extra parent.
 *
 * Every segment is emitted against the row it crosses, so consecutive rows join
 * up by construction. The previous model drew a lane once from its first row to
 * its last and left the line in pieces wherever a row had no node on it.
 *
 * Pure: no Swing, so the arithmetic is testable.
 */
object LoreHistoryLanes {

    /** A revision in display order, newest first. */
    data class Input(val hash: String, val parents: List<String>)

    /** A line within one row, running between lane [from] and lane [to]. */
    data class Segment(val from: Int, val to: Int) {
        /** The lane a turn is named after is the outer one, as in any commit graph. */
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

    fun layout(rows: List<Input>, maxLanes: Int = MAX_LANES): List<Row> {
        if (rows.isEmpty()) return emptyList()

        // A parent outside the window would hold a lane open to the bottom of
        // the table for a line that never arrives, so only what is on screen
        // keeps a lane.
        val known = rows.mapTo(HashSet()) { it.hash }

        // Lane to the hash it is waiting for; null is free.
        val open = ArrayList<String?>()

        fun claim(hash: String?): Int {
            val free = open.indexOfFirst { it == null }
            val lane = when {
                free >= 0 -> free
                open.size < maxLanes -> open.size.also { open.add(null) }
                // Past the cap the graph stops being readable; strands fold into
                // the last lane rather than growing a column nobody can follow.
                else -> maxLanes - 1
            }
            open[lane] = hash
            return lane
        }

        return rows.map { row ->
            val waiting = open.indices.filter { open[it] == row.hash }
            waiting.forEach { open[it] = null }

            // RESERVED keeps the lane out of the free list until the parent is
            // known, so an extra parent cannot be handed the node's own lane.
            val lane = waiting.firstOrNull()?.also { open[it] = RESERVED } ?: claim(RESERVED)

            val incoming = waiting.map { Segment(it, lane) }
            val through = open.indices.filter { it != lane && open[it] != null }

            val first = row.parents.firstOrNull()?.takeIf { it in known }
            open[lane] = first

            val outgoing = mutableListOf<Segment>()
            if (first != null) outgoing += Segment(lane, lane)
            row.parents.drop(1)
                .filter { it in known }
                .forEach { parent -> outgoing += Segment(lane, claim(parent)) }

            val used = through + incoming.map { it.from } + outgoing.map { it.to } + lane
            Row(
                lane = lane,
                incoming = incoming,
                outgoing = outgoing,
                through = through,
                width = used.max() + 1,
            )
        }
    }

    /** Not a hash: nothing can be waiting for it, so no lane can match it. */
    private const val RESERVED = ""

    const val MAX_LANES = 8
}
