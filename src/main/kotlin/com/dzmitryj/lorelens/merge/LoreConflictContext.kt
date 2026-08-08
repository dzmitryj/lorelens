package com.dzmitryj.lorelens.merge

import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap

/**
 * Which conflict-producing operation is in flight per root.
 *
 * Lore surfaces merge and revert conflicts through the same status flags, so
 * the changes view cannot tell them apart -- but the resolve verbs are
 * separate APIs, and sending a revert conflict to the merge resolver fails.
 * Merge is the default: it is the overwhelmingly common case, and the only
 * wrong guess possible after an IDE restart mid-conflict resolves itself the
 * moment the failing verb reports.
 */
object LoreConflictContext {

    enum class Kind { MERGE, REVERT }

    private val active = ConcurrentHashMap<Path, Kind>()

    fun begin(root: Path, kind: Kind) {
        active[root] = kind
    }

    fun kindOf(root: Path): Kind = active[root] ?: Kind.MERGE

    fun end(root: Path) {
        active.remove(root)
    }
}
