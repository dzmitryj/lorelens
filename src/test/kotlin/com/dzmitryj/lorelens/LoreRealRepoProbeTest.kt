package com.dzmitryj.lorelens

import com.dzmitryj.lorelens.api.LoreBranchApi
import com.dzmitryj.lorelens.api.LoreHistoryApi
import com.dzmitryj.lorelens.api.LoreStatusApi
import com.dzmitryj.lorelens.model.LoreBranchLocation
import com.dzmitryj.lorelens.ui.LoreBranchGraphLayout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assume.assumeTrue
import org.junit.Test
import java.nio.file.Files
import java.nio.file.Path

/**
 * Read-only probe of a real repository. Skipped unless -Dlore.probe.repo is set.
 */
class LoreRealRepoProbeTest {

    private val root: Path? = System.getProperty("lore.probe.repo")?.let(Path::of)

    private fun repository(): Path {
        val repository = root
        assumeTrue("no -Dlore.probe.repo", repository != null && Files.isDirectory(repository.resolve(".lore")))
        return repository!!
    }

    @Test
    fun `probe branch attribution`() {
        val path = repository()
        val status = LoreStatusApi.status(path, scan = false).revision
        println("PROBE head=${status?.revisionNumber} branch=${status?.branchName} rev=${status?.revision?.short}")

        val branches = LoreBranchApi.list(path).filterNot { it.isArchived }
        branches.forEach {
            println(
                "PROBE branch name=${it.name} loc=${it.location} current=${it.isCurrent} " +
                    "latest=${it.latest.short} id=${it.id} points=${it.branchPoints.map { p -> "${p.branch}@${p.revision.short}" }}",
            )
        }

        val walked = com.dzmitryj.lorelens.ui.LoreBranchWalks.attribute(path, branches, 500)
        val attributed = walked.attributed
        println("PROBE attributed total=${attributed.size}")
        attributed.groupingBy { it.branch }.eachCount().toSortedMap().forEach { (branch, count) ->
            println("PROBE attributed $branch=$count")
        }
        println("PROBE unsynced=${attributed.count { !it.synced }}")
        attributed.filterNot { it.synced }.sortedByDescending { it.timestamp }.take(8).forEach {
            println("PROBE unsynced r${it.number} branch=${it.branch} merge=${it.isMerge}")
        }
        attributed.filter { it.isMerge }.sortedByDescending { it.timestamp }.take(6).forEach {
            println("PROBE merge r${it.number} branch=${it.branch} parents=${it.parents.size}")
        }
        println("PROBE distinct hashes=${attributed.map { it.hash }.distinct().size}")

        assertNotNull("a checkout must report a revision", status)
        assertTrue("branches must attribute at least one revision", branches.isEmpty() || attributed.isNotEmpty())
        val names = branches.mapTo(HashSet()) { it.name }
        assertTrue(
            "every attribution must name a listed branch",
            attributed.all { it.branch in names },
        )
    }

    /** What the History tab's walk actually gets: are parents filled per entry? */
    @Test
    fun `probe parent links`() {
        val path = repository()
        val branches = LoreBranchApi.list(path).filterNot { it.isArchived }
        val tip = branches.firstOrNull {
            it.name == "dev-main" && it.location == LoreBranchLocation.REMOTE
        }?.latest ?: return

        val history = LoreHistoryApi.history(path, 500, from = tip.hex)
        println("PROBE walk entries=${history.size}")
        println("PROBE with parents=${history.count { it.parents.isNotEmpty() }} merges=${history.count { it.isMerge }}")
        history.take(8).forEach {
            println("PROBE entry r${it.number} rev=${it.revision.short} parents=${it.parents.map { p -> p.short }}")
        }

        // Do parent hashes point at entries in the same walk?
        val known = history.mapTo(HashSet()) { it.revision.hex }
        val dangling = history.flatMap { it.parents }.count { it.hex !in known }
        println("PROBE dangling parents=$dangling")

        val lanes = com.dzmitryj.lorelens.ui.LoreHistoryLanes.layout(
            history.map { entry ->
                com.dzmitryj.lorelens.ui.LoreHistoryLanes.Input(
                    entry.revision.hex,
                    entry.parents.map { it.hex },
                    branch = null,
                )
            },
            order = emptyList(),
        ).rows
        println("PROBE lanes maxWidth=${lanes.maxOfOrNull { it.width }}")
        println("PROBE lanes rows with lines=${lanes.count { it.incoming.isNotEmpty() || it.outgoing.isNotEmpty() || it.through.isNotEmpty() }}")

        assertTrue("a reachable tip must walk to at least one entry", history.isNotEmpty())
        assertEquals("every entry gets a lane row", history.size, lanes.size)
    }

    /** The union view: every branch together, ordered, laned. */
    @Test
    fun `probe union graph`() {
        val path = repository()
        val branches = LoreBranchApi.list(path).filterNot { it.isArchived }
        val walked = com.dzmitryj.lorelens.ui.LoreBranchWalks.attribute(path, branches, 200)

        val ordered = com.dzmitryj.lorelens.ui.LoreLogOrder.topological(walked.attributed)
        println("PROBE union rows=${ordered.size}")

        val position = ordered.withIndex().associate { (index, input) -> input.hash to index }
        val inversions = ordered.withIndex().sumOf { (index, input) ->
            input.parents.count { (position[it] ?: Int.MAX_VALUE) < index }
        }
        println("PROBE union parent-above-child=$inversions")
        assertEquals("topological order must keep every child above its parents", 0, inversions)

        val known = ordered.mapTo(HashSet()) { it.hash }
        println("PROBE union dangling=${ordered.flatMap { it.parents }.count { it !in known }}")

        val lanes = com.dzmitryj.lorelens.ui.LoreHistoryLanes.layout(
            ordered.map { com.dzmitryj.lorelens.ui.LoreHistoryLanes.Input(it.hash, it.parents, it.branch) },
            order = com.dzmitryj.lorelens.model.LoreBranchTree.order(branches),
        ).rows
        println("PROBE union maxWidth=${lanes.maxOfOrNull { it.width }}")
        println("PROBE union curves=${lanes.sumOf { row -> (row.incoming + row.outgoing).count { it.from != it.to } }}")

        val broken = lanes.zipWithNext().count { (upper, lower) ->
            (upper.outgoing.map { it.to } + upper.through).toSet() !=
                (lower.incoming.map { it.from } + lower.through).toSet()
        }
        println("PROBE union broken boundaries=$broken")
        assertEquals("edges must be continuous across every row boundary", 0, broken)

        // What the History tab shows: the current branch's ancestry only.
        val parents = walked.entries.mapValues { (_, entry) -> entry.parents.map { it.hex } }
        val tip = branches
            .filter { it.name == "dev-main" && !it.latest.isNone }
            .minByOrNull { if (it.location == LoreBranchLocation.REMOTE) 0 else 1 }
            ?.latest?.hex ?: return
        val keep = com.dzmitryj.lorelens.ui.LoreLogOrder.reachable(tip, parents)
        val scoped = walked.attributed.filter { it.hash in keep }
        println("PROBE scoped rows=${scoped.size} of ${walked.attributed.size}")
        assertTrue("scoping to an ancestry must never grow the set", scoped.size <= walked.attributed.size)
        assertTrue("the tip itself must survive its own scoping", scoped.any { it.hash == tip })
        scoped.groupingBy { it.branch }.eachCount().toSortedMap().forEach { (branch, count) ->
            println("PROBE scoped $branch=$count")
        }
        val scopedLanes = com.dzmitryj.lorelens.ui.LoreHistoryLanes.layout(
            com.dzmitryj.lorelens.ui.LoreLogOrder.topological(scoped)
                .map { com.dzmitryj.lorelens.ui.LoreHistoryLanes.Input(it.hash, it.parents, it.branch) },
            order = com.dzmitryj.lorelens.model.LoreBranchTree.order(branches),
        ).rows
        println("PROBE scoped maxWidth=${scopedLanes.maxOfOrNull { it.width }}")
        println(
            "PROBE scoped curves=${scopedLanes.sumOf { row -> (row.incoming + row.outgoing).count { it.from != it.to } }} " +
                "broken=${scopedLanes.zipWithNext().count { (upper, lower) ->
                    (upper.outgoing.map { it.to } + upper.through).toSet() !=
                        (lower.incoming.map { it.from } + lower.through).toSet()
                }}",
        )
    }
}
