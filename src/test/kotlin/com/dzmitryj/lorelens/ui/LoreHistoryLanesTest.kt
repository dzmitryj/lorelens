package com.dzmitryj.lorelens.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LoreHistoryLanesTest {

    private fun row(hash: String, vararg parents: String) =
        LoreHistoryLanes.Input(hash, parents.toList())

    /**
     * The complaint that prompted this rewrite: the line arrived in pieces. Every
     * row a strand crosses has to carry it, or the gap shows.
     */
    @Test
    fun `a strand lands on both row boundaries all the way down`() {
        val rows = LoreHistoryLanes.layout(
            listOf(
                row("m2", "m1", "side"),
                row("m1", "m0"),
                row("side", "m0"),
                row("m0"),
            ),
        )

        // Everything between the merge and `side` has to carry side's lane,
        // otherwise the curve starts from nothing.
        val sideLane = rows[0].outgoing.map { it.to }.single { it != rows[0].lane }
        assertTrue("expected side's lane to cross the row between", sideLane in rows[1].through)
        assertEquals(sideLane, rows[2].lane)

        // Each half meets the other: what leaves a row's bottom edge enters the
        // next row's top edge on the same lane.
        rows.zipWithNext().forEach { (upper, lower) ->
            val leaving = (upper.outgoing.map { it.to } + upper.through).toSet()
            val arriving = (lower.incoming.map { it.from } + lower.through).toSet()
            assertEquals(leaving, arriving)
        }
    }

    /** One strand is one line, straight through. */
    @Test
    fun `a linear history stays in one lane`() {
        val rows = LoreHistoryLanes.layout(listOf(row("b", "a"), row("a")))

        assertEquals(listOf(0, 0), rows.map { it.lane })
        assertEquals(listOf(1, 1), rows.map { it.width })
        assertTrue("nothing crosses a single strand", rows.all { it.through.isEmpty() })
        // The first row's parent continues below it; the last has nowhere to go.
        assertEquals(listOf(0 to 0), rows[0].outgoing.map { it.from to it.to })
        assertTrue(rows[1].outgoing.isEmpty())
    }

    /** A merge converges the lane it pulled in, which then closes. */
    @Test
    fun `a merge opens a lane and the merged revision closes it`() {
        val rows = LoreHistoryLanes.layout(
            listOf(row("m", "a", "b"), row("b", "a"), row("a")),
        )

        assertEquals(2, rows[0].outgoing.size)
        assertEquals(setOf(0, 1), rows[0].outgoing.mapTo(mutableSetOf()) { it.to })
        // b sits on the lane the merge opened and carries it straight down; the
        // turn back into lane 0 happens at the shared parent, not before it.
        assertEquals(1, rows[1].lane)
        assertEquals(listOf(1 to 1), rows[1].incoming.map { it.from to it.to })
        assertEquals(listOf(1 to 1), rows[1].outgoing.map { it.from to it.to })
        assertEquals(listOf(0 to 0, 1 to 0), rows[2].incoming.map { it.from to it.to })
        assertTrue("the lane is free again", rows[2].through.isEmpty())
    }

    /** A parent outside the window would hold a lane open for a line that never comes. */
    @Test
    fun `a parent off the end of the window closes its lane`() {
        val rows = LoreHistoryLanes.layout(listOf(row("b", "a")))

        assertTrue(rows.single().outgoing.isEmpty())
        assertEquals(1, rows.single().width)
    }

    @Test
    fun `lanes never exceed the cap`() {
        // A chain of merges, each pulling in a side that never lands.
        val many = (1..10).map { row("m$it", "m${it + 1}", "s$it") } +
            (1..10).map { row("s$it") } + row("m11")
        val rows = LoreHistoryLanes.layout(many, maxLanes = 3)

        assertTrue("expected at most 3 lanes", rows.all { it.width <= 3 })
    }
}
