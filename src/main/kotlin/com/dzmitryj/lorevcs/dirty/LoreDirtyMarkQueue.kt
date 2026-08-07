package com.dzmitryj.lorevcs.dirty

import com.dzmitryj.lorevcs.LoreBundle
import com.dzmitryj.lorevcs.api.LoreStatusApi
import com.dzmitryj.lorevcs.repo.LoreRootFinder
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.VcsDirtyScopeManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.Alarm
import com.intellij.vcsUtil.VcsUtil
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

/**
 * Coalesces IDE edits into batched dirty marks.
 *
 * Lore's status performs no filesystem walk; it trusts its dirty flags. The IDE
 * knows exactly which files it touched, so feeding that in keeps status both
 * correct and near-instant on a repository where a scan would take minutes.
 */
@Service(Service.Level.PROJECT)
class LoreDirtyMarkQueue(private val project: Project) : Disposable {

    private val log = logger<LoreDirtyMarkQueue>()
    private val alarm = Alarm(Alarm.ThreadToUse.POOLED_THREAD, this)

    /** Repository root path to the repository-relative paths awaiting a mark. */
    private val pending = ConcurrentHashMap<VirtualFile, MutableSet<String>>()
    private val consecutiveFailures = AtomicInteger()

    fun enqueue(files: Collection<VirtualFile>) {
        val settings = LoreDirtySettings.getInstance()
        if (!settings.markEditsDirty) return

        var queued = false
        files.forEach { file ->
            val root = LoreRootFinder.findRoot(file) ?: return@forEach
            val relative = LoreRootFinder.relativePath(root, file) ?: return@forEach
            pending.computeIfAbsent(root) { ConcurrentHashMap.newKeySet() } += relative
            queued = true
        }
        if (!queued) return

        alarm.cancelAllRequests()
        alarm.addRequest(::flush, settings.debounceMillis)
    }

    fun flush() {
        val settings = LoreDirtySettings.getInstance()
        if (!settings.markEditsDirty) {
            pending.clear()
            return
        }

        pending.keys.toList().forEach { root ->
            val paths = pending.remove(root)?.toList().orEmpty()
            if (paths.isEmpty()) return@forEach
            markRoot(root, paths, settings.maxBatchSize)
        }
    }

    private fun markRoot(root: VirtualFile, paths: List<String>, maxBatchSize: Int) {
        try {
            paths.chunked(maxBatchSize).forEach { batch ->
                LoreStatusApi.markDirty(root.toNioPath(), batch)
            }
            consecutiveFailures.set(0)

            val filePaths = paths.map { VcsUtil.getFilePath(root.toNioPath().resolve(it).toFile(), false) }
            VcsDirtyScopeManager.getInstance(project).filePathsDirty(filePaths, null)
        } catch (e: RuntimeException) {
            onFailure(root, e)
        }
    }

    /**
     * A dirty marker that fails quietly is worse than none: the user would trust
     * an incomplete status and commit a partial change set. So after repeated
     * failures this turns itself off and says so.
     */
    private fun onFailure(root: VirtualFile, e: RuntimeException) {
        log.warn("Failed to mark files dirty in ${root.path}", e)

        if (consecutiveFailures.incrementAndGet() < FAILURE_LIMIT) return

        LoreDirtySettings.getInstance().markEditsDirty = false
        pending.clear()

        NotificationGroupManager.getInstance()
            .getNotificationGroup("Lore")
            .createNotification(
                LoreBundle.message("dirty.disabled.title"),
                LoreBundle.message("dirty.disabled.message", root.presentableUrl),
                NotificationType.ERROR,
            )
            .notify(project)
    }

    override fun dispose() {
        pending.clear()
    }

    companion object {
        private const val FAILURE_LIMIT = 3

        fun getInstance(project: Project): LoreDirtyMarkQueue = project.service()
    }
}
