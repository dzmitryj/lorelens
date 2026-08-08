package com.dzmitryj.lorelens

import com.dzmitryj.lorelens.api.LoreBranchApi
import com.dzmitryj.lorelens.api.LoreClient
import com.dzmitryj.lorelens.api.LoreDiffApi
import com.dzmitryj.lorelens.api.LoreHistoryApi
import com.dzmitryj.lorelens.api.LoreLockApi
import com.dzmitryj.lorelens.api.LoreStatusApi
import com.dzmitryj.lorelens.api.LoreSyncApi
import com.dzmitryj.lorelens.api.LoreWriteApi
import com.dzmitryj.lorelens.blame.LoreBlameEngine
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
     * The content cache was keyed on the working tree's hash, which does not
     * depend on the revision asked for, so every revision of a file collided on
     * one entry and the second read returned the first one's bytes.
     */
    @Test
    fun `content revisions of one file do not share a cache entry`() {
        val path = repository.resolve("v.txt")
        val filePath = LocalFilePath(path.toString(), false)

        path.writeText("one")
        LoreWriteApi.stage(repository, listOf("v.txt"))
        LoreWriteApi.commit(repository, "add v")
        val first = LoreStatusApi.status(repository, scan = true).revision!!

        path.writeText("two")
        LoreWriteApi.stage(repository, listOf("v.txt"))
        LoreWriteApi.commit(repository, "change v")
        val second = LoreStatusApi.status(repository, scan = true).revision!!

        fun read(revision: LoreRevisionNumber) =
            LoreContentRevision(repository, filePath, "v.txt", revision).contentAsBytes
                ?.toString(Charsets.UTF_8)

        assertEquals("one", read(LoreRevisionNumber(first.revision, first.revisionNumber)))
        assertEquals("two", read(LoreRevisionNumber(second.revision, second.revisionNumber)))
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

    /**
     * Attribution across three commits with known edits: the untouched line
     * keeps the first revision, the edited one moves to the second, and the
     * appended one belongs to the third.
     */
    @Test
    fun `blame attributes each line to the revision that introduced it`() {
        val file = repository.resolve("v.txt")

        file.writeText("alpha\nbeta\n")
        LoreWriteApi.stage(repository, listOf("v.txt"))
        LoreWriteApi.commit(repository, "one")
        val first = LoreStatusApi.status(repository, scan = true).revision!!.revision

        file.writeText("alpha\nBETA\n")
        LoreWriteApi.stage(repository, listOf("v.txt"))
        LoreWriteApi.commit(repository, "two")
        val second = LoreStatusApi.status(repository, scan = true).revision!!.revision

        file.writeText("alpha\nBETA\ngamma\n")
        LoreWriteApi.stage(repository, listOf("v.txt"))
        LoreWriteApi.commit(repository, "three")
        val third = LoreStatusApi.status(repository, scan = true).revision!!.revision

        val history = LoreDiffApi.fileHistory(repository, "v.txt")
        assertEquals(3, history.size)

        val ordered = history.reversed()
        var attribution = List(2) { ordered.first() }
        ordered.zipWithNext().forEach { (previous, next) ->
            val patch = LoreDiffApi
                .fileDiff(repository, listOf("v.txt"), previous.revision.hex, next.revision.hex, contextLines = 0)
                .single()
                .patch
            attribution = LoreBlameEngine.advance(attribution, patch, next)
        }

        assertEquals(
            listOf(first.hex, second.hex, third.hex),
            attribution.map { it.revision.hex },
        )
    }

    /**
     * A revision's changes are what it did to its parent. The log tab used to
     * pair each changed file with the working tree instead, which answers a
     * different and usually wrong question -- here it would also report the
     * uncommitted file, which this revision did not touch.
     */
    @Test
    fun `a revision diff reports only what that revision changed`() {
        repository.resolve("w.txt").writeText("first")
        LoreWriteApi.stage(repository, listOf("w.txt"))
        LoreWriteApi.commit(repository, "add w")
        val parent = LoreStatusApi.status(repository, scan = true).revision!!.revision

        repository.resolve("x.txt").writeText("second")
        LoreWriteApi.stage(repository, listOf("x.txt"))
        LoreWriteApi.commit(repository, "add x")
        val child = LoreStatusApi.status(repository, scan = true).revision!!.revision

        // Uncommitted, so it belongs to no revision.
        repository.resolve("y.txt").writeText("never committed")

        val changed = LoreDiffApi.revisionDiff(repository, parent.hex, child.hex).map { it.path }

        assertEquals(listOf("x.txt"), changed)
    }

    @Test
    fun `branches are listed with the current one marked`() {
        repository.resolve("b.txt").writeText("branching")
        LoreWriteApi.stage(repository, listOf("b.txt"))
        LoreWriteApi.commit(repository, "add b")

        val branches = LoreBranchApi.list(repository)
        val current = branches.singleOrNull { it.isCurrent }

        assertTrue("no branch reported as current in $branches", current != null)
        assertEquals(
            LoreStatusApi.status(repository, scan = true).revision!!.branchName,
            current!!.name,
        )
    }

    /** The filter has to name a branch Lore knows, or it silently returns nothing. */
    @Test
    fun `history filtered to the current branch matches unfiltered history`() {
        repository.resolve("c.txt").writeText("filtered")
        LoreWriteApi.stage(repository, listOf("c.txt"))
        LoreWriteApi.commit(repository, "add c")

        val branch = LoreStatusApi.status(repository, scan = true).revision!!.branchName
        val unfiltered = LoreHistoryApi.history(repository).map { it.revision.hex }
        val filtered = LoreHistoryApi.history(repository, branch = branch).map { it.revision.hex }

        assertTrue("unfiltered history was empty", unfiltered.isNotEmpty())
        assertEquals(unfiltered, filtered)
    }

    /** Revision-only status skips the staged set, so it must still carry the revision. */
    @Test
    fun `revision status reports the head without the staged set`() {
        repository.resolve("r.txt").writeText("head")
        LoreWriteApi.stage(repository, listOf("r.txt"))
        LoreWriteApi.commit(repository, "add r")

        val full = LoreStatusApi.status(repository, scan = true).revision!!
        val lean = LoreStatusApi.revisionStatus(repository)!!

        assertEquals(full.revision.hex, lean.revision.hex)
        assertEquals(full.branchName, lean.branchName)
        assertTrue(LoreStatusApi.status(repository, staged = false).files.isEmpty())
    }

    /**
     * Lore reports both sides of a merge in the history entry's parent array.
     * The generator dropped that field without a word, so every revision looked
     * like it had one parent and the log drew a straight line through merges.
     */
    @Test
    fun `a merge revision reports two parents`() {
        repository.resolve("base.txt").writeText("base")
        LoreWriteApi.stage(repository, listOf("base.txt"))
        LoreWriteApi.commit(repository, "base")

        val main = LoreStatusApi.status(repository, scan = true).revision!!.branchName

        LoreBranchApi.create(repository, "side")
        LoreBranchApi.switch(repository, "side")
        repository.resolve("side.txt").writeText("from the side branch")
        LoreWriteApi.stage(repository, listOf("side.txt"))
        LoreWriteApi.commit(repository, "side work")

        LoreBranchApi.switch(repository, main)
        repository.resolve("main.txt").writeText("from main")
        LoreWriteApi.stage(repository, listOf("main.txt"))
        LoreWriteApi.commit(repository, "main work")

        LoreBranchApi.mergeInto(repository, "side", "merge side into $main")

        val history = LoreHistoryApi.history(repository, 20)
        val ordinary = history.single { it.subject == "main work" }
        assertEquals(1, ordinary.parents.size)
        assertTrue("an ordinary revision is not a merge", !ordinary.isMerge)

        val merge = history.firstOrNull { it.isMerge }
        assertTrue("expected a merge in ${history.map { it.subject }}", merge != null)
        assertEquals(2, merge!!.parents.size)
        assertTrue("both parents must be real", merge.parents.none { it.isNone })
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
