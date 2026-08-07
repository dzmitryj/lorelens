package com.dzmitryj.lorelens.model

/**
 * Lore's revision chain is linear -- there is no merge graph -- so in a history
 * ordered newest first a revision's parent is simply the entry after it, and the
 * oldest revision has none.
 */
object LoreRevisionChain {

    /** Index of the parent of the revision at [index], in a newest-first list. */
    fun parentIndex(index: Int): Int = if (index < 0) -1 else index + 1

    fun <T> parentOf(newestFirst: List<T>, index: Int): T? =
        parentIndex(index).takeIf { it > 0 }?.let(newestFirst::getOrNull)
}
