package com.dzmitryj.lorelens.merge

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.api.LoreBranchApi
import com.dzmitryj.lorelens.checkin.LorePaths
import com.dzmitryj.lorelens.ffi.LoreResult
import com.dzmitryj.lorelens.repo.LoreRepositoryState
import com.dzmitryj.lorelens.repo.LoreRootFinder
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vcs.FileStatus
import com.intellij.openapi.vcs.VcsDataKeys
import com.intellij.openapi.vcs.changes.Change
import com.intellij.openapi.vcs.changes.VcsDirtyScopeManager
import java.nio.file.Path

/**
 * Lore reports conflicts through the status flags the change provider already
 * maps, so they are visible in the changes view but cannot be acted on. These
 * are the four verbs that act on them.
 */
private val log = logger<LoreResolveAction>()

/** Marks the file resolved as it stands on disk. */
class LoreMarkResolvedAction : LoreResolveAction("merge.resolved") {
    override fun apply(root: Path, paths: List<String>) = LoreBranchApi.resolve(root, paths)
}

class LoreResolveMineAction : LoreResolveAction("merge.mine") {
    override fun apply(root: Path, paths: List<String>) = LoreBranchApi.resolveMine(root, paths)
}

class LoreResolveTheirsAction : LoreResolveAction("merge.theirs") {
    override fun apply(root: Path, paths: List<String>) = LoreBranchApi.resolveTheirs(root, paths)
}

class LoreUnresolveAction : LoreResolveAction("merge.unresolve") {
    override fun apply(root: Path, paths: List<String>) = LoreBranchApi.unresolve(root, paths)
}

abstract class LoreResolveAction(private val messageKey: String) : AnAction() {

    protected abstract fun apply(root: Path, paths: List<String>): LoreResult

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    override fun update(e: AnActionEvent) {
        e.presentation.text = LoreLensBundle.message(messageKey)
        e.presentation.isEnabledAndVisible = e.project != null && conflicted(e).isNotEmpty()
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val grouped = LorePaths.groupChanges(conflicted(e))
        if (grouped.isEmpty()) return

        ApplicationManager.getApplication().executeOnPooledThread {
            val failures = grouped.mapNotNull { (root, paths) ->
                runCatching { apply(root, paths) }
                    .exceptionOrNull()
                    ?.also { log.warn("Cannot ${LoreLensBundle.message(messageKey)} in $root", it) }
                    ?.let { root to it }
            }

            LoreRepositoryState.getInstance(project).invalidateAll()
            VcsDirtyScopeManager.getInstance(project).markEverythingDirty()

            if (failures.isNotEmpty()) {
                ApplicationManager.getApplication().invokeLater {
                    Messages.showErrorDialog(
                        project,
                        failures.joinToString("\n") { (root, error) -> "$root: ${error.message}" },
                        LoreLensBundle.message("merge.failed"),
                    )
                }
            }
        }
    }

    private fun conflicted(e: AnActionEvent): List<Change> =
        e.getData(VcsDataKeys.SELECTED_CHANGES)
            .orEmpty()
            .filter { it.fileStatus == FileStatus.MERGE || it.fileStatus == FileStatus.MERGED_WITH_CONFLICTS }
}

/** Abandons the merge outright, so it asks first. */
class LoreAbortMergeAction : AnAction() {

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = e.project?.let { hasRoot(it) } == true
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val roots = LoreRootFinder.mappedRoots(project)
        if (roots.isEmpty()) return

        val confirmed = Messages.showYesNoDialog(
            project,
            LoreLensBundle.message("merge.abort.message"),
            LoreLensBundle.message("merge.abort.title"),
            Messages.getWarningIcon(),
        )
        if (confirmed != Messages.YES) return

        ApplicationManager.getApplication().executeOnPooledThread {
            roots.forEach { root ->
                runCatching { LoreBranchApi.abortMerge(root.toNioPath()) }
                    .onFailure { log.warn("Cannot abort merge in ${root.path}", it) }
            }
            LoreRepositoryState.getInstance(project).invalidateAll()
            VcsDirtyScopeManager.getInstance(project).markEverythingDirty()
        }
    }

    private fun hasRoot(project: Project): Boolean =
        LoreRootFinder.mappedRoots(project).isNotEmpty()
}
