package com.dzmitryj.lorelens.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LoreGraphLayoutTest {

    private fun layout(vararg revisions: Pair<String, List<String>>) =
        LoreGraphLayout.layout(revisions.toList())

    @Test
    fun `a linear history stays in one lane`() {
        val rows = layout(
            "c" to listOf("b"),
            "b" to listOf("a"),
            "a" to emptyList(),
        )

        assertEquals(listOf(0, 0, 0), rows.map { it.lane })
        assertEquals(listOf(1, 1, 1), rows.map { it.width })
    }

    /**
     * A merge opens a second lane for the side it brought in, and that lane
     * closes again on the revision it was waiting for.
     */
    @Test
    fun `a merge forks a lane that rejoins at its other parent`() {
        val rows = layout(
            "m" to listOf("a2", "b1"),
            "a2" to listOf("a1"),
            "b1" to listOf("a1"),
            "a1" to emptyList(),
        )

        assertEquals(0, rows[0].lane)
        assertTrue("the merge should open a second lane", rows[0].width >= 2)
        assertTrue(
            "one of the merge's edges is the second parent",
            rows[0].edges.any { it.merge },
        )

        // Both sides are drawn before the revision they share, and that shared
        // revision is the row where the two lanes converge, so it spans both.
        assertEquals(setOf(0, 1), setOf(rows[1].lane, rows[2].lane))
        assertEquals(2, rows[3].width)
    }

    /** A revision nothing points at still gets a lane rather than vanishing. */
    @Test
    fun `an unreferenced head takes its own lane`() {
        val rows = layout(
            "x" to listOf("a"),
            "y" to listOf("a"),
            "a" to emptyList(),
        )

        assertEquals(0, rows[0].lane)
        assertEquals(1, rows[1].lane)
        // Both heads are waiting on it, so the row it lands on spans both lanes.
        assertEquals(2, rows[2].width)
    }

    /**
     * A branch left open for hundreds of revisions used to open a lane each
     * time and draw a line per lane per row, which is what turned the column
     * into a barcode and cost real paint time.
     */
    @Test
    fun `lanes never exceed the cap`() {
        // Ten heads all waiting on one ancestor: without a cap this is ten lanes.
        val heads = (1..10).map { "h$it" to listOf("base") }
        val rows = LoreGraphLayout.layout(heads + ("base" to emptyList()), maxLanes = 3)

        assertTrue(
            "expected at most 3 lanes, got ${rows.map { it.width }}",
            rows.all { it.width <= 3 },
        )
        assertTrue("every row still gets a lane", rows.all { it.lane < 3 })
    }

    @Test
    fun `an empty history lays out to nothing`() {
        assertEquals(emptyList<LoreGraphLayout.Row>(), LoreGraphLayout.layout(emptyList()))
    }
}
