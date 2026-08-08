package com.dzmitryj.lorelens.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LoreBranchGraphLayoutTest {

    private fun revision(
        hash: String,
        number: Long,
        branch: String,
        parents: List<String> = emptyList(),
    ) = LoreBranchGraphLayout.Input(
        hash = hash,
        number = number,
        branch = branch,
        parents = parents,
        author = "someone@example.com",
        isMerge = parents.size > 1,
    )

    @Test
    fun `a single branch is one lane with nothing crossing it`() {
        val graph = LoreBranchGraphLayout.layout(
            listOf(
                revision("a", 1, "main"),
                revision("b", 2, "main", listOf("a")),
                revision("c", 3, "main", listOf("b")),
            ),
        )

        assertEquals(listOf("main"), graph.lanes)
        assertEquals(listOf(0, 0, 0), graph.nodes.map { it.lane })
        assertEquals(listOf(0, 1, 2), graph.nodes.map { it.column })
        assertTrue("a lane does not connect to itself", graph.links.isEmpty())
    }

    /** The branch's first revision hangs off whatever it was cut from. */
    @Test
    fun `a branch connects back to the one it was cut from`() {
        val graph = LoreBranchGraphLayout.layout(
            listOf(
                revision("a", 1, "main"),
                revision("s1", 2, "side", listOf("a")),
            ),
            order = listOf("main", "side"),
        )

        assertEquals(listOf("main", "side"), graph.lanes)
        val link = graph.links.single()
        assertEquals("a", link.from.hash)
        assertEquals("s1", link.to.hash)
        assertEquals(0, link.from.lane)
        assertEquals(1, link.to.lane)
    }

    /** A merge reaches back to the branch it pulled in. */
    @Test
    fun `a merge links across from the branch it brought in`() {
        val graph = LoreBranchGraphLayout.layout(
            listOf(
                revision("a", 1, "main"),
                revision("s1", 2, "side", listOf("a")),
                revision("m", 3, "main", listOf("a", "s1")),
            ),
            order = listOf("main", "side"),
        )

        val merge = graph.nodeAt("m")!!
        assertTrue("the merge should be marked", merge.isMerge)

        val crossing = graph.links.filter { it.to.hash == "m" }
        assertEquals(1, crossing.size)
        assertEquals("s1", crossing.single().from.hash)
    }

    /** Lanes follow the hierarchy, not which branch happens to be checked out. */
    @Test
    fun `lanes take the order they are given`() {
        val graph = LoreBranchGraphLayout.layout(
            listOf(
                revision("a", 1, "old"),
                revision("b", 2, "young"),
            ),
            order = listOf("young", "old"),
        )

        assertEquals(listOf("young", "old"), graph.lanes)
    }

    /**
     * Branch history carries the ancestry it was cut from, so the same revision
     * arrives in several walks. It belongs to the branch cut most recently
     * before it, not to whichever walk happened to produce it.
     */
    @Test
    fun `a shared revision goes to the branch it was actually made on`() {
        val shared = revision("a", 1, "?", emptyList())
        val onLane = revision("b", 2, "?", listOf("a"))

        val attributed = LoreBranchGraphLayout.attribute(
            listOf(
                // main sees only its own revision.
                LoreBranchGraphLayout.Walk("main", branchPoint = 0, revisions = listOf(shared)),
                // lane was cut at r1 and sees both.
                LoreBranchGraphLayout.Walk("lane", branchPoint = 1, revisions = listOf(shared, onLane)),
            ),
        )

        assertEquals("main", attributed.single { it.hash == "a" }.branch)
        assertEquals("lane", attributed.single { it.hash == "b" }.branch)
    }

    /** Every revision must land somewhere, even one older than every branch. */
    @Test
    fun `a revision older than every branch point still gets a branch`() {
        val ancient = revision("a", 1, "?", emptyList())

        val attributed = LoreBranchGraphLayout.attribute(
            listOf(LoreBranchGraphLayout.Walk("lane", branchPoint = 5, revisions = listOf(ancient))),
        )

        assertEquals("lane", attributed.single().branch)
    }

    @Test
    fun `an empty repository lays out to nothing`() {
        val graph = LoreBranchGraphLayout.layout(emptyList())

        assertEquals(emptyList<String>(), graph.lanes)
        assertEquals(0, graph.columns)
    }
}
