package com.dzmitryj.lorelens.blame

import org.junit.Assert.assertEquals
import org.junit.Test

class LoreBlameEngineTest {

    @Test
    fun `parses hunk headers with and without counts`() {
        val hunks = LoreBlameEngine.parseHunks(
            """
            Aperture/Source/Thing.h
            --- Aperture/Source/Thing.h@140
            +++ Aperture/Source/Thing.h@166
            @@ -24,8 +24,8 @@
             context
            -old
            +new
            @@ -40 +41 @@
            -gone
            """.trimIndent(),
        )

        assertEquals(2, hunks.size)
        assertEquals(24, hunks[0].oldStart)
        assertEquals(24, hunks[0].newStart)
        assertEquals(listOf(" context", "-old", "+new"), hunks[0].lines)
        assertEquals(40, hunks[1].oldStart)
        assertEquals(41, hunks[1].newStart)
    }

    @Test
    fun `an added line takes the new revision and its neighbours keep theirs`() {
        val previous = listOf("r1", "r1", "r1")

        val next = LoreBlameEngine.advance(
            previous,
            """
            @@ -1,3 +1,4 @@
             one
             two
            +inserted
             three
            """.trimIndent(),
            "r2",
        )

        assertEquals(listOf("r1", "r1", "r2", "r1"), next)
    }

    @Test
    fun `a removed line drops out and the rest keep their origin`() {
        val previous = listOf("r1", "r2", "r1")

        val next = LoreBlameEngine.advance(
            previous,
            """
            @@ -1,3 +1,2 @@
             one
            -two
             three
            """.trimIndent(),
            "r3",
        )

        assertEquals(listOf("r1", "r1"), next)
    }

    @Test
    fun `a replaced line takes the new revision`() {
        val previous = listOf("r1", "r1", "r1")

        val next = LoreBlameEngine.advance(
            previous,
            """
            @@ -2,1 +2,1 @@
            -middle
            +replaced
            """.trimIndent(),
            "r5",
        )

        assertEquals(listOf("r1", "r5", "r1"), next)
    }

    /** Lines outside every hunk are untouched and must carry over verbatim. */
    @Test
    fun `lines beyond the last hunk carry over`() {
        val previous = listOf("r1", "r1", "r2", "r2", "r2")

        val next = LoreBlameEngine.advance(
            previous,
            """
            @@ -1,1 +1,1 @@
            -first
            +changed
            """.trimIndent(),
            "r9",
        )

        assertEquals(listOf("r9", "r1", "r2", "r2", "r2"), next)
    }

    @Test
    fun `an empty patch leaves attribution untouched`() {
        val previous = listOf("r1", "r2", "r3")

        assertEquals(previous, LoreBlameEngine.advance(previous, "", "r4"))
    }

    @Test
    fun `two separate hunks both attribute correctly`() {
        val previous = List(6) { "r1" }

        val next = LoreBlameEngine.advance(
            previous,
            """
            @@ -1,2 +1,3 @@
             a
            +inserted near the top
             b
            @@ -5,2 +6,2 @@
            -e
            +rewritten
             f
            """.trimIndent(),
            "r7",
        )

        assertEquals(listOf("r1", "r7", "r1", "r1", "r1", "r7", "r1"), next)
    }
}
