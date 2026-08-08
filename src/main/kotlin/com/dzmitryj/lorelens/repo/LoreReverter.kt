package com.dzmitryj.lorelens.repo

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.api.LoreRevertApi
import com.dzmitryj.lorelens.merge.LoreConflictContext
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vcs.changes.VcsDirtyScopeManager
import java.nio.file.Path

/**
 * Reverting a revision: its inverse applied to the working tree, committed
 * when nothing conflicts. Conflicts land in the same resolution UI as a
 * merge's; [LoreConflictContext] is what routes the resolve verbs to the
 * revert API while one is in progress.
 */
object LoreReverter {

    private val log = logger<LoreReverter>()

    /** Call on the EDT: it prompts, then works in the background. */
    fun revert(project: Project, root: Path, revision: String, number: Long) {
        val confirmed = Messages.showYesNoDialog(
            project,
            LoreLensBundle.message("revert.confirm", number),
            LoreLensBundle.message("revert.title"),
            LoreLensBundle.message("revert.run"),
            LoreLensBundle.message("merge.cancel"),
            Messages.getQuestionIcon(),
        )
        if (confirmed != Messages.YES) return

        object : Task.Backgroundable(project, LoreLensBundle.message("revert.progress", number), true) {
            private var failure: Throwable? = null

            override fun run(indicator: ProgressIndicator) {
                LoreConflictContext.begin(root, LoreConflictContext.Kind.REVERT)
                failure = runCatching {
                    LoreRevertApi.revert(root, revision, LoreLensBundle.message("revert.message", number))
                }.exceptionOrNull()?.also { log.warn("Cannot revert r$number in $root", it) }
            }

            override fun onFinished() {
                LoreRepositoryState.getInstance(project).invalidate(root)
                VcsDirtyScopeManager.getInstance(project).markEverythingDirty()

                failure?.let {
                    ApplicationManager.getApplication().invokeLater {
                        Messages.showErrorDialog(
                            project,
                            it.message ?: LoreLensBundle.message("revert.failed", number),
                            LoreLensBundle.message("revert.title"),
                        )
                    }
                }
            }
        }.queue()
    }
}
