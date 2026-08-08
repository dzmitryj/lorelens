package com.dzmitryj.lorelens.ui

import java.util.PriorityQueue

/**
 * Display order for the union of every branch's history: newest first, but a
 * child always above its parents. Plain timestamp order is almost right and
 * then breaks the graph the one time a parent's clock ran ahead of its child,
 * because a lane waits downward for a hash that already went past.
 */
object LoreLogOrder {

    /**
     * Everything the tip reaches through parents: the branch's own history plus
     * what was merged into it, and nothing that was not. This is what "the log
     * of a branch" means; the union of every branch would also show the other
     * branches' unmerged work, which is their business.
     */
    fun reachable(tip: String, parents: Map<String, List<String>>): Set<String> {
        val seen = HashSet<String>()
        val queue = ArrayDeque<String>()
        if (tip in parents) {
            seen += tip
            queue += tip
        }
        while (queue.isNotEmpty()) {
            parents.getValue(queue.removeFirst()).forEach { parent ->
                if (parent in parents && seen.add(parent)) queue += parent
            }
        }
        return seen
    }

    fun topological(inputs: List<LoreBranchGraphLayout.Input>): List<LoreBranchGraphLayout.Input> {
        val byHash = inputs.associateBy { it.hash }
        val pendingChildren = HashMap<String, Int>()
        inputs.forEach { input ->
            input.parents.forEach { parent ->
                if (parent in byHash) pendingChildren.merge(parent, 1, Int::plus)
            }
        }

        val newest = compareByDescending<LoreBranchGraphLayout.Input> { it.timestamp }
            .thenByDescending { it.number }
            .thenBy { it.hash }
        val ready = PriorityQueue(newest)
        inputs.filterTo(ready) { pendingChildren.getOrDefault(it.hash, 0) == 0 }

        val out = ArrayList<LoreBranchGraphLayout.Input>(inputs.size)
        val seen = HashSet<String>()
        while (ready.isNotEmpty()) {
            val next = ready.poll()
            if (!seen.add(next.hash)) continue
            out += next
            next.parents.forEach { parent ->
                val node = byHash[parent] ?: return@forEach
                if (pendingChildren.merge(parent, -1, Int::plus) == 0) ready += node
            }
        }

        // A cycle cannot happen in a revision DAG; if the data is broken anyway,
        // showing the leftovers beats losing them.
        if (out.size < inputs.size) {
            out += inputs.filter { it.hash !in seen }.sortedWith(newest)
        }
        return out
    }
}
