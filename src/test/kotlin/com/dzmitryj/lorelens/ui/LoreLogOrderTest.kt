package com.dzmitryj.lorelens.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LoreLogOrderTest {

    private fun input(hash: String, timestamp: Long, vararg parents: String) =
        LoreBranchGraphLayout.Input(
            hash = hash,
            number = timestamp,
            branch = "main",
            parents = parents.toList(),
            author = "a",
            isMerge = parents.size > 1,
            synced = true,
            timestamp = timestamp,
        )

    @Test
    fun `newest first when independent`() {
        val ordered = LoreLogOrder.topological(
            listOf(input("old", 1), input("new", 3), input("mid", 2)),
        )

        assertEquals(listOf("new", "mid", "old"), ordered.map { it.hash })
    }

    /** The case plain timestamp order gets wrong: a parent's clock ran ahead. */
    @Test
    fun `a child stays above its parent regardless of timestamps`() {
        val ordered = LoreLogOrder.topological(
            listOf(input("child", 1, "parent"), input("parent", 5)),
        )

        assertEquals(listOf("child", "parent"), ordered.map { it.hash })
    }

    @Test
    fun `both sides of a merge sit above the shared parent`() {
        val ordered = LoreLogOrder.topological(
            listOf(
                input("base", 1),
                input("side", 2, "base"),
                input("merge", 4, "tip", "side"),
                input("tip", 3, "base"),
            ),
        ).map { it.hash }

        val position = ordered.withIndex().associate { (index, hash) -> hash to index }
        assertTrue(position["merge"]!! < position["tip"]!!)
        assertTrue(position["merge"]!! < position["side"]!!)
        assertTrue(position["tip"]!! < position["base"]!!)
        assertTrue(position["side"]!! < position["base"]!!)
    }

    /** The log of a branch: its history and what was merged in, nothing else. */
    @Test
    fun `reachable keeps merged work and drops unmerged work`() {
        val parents = mapOf(
            "tip" to listOf("merge"),
            "merge" to listOf("base", "side"),
            "side" to listOf("base"),
            "base" to emptyList(),
            // Another branch's commit, not merged anywhere near the tip.
            "elsewhere" to listOf("base"),
        )

        val keep = LoreLogOrder.reachable("tip", parents)

        assertEquals(setOf("tip", "merge", "side", "base"), keep)
    }

    /** A tip outside the window yields nothing rather than throwing. */
    @Test
    fun `reachable from an unknown tip is empty`() {
        assertEquals(emptySet<String>(), LoreLogOrder.reachable("gone", mapOf("a" to emptyList())))
    }

    /** A parent outside the window must not stall the walk. */
    @Test
    fun `unknown parents are ignored`() {
        val ordered = LoreLogOrder.topological(
            listOf(input("a", 2, "gone"), input("b", 1, "gone")),
        )

        assertEquals(listOf("a", "b"), ordered.map { it.hash })
    }
}
