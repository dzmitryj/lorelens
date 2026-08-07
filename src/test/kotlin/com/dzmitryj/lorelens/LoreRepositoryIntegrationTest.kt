package com.dzmitryj.lorelens

import com.dzmitryj.lorelens.api.LoreClient
import com.dzmitryj.lorelens.api.LoreDiffApi
import com.dzmitryj.lorelens.api.LoreHistoryApi
import com.dzmitryj.lorelens.api.LoreLockApi
import com.dzmitryj.lorelens.api.LoreStatusApi
import com.dzmitryj.lorelens.api.LoreSyncApi
import com.dzmitryj.lorelens.api.LoreWriteApi
import com.dzmitryj.lorelens.changes.LoreContentRevision
import com.dzmitryj.lorelens.changes.LoreRevisionNumber
import com.dzmitryj.lorelens.model.LoreFileAction
import com.dzmitryj.lorelens.model.LoreMetadata
import com.intellij.openapi.vcs.LocalFilePath
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
        LoreWriteApi.stage(repository, listOf("e.txt"))
        LoreWriteApi.commit(repository, "add e.txt")

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
        LoreWriteApi.stage(repository, listOf("f.txt"))
        LoreWriteApi.commit(repository, "add f.txt")

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
    fun `locks can be acquired, listed and released`() {
        repository.resolve("j.txt").writeText("lockable")
        LoreWriteApi.stage(repository, listOf("j.txt"))
        LoreWriteApi.commit(repository, "add j.txt")

        LoreLockApi.acquire(repository, listOf("j.txt"))

        val held = LoreLockApi.query(repository)
        assertTrue("expected j.txt among $held", held.any { it.path == "j.txt" })
        assertTrue("expected a lock owner", held.first { it.path == "j.txt" }.owner.isNotEmpty())

        LoreLockApi.release(repository, listOf("j.txt"))

        assertTrue(
            "expected no locks after release",
            LoreLockApi.query(repository).none { it.path == "j.txt" },
        )
    }

    /**
     * The loop a second developer performs: clone what someone else pushed,
     * then sync a later revision into an existing checkout.
     */
    @Test
    fun `a pushed revision can be cloned and synced into another checkout`() {
        val url = "${server!!.url}/shared-${System.nanoTime()}"
        val origin = Files.createTempDirectory("lore-origin")
        LoreClient.createRepository(origin, url)

        origin.resolve("k.txt").writeText("first")
        LoreWriteApi.stage(origin, listOf("k.txt"))
        LoreWriteApi.commit(origin, "add k.txt")
        LoreWriteApi.push(origin)

        val clone = Files.createTempDirectory("lore-clone")
        LoreSyncApi.clone(clone, url)

        assertEquals("first", Files.readString(clone.resolve("k.txt")))

        origin.resolve("k.txt").writeText("second")
        LoreWriteApi.stage(origin, listOf("k.txt"))
        LoreWriteApi.commit(origin, "edit k.txt")
        LoreWriteApi.push(origin)

        LoreSyncApi.sync(clone)

        assertEquals("second", Files.readString(clone.resolve("k.txt")))
    }

    @Test
    fun `history reports revisions with their commit messages`() {
        repository.resolve("l.txt").writeText("historic")
        LoreWriteApi.stage(repository, listOf("l.txt"))
        LoreWriteApi.commit(repository, "a memorable message")

        val history = LoreHistoryApi.history(repository)

        assertEquals(1, history.size)
        assertEquals(1L, history.single().number)
        assertEquals("a memorable message", history.single().message)
    }

    /**
     * lore_file_write fails with INVALID_PATH when the output already exists.
     * Content revisions used to hand it a created temp file, so every base
     * content fetch failed and diffs and gutter markers were dead.
     */
    @Test
    fun `content can be read through a content revision`() {
        repository.resolve("n.txt").writeText("base content")
        LoreWriteApi.stage(repository, listOf("n.txt"))
        LoreWriteApi.commit(repository, "add n.txt")
        repository.resolve("n.txt").writeText("working copy")

        val status = LoreStatusApi.status(repository, scan = true)
        val revision = LoreRevisionNumber(status.revision!!.revision, status.revision!!.revisionNumber)

        val content = LoreContentRevision(
            repository,
            LocalFilePath(repository.resolve("n.txt").toString(), false),
            "n.txt",
            revision,
        ).contentAsBytes

        assertEquals("base content", content?.toString(Charsets.UTF_8))
    }

    /**
     * lore_file_diff is the only verb that yields patch text, and it can diff
     * two committed revisions without touching the working tree.
     */
    @Test
    fun `two committed revisions can be diffed as text`() {
        repository.resolve("p.txt").writeText("first line\n")
        LoreWriteApi.stage(repository, listOf("p.txt"))
        LoreWriteApi.commit(repository, "one")
        val first = LoreStatusApi.status(repository, scan = true).revision!!.revision

        repository.resolve("p.txt").writeText("first line\nsecond line\n")
        LoreWriteApi.stage(repository, listOf("p.txt"))
        LoreWriteApi.commit(repository, "two")
        val second = LoreStatusApi.status(repository, scan = true).revision!!.revision

        val patches = LoreDiffApi.fileDiff(repository, listOf("p.txt"), first.hex, second.hex)

        assertEquals(1, patches.size)
        assertTrue("expected an added line in:\n${patches.single().patch}",
            patches.single().patch.contains("+second line"))
    }

    /** Revision diff reports which files changed, but carries no patch text. */
    @Test
    fun `a revision diff lists the changed files`() {
        repository.resolve("q.txt").writeText("q")
        LoreWriteApi.stage(repository, listOf("q.txt"))
        LoreWriteApi.commit(repository, "add q")
        val first = LoreStatusApi.status(repository, scan = true).revision!!.revision

        repository.resolve("r.txt").writeText("r")
        LoreWriteApi.stage(repository, listOf("r.txt"))
        LoreWriteApi.commit(repository, "add r")
        val second = LoreStatusApi.status(repository, scan = true).revision!!.revision

        val changed = LoreDiffApi.revisionDiff(repository, first.hex, second.hex)

        assertTrue("expected r.txt in ${changed.map { it.path }}", changed.any { it.path == "r.txt" })
    }

    /**
     * Lore records MOVE as a first-class action, so following a rename is exact
     * rather than heuristic as it is in Git.
     */
    @Test
    fun `file history follows a move`() {
        repository.resolve("s.txt").writeText("moved content")
        LoreWriteApi.stage(repository, listOf("s.txt"))
        LoreWriteApi.commit(repository, "add s")

        Files.move(repository.resolve("s.txt"), repository.resolve("t.txt"))
        LoreWriteApi.stageMove(repository, "s.txt", "t.txt")
        LoreWriteApi.commit(repository, "move s to t")

        val history = LoreDiffApi.fileHistory(repository, "t.txt")

        assertTrue("expected history for t.txt, got $history", history.isNotEmpty())
        assertTrue(
            "expected a MOVE in ${history.map { it.action }}",
            history.any { it.action == LoreFileAction.MOVE },
        )
        assertEquals("move s to t", history.first().message)
    }

    /**
     * Metadata was previously filtered to StringValue only, which silently
     * discarded the timestamp and any hash-valued key. Asserts the whole record
     * survives, and that a multi-line message splits into subject and body.
     */
    @Test
    fun `revision metadata survives decoding`() {
        repository.resolve("u.txt").writeText("metadata")
        LoreWriteApi.stage(repository, listOf("u.txt"))
        LoreWriteApi.commit(repository, "feat(u): add u\n\nA body paragraph explaining why.")

        val entry = LoreHistoryApi.history(repository).first()

        assertEquals("feat(u): add u", entry.subject)
        assertEquals("A body paragraph explaining why.", entry.metadata.body)
        assertTrue(
            "expected a parseable timestamp, got ${entry.metadata.values[LoreMetadata.TIMESTAMP]}",
            entry.timestampMillis != null,
        )
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
