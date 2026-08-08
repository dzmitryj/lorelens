package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.api.LoreHistoryApi
import com.dzmitryj.lorelens.api.LoreHistoryEntry
import com.dzmitryj.lorelens.model.LoreBranch
import com.dzmitryj.lorelens.model.LoreBranchLocation
import com.dzmitryj.lorelens.model.LoreBranchTree
import java.nio.file.Path

/**
 * Which branch each revision belongs to, and which revisions this checkout has.
 *
 * Two things about Lore drive the shape of this, both established against a real
 * repository rather than assumed:
 *
 * - `lore_revision_history` returns nothing when given a branch *and* a
 *   revision. Walking from a branch tip therefore passes the revision alone.
 * - Revision numbers restart per branch: two branches can each hold an r175
 *   with different hashes. Nothing here compares numbers across branches.
 *
 * Costs a couple of history calls per branch, so callers do it off the EDT and
 * cache it.
 */
object LoreBranchWalks {

    /** The attribution, and the entries it was built from, so nobody walks twice. */
    data class Result(
        val attributed: List<LoreBranchGraphLayout.Input>,
        val entries: Map<String, LoreHistoryEntry>,
    )

    fun attribute(root: Path, branches: List<LoreBranch>, limit: Int): Result {
        val entries = mutableMapOf<String, LoreHistoryEntry>()
        val tree = LoreBranchTree.build(branches)
        val depths = mutableMapOf<String, Int>()
        val parents = mutableMapOf<String, String?>()

        fun descend(node: LoreBranchTree.Node, depth: Int, parent: String?) {
            depths[node.name] = depth
            parents[node.name] = parent
            node.children.forEach { descend(it, depth + 1, node.name) }
        }
        tree.forEach { descend(it, 0, null) }

        val walks = branches.groupBy { it.name }.map { (name, sides) ->
            val local = sides.firstOrNull { it.location == LoreBranchLocation.LOCAL }
            val remote = sides.firstOrNull { it.location == LoreBranchLocation.REMOTE }

            // From the remote tip, so revisions this checkout has not synced are
            // included. No branch argument: passing both returns nothing.
            val reachable = walk(root, remote?.latest?.hex, limit)
                .ifEmpty { walk(root, local?.latest?.hex, limit) }
                .ifEmpty {
                    runCatching { LoreHistoryApi.history(root, limit, branch = name) }
                        .getOrDefault(emptyList())
                }
            reachable.forEach { entries.putIfAbsent(it.revision.hex, it) }

            // Membership rather than numbering: what the local tip reaches is
            // what this checkout has.
            val here = local?.latest?.hex
                ?.let { tip -> walk(root, tip, limit).map { it.revision.hex }.toHashSet() }

            LoreBranchGraphLayout.Walk(
                branch = name,
                depth = depths[name] ?: 0,
                parent = parents[name],
                revisions = reachable.map { entry ->
                    LoreBranchGraphLayout.Input(
                        hash = entry.revision.hex,
                        number = entry.number,
                        branch = name,
                        parents = entry.parents.map { it.hex },
                        author = entry.author,
                        isMerge = entry.isMerge,
                        synced = here == null || entry.revision.hex in here,
                        timestamp = entry.timestampMillis ?: 0L,
                    )
                },
            )
        }

        return Result(LoreBranchGraphLayout.attribute(walks), entries)
    }

    private fun walk(root: Path, from: String?, limit: Int): List<LoreHistoryEntry> {
        if (from.isNullOrEmpty()) return emptyList()
        return runCatching { LoreHistoryApi.history(root, limit, from = from) }.getOrDefault(emptyList())
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
