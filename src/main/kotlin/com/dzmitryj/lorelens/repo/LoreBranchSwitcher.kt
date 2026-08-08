package com.dzmitryj.lorelens.repo

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.api.LoreBranchApi
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vcs.changes.ChangeListManager
import com.intellij.openapi.vcs.changes.VcsDirtyScopeManager
import java.nio.file.Path

/**
 * Switching branches rewrites the working directory, so it asks before
 * discarding anything. Lore's `reset` flag is never passed silently.
 */
object LoreBranchSwitcher {

    private val log = logger<LoreBranchSwitcher>()

    /** Call on the EDT: it may prompt. */
    fun switch(project: Project, root: Path, branch: String) {
        val modified = ChangeListManager.getInstance(project).allChanges.size
        var reset = false

        if (modified > 0) {
            val answer = Messages.showYesNoCancelDialog(
                project,
                LoreLensBundle.message("branch.switch.dirty.message", modified, branch),
                LoreLensBundle.message("branch.switch.dirty.title"),
                LoreLensBundle.message("branch.switch.discard"),
                LoreLensBundle.message("branch.switch.keep"),
                LoreLensBundle.message("branch.switch.cancel"),
                Messages.getWarningIcon(),
            )
            when (answer) {
                Messages.YES -> reset = true
                Messages.NO -> reset = false
                else -> return
            }
        }

        run(project, root, branch, reset)
    }

    private fun run(project: Project, root: Path, branch: String, reset: Boolean) {
        object : Task.Backgroundable(project, LoreLensBundle.message("branch.switch.progress", branch), true) {
            private var failure: Throwable? = null

            override fun run(indicator: ProgressIndicator) {
                failure = runCatching { LoreBranchApi.switch(root, branch, reset = reset) }
                    .exceptionOrNull()
                    ?.also { log.warn("Cannot switch $root to $branch", it) }
            }

            override fun onFinished() {
                LoreRepositoryState.getInstance(project).invalidate(root)
                VcsDirtyScopeManager.getInstance(project).markEverythingDirty()

                failure?.let {
                    Messages.showErrorDialog(
                        project,
                        it.message ?: LoreLensBundle.message("branch.switch.failed", branch),
                        LoreLensBundle.message("branch.switch.dirty.title"),
                    )
                }
            }
        }.queue()
    }
}
