package com.dzmitryj.lorelens

import com.dzmitryj.lorelens.api.LoreBranchApi
import com.dzmitryj.lorelens.api.LoreHistoryApi
import com.dzmitryj.lorelens.api.LoreStatusApi
import com.dzmitryj.lorelens.model.LoreBranchLocation
import com.dzmitryj.lorelens.ui.LoreBranchGraphLayout
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
    }
}
