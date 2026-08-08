package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.api.LoreHistoryApi
import com.dzmitryj.lorelens.api.LoreHistoryEntry
import com.dzmitryj.lorelens.model.LoreBranch
import com.dzmitryj.lorelens.model.LoreBranchLocation
import java.nio.file.Path

/**
 * Which branch each revision belongs to.
 *
 * Branch history carries the ancestry it was cut from, so a revision comes back
 * in several walks and has to be attributed rather than taken from whichever
 * walk produced it. Shared, because both the graph and the merge labels in
 * History need the same answer.
 *
 * Costs one history call per branch, so callers do it off the EDT and cache it.
 */
object LoreBranchWalks {

    /** The attribution, and the entries it was built from, so nobody walks twice. */
    data class Result(
        val attributed: List<LoreBranchGraphLayout.Input>,
        val entries: Map<String, LoreHistoryEntry>,
    )

    fun attribute(root: Path, branches: List<LoreBranch>, limit: Int): Result {
        val entries = mutableMapOf<String, LoreHistoryEntry>()

        val walks = branches.groupBy { it.name }.map { (name, sides) ->
            val local = sides.firstOrNull { it.location == LoreBranchLocation.LOCAL }
            val remote = sides.firstOrNull { it.location == LoreBranchLocation.REMOTE }
            val branch = local ?: remote ?: sides.first()

            // Walking from the remote tip is what reaches revisions this
            // checkout has not synced; from the local tip they are invisible.
            val from = remote?.latest?.takeIf { !it.isNone }?.hex.orEmpty()
            val revisions = runCatching { LoreHistoryApi.history(root, limit, branch = name, from = from) }
                .getOrDefault(emptyList())
                .ifEmpty {
                    runCatching { LoreHistoryApi.history(root, limit, branch = name) }
                        .getOrDefault(emptyList())
                }
            revisions.forEach { entries.putIfAbsent(it.revision.hex, it) }

            // Everything past the local tip is on the branch but not here yet.
            val syncedThrough = local?.latest
                ?.let { tip -> revisions.firstOrNull { it.revision.hex == tip.hex }?.number }
                ?: Long.MAX_VALUE

            val branchPoint = branch.branchPoints
                .mapNotNull { point -> revisions.firstOrNull { it.revision.hex == point.revision.hex }?.number }
                .maxOrNull()
                ?: 0L

            LoreBranchGraphLayout.Walk(
                branch = name,
                branchPoint = branchPoint,
                revisions = revisions.map { entry ->
                    LoreBranchGraphLayout.Input(
                        hash = entry.revision.hex,
                        number = entry.number,
                        branch = name,
                        parents = entry.parents.map { it.hex },
                        author = entry.author,
                        isMerge = entry.isMerge,
                        synced = entry.number <= syncedThrough,
                    )
                },
            )
        }

        return Result(LoreBranchGraphLayout.attribute(walks), entries)
    }

    /**
     * For each merge, the branch it pulled in and the branch it landed on, as
     * "dev-dima into dev-main". The second parent is the side being merged; the
     * merge itself sits on the branch that received it.
     */
    fun mergeLabels(attributed: List<LoreBranchGraphLayout.Input>): Map<String, Pair<String, String>> {
        val branchOf = attributed.associate { it.hash to it.branch }

        return attributed
            .filter { it.isMerge }
            .mapNotNull { merge ->
                val from = merge.parents.getOrNull(1)?.let { branchOf[it] } ?: return@mapNotNull null
                val into = merge.branch
                if (from == into) null else merge.hash to (from to into)
            }
            .toMap()
    }
}
