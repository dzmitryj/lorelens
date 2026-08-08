package com.dzmitryj.lorelens.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LoreHistoryLanesTest {

    private fun row(hash: String, branch: String?, vararg parents: String) =
        LoreHistoryLanes.Input(hash, parents.toList(), branch)

    private val order = listOf("main", "dev-main", "dev-alberto")

    /** A lane is a branch: every revision of a branch sits in the same column. */
    @Test
    fun `a branch keeps one lane for the whole view`() {
        val rows = LoreHistoryLanes.layout(
            listOf(
                row("d3", "dev-main", "d2"),
                row("a2", "dev-alberto", "a1"),
                row("d2", "dev-main", "d1"),
                row("a1", "dev-alberto", "d1"),
                row("d1", "dev-main"),
            ),
            order,
        )

        assertEquals(listOf(0, 1, 0, 1, 0), rows.map { it.lane })
    }

    /**
     * The point of the model: direction is visible. A merge's edge runs down
     * the child branch's lane and curves into the receiving branch's node.
     */
    @Test
    fun `a merge edge runs in the merged branch's lane`() {
        val rows = LoreHistoryLanes.layout(
            listOf(
                row("m", "dev-main", "d1", "a1"),
                row("a1", "dev-alberto", "d0"),
                row("d1", "dev-main", "d0"),
                row("d0", "dev-main"),
            ),
            order,
        )

        // The merge node sits on dev-main and sends one edge down its own lane
        // and one down dev-alberto's.
        assertEquals(setOf(0, 1), rows[0].outgoing.mapTo(mutableSetOf()) { it.to })
        // a1 receives the edge in its own lane, and its parent edge curves back
        // into dev-main at d0.
        assertEquals(listOf(1 to 1), rows[1].incoming.map { it.from to it.to })
        assertEquals(listOf(1 to 1), rows[1].outgoing.map { it.from to it.to })
        assertEquals(setOf(0 to 0, 1 to 0), rows[3].incoming.mapTo(mutableSetOf()) { it.from to it.to })
    }

    /** Rows between an edge's ends carry it, so the line never breaks. */
    @Test
    fun `edges land on both row boundaries all the way down`() {
        val rows = LoreHistoryLanes.layout(
            listOf(
                row("m", "dev-main", "d1", "a1"),
                row("d1", "dev-main", "d0"),
                row("a1", "dev-alberto", "d0"),
                row("d0", "dev-main"),
            ),
            order,
        )

        // dev-alberto's edge crosses the dev-main row between the merge and a1.
        assertTrue(1 in rows[1].through)
        rows.zipWithNext().forEach { (upper, lower) ->
            val leaving = (upper.outgoing.map { it.to } + upper.through).toSet()
            val arriving = (lower.incoming.map { it.from } + lower.through).toSet()
            assertEquals(leaving, arriving)
        }
    }

    /** An edge passing a revision in its own lane threads through the node. */
    @Test
    fun `an edge through an occupied lane threads the node`() {
        val rows = LoreHistoryLanes.layout(
            listOf(
                row("m", "dev-main", "d0", "a2"),
                row("a1", "dev-alberto"),
                row("a2", "dev-alberto", "d0"),
                row("d0", "dev-main"),
            ),
            order,
        )

        // a1 has no edges of its own, but the merge's edge passes its lane, so
        // the line threads through rather than breaking around it.
        assertEquals(listOf(1 to 1), rows[1].incoming.map { it.from to it.to })
        assertEquals(listOf(1 to 1), rows[1].outgoing.map { it.from to it.to })
        // dev-main's own edge crosses this row too, in its own lane.
        assertEquals(listOf(0), rows[1].through)
    }

    /** One branch is one line, straight through. */
    @Test
    fun `a single branch stays in lane zero`() {
        val rows = LoreHistoryLanes.layout(
            listOf(row("b", "main", "a"), row("a", "main")),
            order,
        )

        assertEquals(listOf(0, 0), rows.map { it.lane })
        assertEquals(listOf(1, 1), rows.map { it.width })
    }

    /** Only branches on screen take lanes: absent ones do not leave gaps. */
    @Test
    fun `absent branches do not reserve lanes`() {
        val rows = LoreHistoryLanes.layout(
            listOf(row("a1", "dev-alberto"), row("m1", "main")),
            order,
        )

        // dev-main is not in the view, so dev-alberto compacts to lane 1.
        assertEquals(listOf(1, 0), rows.map { it.lane })
    }

    @Test
    fun `lanes never exceed the cap`() {
        val names = (1..10).map { "branch$it" }
        val rows = LoreHistoryLanes.layout(
            names.map { row("h$it", it) },
            names,
            maxLanes = 3,
        )

        assertTrue("expected at most 3 lanes", rows.all { it.width <= 3 && it.lane < 3 })
    }

    private fun row(hash: String, n: Int) = row(hash, "branch$n")
}
