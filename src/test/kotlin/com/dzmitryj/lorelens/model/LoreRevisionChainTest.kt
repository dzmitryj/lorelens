package com.dzmitryj.lorelens.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class LoreRevisionChainTest {

    private val newestFirst = listOf("r3", "r2", "r1")

    @Test
    fun `a revision's parent is the next entry in a newest-first history`() {
        assertEquals("r2", LoreRevisionChain.parentOf(newestFirst, 0))
        assertEquals("r1", LoreRevisionChain.parentOf(newestFirst, 1))
    }

    @Test
    fun `the oldest revision has no parent`() {
        assertNull(LoreRevisionChain.parentOf(newestFirst, 2))
    }

    @Test
    fun `an out of range or absent selection has no parent`() {
        assertNull(LoreRevisionChain.parentOf(newestFirst, -1))
        assertNull(LoreRevisionChain.parentOf(newestFirst, 99))
        assertNull(LoreRevisionChain.parentOf(emptyList<String>(), 0))
    }
}
