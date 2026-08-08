package com.dzmitryj.lorelens.ui

/**
 * Lays a repository out as one horizontal lane per branch, time running left to
 * right, with a connector wherever a revision's parent sits on another lane.
 *
 * That single rule covers both shapes worth seeing: a branch's first revision
 * hanging off whatever it was cut from, and a merge reaching back across to the
 * branch it pulled in.
 *
 * Pure: the painting lives elsewhere so the arithmetic can be tested.
 */
object LoreBranchGraphLayout {

    /** A revision as it arrives, already attributed to one branch. */
    data class Input(
        val hash: String,
        val number: Long,
        val branch: String,
        val parents: List<String>,
        val author: String?,
        val isMerge: Boolean,
    )

    data class Node(
        val hash: String,
        val number: Long,
        val lane: Int,
        val column: Int,
        val author: String?,
        val isMerge: Boolean,
    )

    /** A line from one node to another, always drawn parent to child. */
    data class Link(val from: Node, val to: Node)

    data class Graph(
        val lanes: List<String>,
        val nodes: List<Node>,
        val links: List<Link>,
    ) {
        val columns: Int get() = (nodes.maxOfOrNull { it.column } ?: -1) + 1

        fun nodeAt(hash: String): Node? = nodes.firstOrNull { it.hash == hash }
    }

    /** One branch's walk, which includes the ancestry it was cut from. */
    data class Walk(
        val branch: String,
        val branchPoint: Long,
        val revisions: List<Input>,
    )

    /**
     * Decides which branch each revision belongs to.
     *
     * History for a branch carries the ancestry it was cut from, so the same
     * revision comes back in several walks and cannot simply be attributed to
     * whichever walk produced it. A revision belongs to the most specific branch
     * that contains it: of the branches whose walk holds it and whose branch
     * point is older than it, the one cut most recently.
     */
    fun attribute(walks: List<Walk>): List<Input> {
        val claims = mutableMapOf<String, Pair<Walk, Input>>()

        walks.forEach { walk ->
            walk.revisions.forEach { revision ->
                val existing = claims[revision.hash]
                val better = when {
                    existing == null -> true
                    // Cut more recently, so more specific.
                    walk.branchPoint > existing.first.branchPoint -> true
                    walk.branchPoint < existing.first.branchPoint -> false
                    else -> walk.branch < existing.first.branch
                }
                // A revision at or before a branch point predates that branch.
                if (better && revision.number > walk.branchPoint) {
                    claims[revision.hash] = walk to revision
                }
            }
        }

        // Anything only ever seen at or before every branch point still belongs
        // somewhere: give it to the oldest branch that saw it.
        walks.sortedBy { it.branchPoint }.forEach { walk ->
            walk.revisions.forEach { revision ->
                claims.getOrPut(revision.hash) { walk to revision }
            }
        }

        return claims.values.map { (walk, revision) -> revision.copy(branch = walk.branch) }
    }

    /**
     * @param revisions every revision across every branch, each attributed to
     *   the branch that owns it.
     * @param order branch names in hierarchy order, which is the lane order.
     */
    fun layout(revisions: List<Input>, order: List<String> = emptyList()): Graph {
        if (revisions.isEmpty()) return Graph(emptyList(), emptyList(), emptyList())

        // Oldest first, so a column index is also chronological order.
        val ordered = revisions.distinctBy { it.hash }.sortedBy { it.number }

        val earliest = ordered.groupBy { it.branch }.mapValues { (_, all) -> all.first().number }

        // Hierarchy order -- main, then dev-main, then its children -- because
        // "which branch came from which" is what the lanes are for. Hoisting the
        // checked-out branch to the top broke exactly that, so it does not.
        val rank = order.withIndex().associate { (index, name) -> name to index }
        val lanes = earliest.keys.sortedWith(
            compareBy({ rank[it] ?: Int.MAX_VALUE }, { earliest.getValue(it) }, { it }),
        )
        val laneOf = lanes.withIndex().associate { (index, name) -> name to index }

        val nodes = ordered.mapIndexed { column, revision ->
            Node(
                hash = revision.hash,
                number = revision.number,
                lane = laneOf.getValue(revision.branch),
                column = column,
                author = revision.author,
                isMerge = revision.isMerge,
            )
        }
        val byHash = nodes.associateBy { it.hash }

        // Only cross-lane parents produce a line; a parent on the same lane is
        // already implied by the lane itself.
        val links = ordered.flatMap { revision ->
            val child = byHash.getValue(revision.hash)
            revision.parents.mapNotNull { byHash[it] }
                .filter { it.lane != child.lane }
                .map { Link(it, child) }
        }

        return Graph(lanes, nodes, links)
    }
}
