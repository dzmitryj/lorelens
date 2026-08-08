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
        assertEquals(8, hunks[0].oldCount)
        assertEquals(24, hunks[0].newStart)
        assertEquals(8, hunks[0].newCount)
        assertEquals(listOf(" context", "-old", "+new"), hunks[0].lines)
        assertEquals(40, hunks[1].oldStart)
        assertEquals(1, hunks[1].oldCount)
        assertEquals(41, hunks[1].newStart)
        assertEquals(1, hunks[1].newCount)
    }

    /**
     * A zero-length range is numbered by the line before it, so a pure deletion
     * anchors one line later than a range that contains lines. Getting this
     * wrong shifted every line after the first deletion onto its neighbour.
     */
    @Test
    fun `a pure deletion hunk does not shift the lines around it`() {
        val previous = listOf("r1", "r2", "r3", "r4", "r5")

        val next = LoreBlameEngine.advance(
            previous,
            """
            @@ -3,2 +2,0 @@
            -three
            -four
            """.trimIndent(),
            "r9",
        )

        assertEquals(listOf("r1", "r2", "r5"), next)
    }

    /** The same anchoring, for an insertion that follows a deletion. */
    @Test
    fun `a deletion followed by an insertion keeps both in step`() {
        val previous = listOf("r1", "r2", "r3", "r4", "r5", "r6")

        val next = LoreBlameEngine.advance(
            previous,
            """
            @@ -2,2 +1,0 @@
            -two
            -three
            @@ -5,0 +4 @@
            +added
            """.trimIndent(),
            "r9",
        )

        assertEquals(listOf("r1", "r4", "r5", "r9", "r6"), next)
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

    /**
     * Lore emits blocks that delete lines and re-add them unchanged. Taken
     * literally that hands them all to the new revision, which is what made a
     * copyright header read as a commit years later than the one that wrote it.
     */
    @Test
    fun `lines deleted and re-added unchanged keep their origin`() {
        val previous = listOf("r1", "r2", "r3", "r4")

        val next = LoreBlameEngine.advance(
            previous,
            """
            @@ -2,3 +2,3 @@
            -class APlayerState;
            -class APlayerController;
            -class AAperturePlayerController;
            +class APlayerState;
            +class APlayerController;
            +class AAperturePlayerController;
            """.trimIndent(),
            "r9",
        )

        assertEquals(previous, next)
    }

    @Test
    fun `only the genuinely new line in a re-added block takes the new revision`() {
        val previous = listOf("r1", "r2", "r3")

        val next = LoreBlameEngine.advance(
            previous,
            """
            @@ -1,3 +1,4 @@
            -one
            -two
            -three
            +one
            +two
            +inserted
            +three
            """.trimIndent(),
            "r9",
        )

        assertEquals(listOf("r1", "r2", "r9", "r3"), next)
    }

    @Test
    fun `only the changed line of a re-added block takes the new revision`() {
        val previous = listOf("r1", "r2", "r3")

        val next = LoreBlameEngine.advance(
            previous,
            """
            @@ -1,3 +1,3 @@
            -one
            -two
            -three
            +one
            +rewritten
            +three
            """.trimIndent(),
            "r9",
        )

        assertEquals(listOf("r1", "r9", "r3"), next)
    }

    /** Whitespace churn -- trailing spaces, a stripped CR -- is not authorship. */
    @Test
    fun `a line re-added with different trailing whitespace keeps its origin`() {
        val previous = listOf("r1", "r2")

        val next = LoreBlameEngine.advance(
            previous,
            "@@ -1,2 +1,2 @@\n-one  \n-two\t\n+one\n+two\n",
            "r9",
        )

        assertEquals(previous, next)
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
