package com.dzmitryj.lorelens

import com.dzmitryj.lorelens.api.LoreBranchApi
import com.dzmitryj.lorelens.api.LoreClient
import com.dzmitryj.lorelens.api.LoreHistoryApi
import com.dzmitryj.lorelens.api.LoreStatusApi
import com.dzmitryj.lorelens.api.LoreSyncApi
import com.dzmitryj.lorelens.api.LoreWriteApi
import com.dzmitryj.lorelens.model.LoreBranchLocation
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assume.assumeTrue
import org.junit.Before
import org.junit.Test
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.writeText

/**
 * Can a behind checkout see the revisions it has not synced yet? Lore has no
 * remote-history verb, so this settles whether the local metadata already
 * carries them or whether "behind by N" is the most the UI can honestly show.
 */
class LoreUnsyncedProbeTest {

    private var server: LoreTestServer? = null

    @Before
    fun setUp() {
        assumeTrue("loreserver is not available for this platform", LoreTestServer.isAvailable())
        server = LoreTestServer.startNew()
    }

    @After
    fun tearDown() {
        server?.close()
        server = null
    }

    /**
     * The result the log tab depends on: walking from the remote branch tip
     * reaches revisions this checkout has not synced, with their metadata.
     * Walking from the local tip stops at what is here.
     */
    @Test
    fun `a behind checkout can list the revisions it has not synced`() {
        val url = "${server!!.url}/unsynced-${System.nanoTime()}"
        val origin = Files.createTempDirectory("lore-origin")
        LoreClient.createRepository(origin, url)

        commit(origin, "base", "one")
        LoreWriteApi.push(origin)

        val clone = Files.createTempDirectory("lore-clone")
        LoreSyncApi.clone(clone, url)

        // Origin moves three revisions ahead; the clone stays put.
        commit(origin, "second", "two")
        commit(origin, "third", "three")
        commit(origin, "fourth", "four")
        LoreWriteApi.push(origin)

        val status = LoreStatusApi.status(clone, scan = true).revision!!
        assertEquals(1L, status.revisionNumber)
        assertTrue("clone should know it is behind", status.remoteAhead)

        // Only what is synced.
        assertEquals(listOf(1L), LoreHistoryApi.history(clone, 50).map { it.number })

        val remoteTip = LoreBranchApi.list(clone)
            .single { it.name == status.branchName && it.location == LoreBranchLocation.REMOTE }
            .latest

        assertEquals(
            listOf(4L, 3L, 2L, 1L),
            LoreHistoryApi.history(clone, 50, from = remoteTip.hex).map { it.number },
        )
    }

    private fun commit(root: Path, name: String, text: String) {
        root.resolve("$name.txt").writeText(text)
        LoreWriteApi.stage(root, listOf("$name.txt"))
        LoreWriteApi.commit(root, "add $name")
    }
}
