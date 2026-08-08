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
        /** False for a revision on the branch that this checkout does not have. */
        val synced: Boolean = true,
        /**
         * Ordering key. Revision numbers restart per branch -- two branches can
         * both hold an r175 with different hashes -- so time is what puts the
         * columns in order.
         */
        val timestamp: Long = 0,
    )

    data class Node(
        val hash: String,
        val number: Long,
        val lane: Int,
        val column: Int,
        val author: String?,
        val isMerge: Boolean,
        val synced: Boolean,
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

    /**
     * One branch's reachable history, and how deep the branch sits in the tree.
     *
     * Depth is what decides ownership: a child's walk contains everything its
     * parent's does, so the deepest branch reaching a revision is the one it was
     * made on.
     */
    data class Walk(
        val branch: String,
        val depth: Int,
        /** The branch this one was cut from, if it is in the listing. */
        val parent: String?,
        val revisions: List<Input>,
    )

    /**
     * Decides which branch each revision belongs to.
     *
     * A branch's history carries the ancestry it was cut from, so the same
     * revision comes back in several walks. A branch owns a revision only when
     * its parent does not also reach it -- otherwise the branch merely inherited
     * it, and it was made further up.
     *
     * Reachability rather than revision numbers, because numbers restart per
     * branch and comparing them across branches means nothing.
     */
    fun attribute(walks: List<Walk>): List<Input> {
        val reach = walks.associate { walk -> walk.branch to walk.revisions.mapTo(HashSet()) { it.hash } }
        val claims = mutableMapOf<String, Pair<Walk, Input>>()

        walks.forEach { walk ->
            walk.revisions.forEach { revision ->
                // Inherited, not made here.
                if (reach[walk.parent]?.contains(revision.hash) == true) return@forEach

                val existing = claims[revision.hash]?.first
                val better = when {
                    existing == null -> true
                    walk.depth > existing.depth -> true
                    walk.depth < existing.depth -> false
                    else -> walk.branch < existing.branch
                }
                if (better) claims[revision.hash] = walk to revision
            }
        }

        // A revision every candidate inherited still has to land somewhere.
        walks.sortedBy { it.depth }.forEach { walk ->
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

        // Oldest first, but a parent never sits right of its child: plain
        // timestamp order breaks the moment one clock ran ahead. Time only
        // breaks ties inside the topological order.
        val ordered = LoreLogOrder.topological(revisions.distinctBy { it.hash }).reversed()

        val earliest = ordered.groupBy { it.branch }
            .mapValues { (_, all) -> all.first().timestamp }

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
                synced = revision.synced,
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
