package com.dzmitryj.lorelens.model

/**
 * The branch hierarchy: which branch each one was cut from.
 *
 * Lore records this on every branch as a stack of branch points, each naming the
 * branch it came from, so `main -> dev-main -> dev-<developer>` is read rather
 * than guessed at from names or from which branch happens to be older.
 */
object LoreBranchTree {

    data class Node(val branch: LoreBranch, val children: List<Node>) {
        val name: String get() = branch.name
    }

    /**
     * Roots are branches whose parent is not in [branches] -- normally just the
     * default branch, but also any branch whose parent has been archived away.
     */
    fun build(branches: List<LoreBranch>): List<Node> {
        val unique = branches.distinctBy { it.name }
        val byId = unique.associateBy { it.id.hex }

        val childrenOf = unique.groupBy { branch ->
            branch.parentBranch
                ?.hex
                ?.takeIf { it != branch.id.hex && byId.containsKey(it) }
        }

        val roots = childrenOf[null].orEmpty()
        return roots.sortedBy { it.name }.map { node(it, childrenOf, mutableSetOf()) }
    }

    /** Flattened depth-first, which is the order the lanes should read in. */
    fun order(branches: List<LoreBranch>): List<String> {
        val flat = mutableListOf<String>()

        fun walk(node: Node) {
            flat += node.name
            node.children.forEach(::walk)
        }
        build(branches).forEach(::walk)

        // Anything a cycle kept out still has to appear somewhere.
        val missing = branches.map { it.name }.distinct() - flat.toSet()
        return flat + missing.sorted()
    }

    private fun node(
        branch: LoreBranch,
        childrenOf: Map<String?, List<LoreBranch>>,
        seen: MutableSet<String>,
    ): Node {
        // Lore should not produce a cycle, but a branch pointing at itself
        // through a chain would hang this, and a hung UI is worse than a
        // flattened tree.
        if (!seen.add(branch.id.hex)) return Node(branch, emptyList())

        val children = childrenOf[branch.id.hex]
            .orEmpty()
            .sortedBy { it.name }
            .map { node(it, childrenOf, seen) }

        return Node(branch, children)
    }
}
