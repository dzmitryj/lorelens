package com.dzmitryj.lorelens.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class LoreBranchColoursTest {

    private val order = listOf("main", "dev-main", "dev-alberto", "dev-dicenzo", "dev-dima")

    /** The whole point: every view asking for a branch gets the same answer. */
    @Test
    fun `assignment follows hierarchy order and is distinct`() {
        LoreBranchColours.assign(order)

        val colours = order.map { LoreBranchColours.colourOf(it) }
        assertEquals("five branches, five colours", colours.size, colours.distinct().size)
        // Asking twice changes nothing.
        assertEquals(colours, order.map { LoreBranchColours.colourOf(it) })
    }

    /** A name outside the assignment still renders, stably. */
    @Test
    fun `unassigned names get a stable fallback`() {
        LoreBranchColours.assign(order)

        val first = LoreBranchColours.colourOf("feature/unlisted")
        assertNotNull(first)
        assertEquals(first, LoreBranchColours.colourOf("feature/unlisted"))
    }

    @Test
    fun `an empty order does not wipe the assignment`() {
        LoreBranchColours.assign(order)
        val before = LoreBranchColours.colourOf("dev-main")

        LoreBranchColours.assign(emptyList())

        assertEquals(before, LoreBranchColours.colourOf("dev-main"))
    }
}
