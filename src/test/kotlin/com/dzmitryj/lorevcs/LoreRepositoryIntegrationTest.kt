package com.dzmitryj.lorevcs

import com.dzmitryj.lorevcs.api.LoreClient
import com.dzmitryj.lorevcs.api.LoreStatusApi
import com.dzmitryj.lorevcs.api.LoreWriteApi
import com.dzmitryj.lorevcs.model.LoreFileAction
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assume.assumeTrue
import org.junit.Before
import org.junit.Test
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.writeText

/**
 * Round trips that the unit tests structurally cannot cover: a real repository,
 * created against a real server, read back through the FFI.
 */
class LoreRepositoryIntegrationTest {

    private var server: LoreTestServer? = null
    private lateinit var repository: Path

    @Before
    fun setUp() {
        assumeTrue("loreserver is not available for this platform", LoreTestServer.isAvailable())

        server = LoreTestServer.startNew()
        repository = Files.createTempDirectory("lore-repo")
        LoreClient.createRepository(repository, "${server!!.url}/test-${System.nanoTime()}")
    }

    @After
    fun tearDown() {
        server?.close()
        server = null
    }

    @Test
    fun `a new repository reports a valid administrative area`() {
        assertTrue(repository.resolve(".lore").toFile().isDirectory)
        assertTrue(repository.resolve(".lore/instance").toFile().isFile)
    }

    @Test
    fun `a clean repository reports no changes`() {
        val status = LoreStatusApi.status(repository, scan = true)

        assertTrue("expected no changed files, got ${status.files}", status.files.isEmpty())
        assertEquals("main", status.revision?.branchName)
    }

    /**
     * The core claim of the integration: marking a path dirty is enough for
     * status to see it, with no filesystem walk.
     */
    @Test
    fun `marking a file dirty makes status report exactly that file`() {
        repository.resolve("a.txt").writeText("hello lore")
        repository.resolve("nested").createDirectories()
        repository.resolve("nested/b.txt").writeText("second")

        LoreStatusApi.markDirty(repository, listOf("a.txt"))
        val status = LoreStatusApi.status(repository)

        val files = status.files.filter { it.path.endsWith(".txt") }
        assertEquals(listOf("a.txt"), files.map { it.path })
        assertEquals(LoreFileAction.ADD, files.single().action)
    }

    @Test
    fun `a scan finds files that were never marked`() {
        repository.resolve("c.txt").writeText("unmarked")

        val status = LoreStatusApi.status(repository, scan = true)

        assertTrue(
            "expected c.txt in ${status.files.map { it.path }}",
            status.files.any { it.path == "c.txt" },
        )
    }

    /**
     * The full loop M2 depends on: commit a file, edit it, and read the base
     * content back out of the repository.
     */
    @Test
    fun `content at a revision is readable after a commit`() {
        repository.resolve("e.txt").writeText("original")
        LoreTestRepository.stage(repository, listOf("e.txt"))
        LoreTestRepository.commit(repository, "add e.txt")

        repository.resolve("e.txt").writeText("edited")
        val status = LoreStatusApi.status(repository, scan = true)
        val revision = status.revision!!.revision
        assertTrue("expected a real revision, got $revision", !revision.isNone)

        val output = Files.createTempDirectory("lore-out").resolve("e.txt")
        LoreStatusApi.writeFile(repository, "e.txt", revision.hex, output)

        assertEquals("original", Files.readString(output))
    }

    /**
     * Settles whether reset moves the revision pointer, like git reset, or
     * restores file content, like git checkout --. Wiring the wrong one to the
     * Revert action would destroy uncommitted work, so RollbackEnvironment
     * stays unregistered until this passes.
     */
    @Test
    fun `reset restores file content and leaves the revision alone`() {
        repository.resolve("f.txt").writeText("original")
        LoreTestRepository.stage(repository, listOf("f.txt"))
        LoreTestRepository.commit(repository, "add f.txt")

        val before = LoreStatusApi.status(repository, scan = true).revision!!

        repository.resolve("f.txt").writeText("edited")
        LoreWriteApi.reset(repository, listOf("f.txt"))

        assertEquals("original", Files.readString(repository.resolve("f.txt")))

        val after = LoreStatusApi.status(repository, scan = true).revision!!
        assertEquals(before.revisionNumber, after.revisionNumber)
        assertEquals(before.revision.hex, after.revision.hex)
    }

    /**
     * The write loop the checkin environment performs: mark, stage explicitly,
     * commit, and end up with a clean tree at a new revision.
     */
    @Test
    fun `stage and commit advances the revision and leaves the tree clean`() {
        repository.resolve("g.txt").writeText("committed")

        LoreStatusApi.markDirty(repository, listOf("g.txt"))
        LoreWriteApi.stage(repository, listOf("g.txt"))
        LoreWriteApi.commit(repository, "add g.txt")

        val status = LoreStatusApi.status(repository, scan = true)

        assertTrue("expected a clean tree, got ${status.files}", status.files.isEmpty())
        assertEquals(1L, status.revision!!.revisionNumber)
    }

    @Test
    fun `renames are recorded as moves`() {
        repository.resolve("h.txt").writeText("movable")
        LoreWriteApi.stage(repository, listOf("h.txt"))
        LoreWriteApi.commit(repository, "add h.txt")

        Files.move(repository.resolve("h.txt"), repository.resolve("i.txt"))
        LoreWriteApi.stageMove(repository, "h.txt", "i.txt")

        val moved = LoreStatusApi.status(repository).files.single { it.path == "i.txt" }

        assertEquals(LoreFileAction.MOVE, moved.action)
        assertEquals("h.txt", moved.fromPath)
    }

    @Test
    fun `hashing reports content addresses`() {
        repository.resolve("d.txt").writeText("addressable")

        val hashes = LoreStatusApi.hash(repository, listOf("d.txt"))

        assertEquals(1, hashes.size)
        assertEquals("d.txt", hashes.single().path)
        assertTrue(hashes.single().hash.hex.length == 64)
    }
}
