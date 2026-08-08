package com.dzmitryj.lorelens.ui

import java.util.PriorityQueue

/**
 * Display order for the union of every branch's history: newest first, but a
 * child always above its parents. Plain timestamp order is almost right and
 * then breaks the graph the one time a parent's clock ran ahead of its child,
 * because a lane waits downward for a hash that already went past.
 */
object LoreLogOrder {

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
