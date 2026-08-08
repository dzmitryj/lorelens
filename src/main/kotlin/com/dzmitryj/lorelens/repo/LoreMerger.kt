package com.dzmitryj.lorelens.repo

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.api.LoreBranchApi
import com.dzmitryj.lorelens.model.LoreMergePreview
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vcs.changes.VcsDirtyScopeManager
import java.nio.file.Path

/**
 * Merging another branch in.
 *
 * Lore reports what a merge would touch before it touches anything, so the
 * conflicts are shown up front rather than discovered halfway through. A merge
 * that will conflict is still allowed -- that is what the resolve actions are
 * for -- but it should not be a surprise.
 */
object LoreMerger {

    private val log = logger<LoreMerger>()

    /** Call on the EDT: it previews, prompts, then works in the background. */
    fun merge(project: Project, root: Path, source: String) {
        object : Task.Backgroundable(project, LoreLensBundle.message("merge.preview.progress", source), true) {
            private var preview: LoreMergePreview? = null
            private var failure: Throwable? = null

            override fun run(indicator: ProgressIndicator) {
                runCatching { LoreBranchApi.previewMerge(root, source) }
                    .onSuccess { preview = it }
                    .onFailure {
                        failure = it
                        log.warn("Cannot preview merge of $source into $root", it)
                    }
            }

            override fun onFinished() {
                val result = preview
                if (result == null) {
                    Messages.showErrorDialog(
                        project,
                        failure?.message ?: LoreLensBundle.message("merge.preview.failed", source),
                        LoreLensBundle.message("merge.title"),
                    )
                    return
                }
                confirm(project, root, source, result)
            }
        }.queue()
    }

    private fun confirm(project: Project, root: Path, source: String, preview: LoreMergePreview) {
        val message = when {
            preview.isClean ->
                LoreLensBundle.message("merge.clean", source, preview.changed.size)

            else -> LoreLensBundle.message(
                "merge.conflicts",
                source,
                preview.changed.size,
                preview.conflicted.size,
                preview.conflicted.take(10).joinToString("\n"),
            )
        }

        val confirmed = Messages.showYesNoDialog(
            project,
            message,
            LoreLensBundle.message("merge.title"),
            LoreLensBundle.message("merge.run"),
            LoreLensBundle.message("merge.cancel"),
            if (preview.isClean) Messages.getQuestionIcon() else Messages.getWarningIcon(),
        )
        if (confirmed != Messages.YES) return

        run(project, root, source)
    }

    private fun run(project: Project, root: Path, source: String) {
        object : Task.Backgroundable(project, LoreLensBundle.message("merge.progress", source), true) {
            private var failure: Throwable? = null

            override fun run(indicator: ProgressIndicator) {
                failure = runCatching {
                    LoreBranchApi.mergeInto(root, source, LoreLensBundle.message("merge.message", source))
                }.exceptionOrNull()?.also { log.warn("Cannot merge $source into $root", it) }
            }

            override fun onFinished() {
                LoreRepositoryState.getInstance(project).invalidate(root)
                VcsDirtyScopeManager.getInstance(project).markEverythingDirty()

                failure?.let {
                    ApplicationManager.getApplication().invokeLater {
                        Messages.showErrorDialog(
                            project,
                            it.message ?: LoreLensBundle.message("merge.failed", source),
                            LoreLensBundle.message("merge.title"),
                        )
                    }
                }
            }
        }.queue()
    }
}
