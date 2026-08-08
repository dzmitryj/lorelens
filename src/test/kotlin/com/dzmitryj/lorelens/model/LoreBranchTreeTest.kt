package com.dzmitryj.lorelens.model

import org.junit.Assert.assertEquals
import org.junit.Test

class LoreBranchTreeTest {

    private fun id(seed: Int) = LoreBranchId(ByteArray(16) { seed.toByte() })

    private fun branch(name: String, self: Int, parent: Int? = null) = LoreBranch(
        id = id(self),
        name = name,
        category = "",
        location = LoreBranchLocation.LOCAL,
        latest = LoreRevisionId(ByteArray(32)),
        creator = "",
        createdMillis = 0,
        isCurrent = false,
        isArchived = false,
        branchPoints = parent?.let {
            listOf(LoreBranchPoint(id(it), LoreRevisionId(ByteArray(32) { 1 })))
        }.orEmpty(),
    )

    /** The shape the repository actually has. */
    @Test
    fun `developer branches hang off dev-main, which hangs off main`() {
        val tree = LoreBranchTree.build(
            listOf(
                branch("main", 1),
                branch("dev-main", 2, parent = 1),
                branch("dev-alberto", 3, parent = 2),
                branch("dev-dima", 4, parent = 2),
            ),
        )

        val main = tree.single()
        assertEquals("main", main.name)

        val devMain = main.children.single()
        assertEquals("dev-main", devMain.name)
        assertEquals(listOf("dev-alberto", "dev-dima"), devMain.children.map { it.name })
    }

    /** Lanes read root first, then down each limb. */
    @Test
    fun `order walks the tree depth first`() {
        val order = LoreBranchTree.order(
            listOf(
                branch("dev-dima", 4, parent = 2),
                branch("main", 1),
                branch("dev-main", 2, parent = 1),
                branch("dev-alberto", 3, parent = 2),
            ),
        )

        assertEquals(listOf("main", "dev-main", "dev-alberto", "dev-dima"), order)
    }

    /** An archived or absent parent leaves the branch standing on its own. */
    @Test
    fun `a branch whose parent is missing becomes a root`() {
        val tree = LoreBranchTree.build(
            listOf(
                branch("main", 1),
                branch("orphan", 9, parent = 42),
            ),
        )

        assertEquals(listOf("main", "orphan"), tree.map { it.name }.sorted())
    }

    /** A hung UI would be worse than a flattened tree, so cycles terminate. */
    @Test
    fun `a cycle does not hang and still lists every branch`() {
        val order = LoreBranchTree.order(
            listOf(
                branch("a", 1, parent = 2),
                branch("b", 2, parent = 1),
            ),
        )

        assertEquals(listOf("a", "b"), order.sorted())
    }
}
