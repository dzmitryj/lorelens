package com.dzmitryj.lorelens.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LoreHistoryLanesTest {

    private fun row(hash: String, branch: String?, vararg parents: String) =
        LoreHistoryLanes.Input(hash, parents.toList(), branch)

    private val order = listOf("main", "dev-main", "dev-alberto")

    /**
     * The complaint that prompted this: a branch showed as a stub at its merge
     * and nowhere else, because lanes came from parent chains rather than
     * branches.
     */
    @Test
    fun `a branch runs as a line for as long as it has revisions`() {
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

        // dev-alberto holds lane 2 across every row between its two revisions.
        assertTrue(
            "expected dev-alberto's lane to run through the rows between it",
            rows.subList(1, 4).all { 2 in it.occupied },
        )
        assertEquals(listOf(1, 2, 1, 2, 1), rows.map { it.lane })
    }

    /** A branch point turns from the child's lane into the parent's. */
    @Test
    fun `a parent on another lane produces a join`() {
        val rows = LoreHistoryLanes.layout(
            listOf(
                row("a1", "dev-alberto", "d1"),
                row("d1", "dev-main"),
            ),
            order,
        )

        val join = rows.first().joins.single()
        assertEquals(2, join.fromLane)
        assertEquals(1, join.toLane)
    }

    /** One branch is one line, with nothing crossing it. */
    @Test
    fun `a single branch stays in its lane`() {
        val rows = LoreHistoryLanes.layout(
            listOf(row("b", "main", "a"), row("a", "main")),
            order,
        )

        assertEquals(listOf(0, 0), rows.map { it.lane })
        assertTrue("nothing to join", rows.all { it.joins.isEmpty() })
        assertEquals(listOf(1, 1), rows.map { it.width })
    }

    /** Before attribution arrives every row is unattributed; it still lays out. */
    @Test
    fun `unattributed rows share one lane`() {
        val rows = LoreHistoryLanes.layout(
            listOf(row("b", null, "a"), row("a", null)),
            order,
        )

        assertEquals(listOf(0, 0), rows.map { it.lane })
    }

    @Test
    fun `lanes never exceed the cap`() {
        val many = (1..10).map { row("h$it", "branch$it") }
        val rows = LoreHistoryLanes.layout(many, (1..10).map { "branch$it" }, maxLanes = 3)

        assertTrue("expected at most 3 lanes", rows.all { it.width <= 3 && it.lane < 3 })
    }
}
