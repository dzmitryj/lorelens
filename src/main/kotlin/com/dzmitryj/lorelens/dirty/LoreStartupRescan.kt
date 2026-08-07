package com.dzmitryj.lorelens.dirty

import com.dzmitryj.lorelens.api.LoreStatusApi
import com.dzmitryj.lorelens.repo.LoreRootFinder
import com.dzmitryj.lorelens.repo.loreInstanceId
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.openapi.vcs.changes.VcsDirtyScopeManager
import com.dzmitryj.lorelens.LoreLensBundle

/**
 * Reconciles a repository the first time it is seen, which covers changes made
 * before the plugin ever ran. Later opens rely on auto-dirty instead: a full
 * scan is O(repository), and on an asset repository that is minutes of work to
 * repeat on every open.
 *
 * Use Full Rescan for changes made while the IDE was closed.
 */
class LoreStartupRescan : ProjectActivity {

    private val log = logger<LoreStartupRescan>()

    override suspend fun execute(project: Project) {
        val roots = LoreRootFinder.mappedRoots(project)
        if (roots.isEmpty()) return

        val settings = LoreDirtySettings.getInstance()
        val pending = roots.mapNotNull { root ->
            val path = root.toNioPath()
            val instanceId = loreInstanceId(path) ?: return@mapNotNull null
            if (settings.needsInitialScan(instanceId)) path to instanceId else null
        }
        if (pending.isEmpty()) return

        ProgressManager.getInstance().run(
            object : Task.Backgroundable(project, LoreLensBundle.message("rescan.progress"), true) {
                override fun run(indicator: ProgressIndicator) {
                    pending.forEach { (path, instanceId) ->
                        indicator.checkCanceled()
                        indicator.text2 = path.toString()
                        try {
                            LoreStatusApi.status(path, scan = true)
                            // Only after a completed scan, so a cancelled or
                            // failed one is retried next time.
                            settings.markScanned(instanceId)
                        } catch (e: RuntimeException) {
                            log.warn("Initial scan failed for $path", e)
                        }
                    }
                    VcsDirtyScopeManager.getInstance(project).markEverythingDirty()
                }
            },
        )
    }
}
