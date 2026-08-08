package com.dzmitryj.lorelens.update

import com.dzmitryj.lorelens.model.LoreFileAction
import com.dzmitryj.lorelens.model.LoreFileStatus
import com.dzmitryj.lorelens.model.LoreNodeType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LoreSyncSessionTest {

    private fun file(
        path: String,
        staged: Boolean = true,
        nodeType: LoreNodeType = LoreNodeType.FILE,
    ) = LoreFileStatus(
        path = path,
        size = 0,
        action = LoreFileAction.KEEP,
        nodeType = nodeType,
        staged = staged,
        dirty = false,
        conflicted = false,
        conflictUnresolved = false,
        fromPath = null,
    )

    @Test
    fun `only staged files are re-staged`() {
        val plan = LoreSyncSession.restagePlan(
            listOf(file("a.txt"), file("b.txt", staged = false)),
        ) { true }

        assertEquals(listOf("a.txt"), plan.present)
        assertTrue(plan.absent.isEmpty())
    }

    /** Staging a directory stages only what is already dirty, so never do it. */
    @Test
    fun `directories are not re-staged`() {
        val plan = LoreSyncSession.restagePlan(
            listOf(file("dir", nodeType = LoreNodeType.DIRECTORY), file("dir/a.txt")),
        ) { true }

        assertEquals(listOf("dir/a.txt"), plan.present)
    }

    /** A staged deletion has no file on disk; it still has to go back. */
    @Test
    fun `paths that no longer exist are separated from the batch`() {
        val plan = LoreSyncSession.restagePlan(
            listOf(file("kept.txt"), file("deleted.txt")),
        ) { it != "deleted.txt" }

        assertEquals(listOf("kept.txt"), plan.present)
        assertEquals(listOf("deleted.txt"), plan.absent)
    }

    @Test
    fun `nothing staged means nothing to do`() {
        assertTrue(LoreSyncSession.restagePlan(listOf(file("a.txt", staged = false))) { true }.isEmpty)
    }
}
