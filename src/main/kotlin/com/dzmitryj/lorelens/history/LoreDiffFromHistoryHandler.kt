package com.dzmitryj.lorelens.history

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.FilePath
import com.intellij.openapi.vcs.history.DiffFromHistoryHandler
import com.intellij.openapi.vcs.history.VcsFileRevision
import com.intellij.openapi.vcs.history.VcsHistoryUtil

/**
 * Implemented rather than extending StandardDiffFromHistoryHandler, which is
 * marked internal. Both cases are the same for Lore: a linear chain means the
 * single-revision case is just that revision against its parent, which the
 * history table has already resolved for us.
 */
class LoreDiffFromHistoryHandler : DiffFromHistoryHandler {

    override fun showDiffForOne(
        e: AnActionEvent,
        project: Project,
        filePath: FilePath,
        previous: VcsFileRevision,
        revision: VcsFileRevision,
    ) = VcsHistoryUtil.showDifferencesInBackground(project, filePath, previous, revision)

    override fun showDiffForTwo(
        project: Project,
        filePath: FilePath,
        older: VcsFileRevision,
        newer: VcsFileRevision,
    ) = VcsHistoryUtil.showDifferencesInBackground(project, filePath, older, newer)
}
