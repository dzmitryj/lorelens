package com.dzmitryj.lorelens.lock

import com.dzmitryj.lorelens.api.LoreLockApi
import com.dzmitryj.lorelens.model.LoreLock
import com.dzmitryj.lorelens.repo.LoreRootFinder
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.FileStatusManager
import com.intellij.openapi.vfs.VirtualFile
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap

enum class LockState { UNLOCKED, LOCKED_BY_ME, LOCKED_BY_OTHER }

/**
 * Caches the lock table per root. Locks are server state that changes without
 * any local event, so this is refreshed explicitly rather than trusted
 * indefinitely.
 */
@Service(Service.Level.PROJECT)
class LoreLockService(private val project: Project) {

    private val log = logger<LoreLockService>()
    private val locksByRoot = ConcurrentHashMap<Path, Map<String, LoreLock>>()
    private val identityByRoot = ConcurrentHashMap<Path, String>()

    fun refresh(root: Path) {
        try {
            locksByRoot[root] = LoreLockApi.query(root).associateBy { it.path }
            LoreLockApi.currentUserId(root)?.let { identityByRoot[root] = it }
        } catch (e: RuntimeException) {
            log.warn("Cannot read locks in $root", e)
        }
        FileStatusManager.getInstance(project).fileStatusesChanged()
    }

    fun refreshAll() {
        LoreRootFinder.mappedRoots(project).forEach { refresh(it.toNioPath()) }
    }

    fun lockOf(file: VirtualFile): LoreLock? {
        val root = LoreRootFinder.findRoot(file) ?: return null
        val relative = LoreRootFinder.relativePath(root, file) ?: return null
        return locksByRoot[root.toNioPath()]?.get(relative)
    }

    fun stateOf(file: VirtualFile): LockState {
        val root = LoreRootFinder.findRoot(file) ?: return LockState.UNLOCKED
        val lock = lockOf(file) ?: return LockState.UNLOCKED
        val me = identityByRoot[root.toNioPath()]
        return if (me != null && lock.owner == me) LockState.LOCKED_BY_ME else LockState.LOCKED_BY_OTHER
    }

    fun heldByMe(): Int = locksByRoot.entries.sumOf { (root, locks) ->
        val me = identityByRoot[root] ?: return@sumOf 0
        locks.values.count { it.owner == me }
    }

    fun acquire(files: Collection<VirtualFile>) {
        files.groupBy { LoreRootFinder.findRoot(it) }.forEach { (root, grouped) ->
            if (root == null) return@forEach
            val paths = grouped.mapNotNull { LoreRootFinder.relativePath(root, it) }
            LoreLockApi.acquire(root.toNioPath(), paths)
            refresh(root.toNioPath())
        }
    }

    fun release(files: Collection<VirtualFile>) {
        files.groupBy { LoreRootFinder.findRoot(it) }.forEach { (root, grouped) ->
            if (root == null) return@forEach
            val paths = grouped.mapNotNull { LoreRootFinder.relativePath(root, it) }
            LoreLockApi.release(root.toNioPath(), paths)
            refresh(root.toNioPath())
        }
    }

    companion object {
        fun getInstance(project: Project): LoreLockService = project.service()
    }
}
