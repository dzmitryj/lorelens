package com.dzmitryj.lorelens.repo

import com.dzmitryj.lorelens.api.LoreStatusApi
import com.dzmitryj.lorelens.model.LoreRevisionStatus
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap

/**
 * Where each repository currently sits: branch, head revision, ahead and behind.
 *
 * Without this the line status tracker asks Lore for the head revision once per
 * file, on every editor open and every document settle, and each of those calls
 * also decodes the entire staged set only to discard it. The answer is a
 * per-repository constant that moves on commit, sync, push and branch switch,
 * so it is cached and invalidated at exactly those points.
 */
@Service(Service.Level.PROJECT)
class LoreRepositoryState(private val project: Project) {

    private val log = logger<LoreRepositoryState>()
    private val cache = ConcurrentHashMap<Path, LoreRevisionStatus>()

    /** Cached, loading it on first ask. Safe from any thread; never on the EDT for a cold root. */
    fun of(root: Path): LoreRevisionStatus? = cache[root] ?: load(root)

    /** The last known state, without ever calling Lore. */
    fun cached(root: Path): LoreRevisionStatus? = cache[root]

    fun invalidate(root: Path) {
        cache.remove(root)
    }

    fun invalidateAll() {
        cache.clear()
    }

    /** Re-reads every mapped root and returns whether anything moved. */
    fun refreshAll(): Boolean {
        val before = cache.toMap()
        cache.clear()
        return LoreRootFinder.mappedRoots(project)
            .map { it.toNioPath() }
            .any { load(it) != before[it] }
    }

    private fun load(root: Path): LoreRevisionStatus? =
        runCatching { LoreStatusApi.revisionStatus(root) }
            .onFailure { log.warn("Cannot read Lore revision status for $root", it) }
            .getOrNull()
            ?.also { cache[root] = it }

    companion object {
        fun getInstance(project: Project): LoreRepositoryState = project.service()
    }
}
