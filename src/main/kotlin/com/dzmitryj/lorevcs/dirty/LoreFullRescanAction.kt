package com.dzmitryj.lorevcs.dirty

import com.dzmitryj.lorevcs.LoreBundle
import com.dzmitryj.lorevcs.api.LoreStatusApi
import com.dzmitryj.lorevcs.repo.LoreRootFinder
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.VcsDirtyScopeManager

/**
 * Reconciles every path against the current revision. This is the O(repository)
 * path that auto-marking exists to avoid, so it stays explicit -- plus one run
 * per session to cover edits made while the IDE was closed.
 */
class LoreFullRescanAction : AnAction() {

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible =
            e.project?.let { LoreRootFinder.mappedRoots(it).isNotEmpty() } == true
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        rescan(project)
    }

    companion object {
        private val log = logger<LoreFullRescanAction>()

        fun rescan(project: Project) {
            val roots = LoreRootFinder.mappedRoots(project)
            if (roots.isEmpty()) return

            ProgressManager.getInstance().run(
                object : Task.Backgroundable(project, LoreBundle.message("rescan.progress"), true) {
                    override fun run(indicator: ProgressIndicator) {
                        roots.forEach { root ->
                            indicator.checkCanceled()
                            indicator.text2 = root.presentableUrl
                            try {
                                LoreStatusApi.status(root.toNioPath(), scan = true)
                            } catch (e: RuntimeException) {
                                log.warn("Full rescan failed for ${root.path}", e)
                            }
                        }
                        VcsDirtyScopeManager.getInstance(project).markEverythingDirty()
                    }
                },
            )
        }
    }
}
