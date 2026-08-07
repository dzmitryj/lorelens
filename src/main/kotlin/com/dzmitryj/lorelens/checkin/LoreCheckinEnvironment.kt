package com.dzmitryj.lorelens.checkin

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.api.LoreStatusApi
import com.dzmitryj.lorelens.api.LoreWriteApi
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.vcs.CheckinProjectPanel
import com.intellij.openapi.vcs.FilePath
import com.intellij.openapi.vcs.VcsException
import com.intellij.openapi.vcs.changes.Change
import com.intellij.openapi.vcs.changes.CommitContext
import com.intellij.openapi.vcs.changes.VcsDirtyScopeManager
import com.intellij.openapi.vcs.checkin.CheckinEnvironment
import com.intellij.openapi.vcs.ui.RefreshableOnComponent
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.ui.JBUI
import javax.swing.JCheckBox
import javax.swing.JComponent

class LoreCheckinEnvironment(private val project: Project) : CheckinEnvironment {

    override fun getCheckinOperationName(): String = LoreLensBundle.message("checkin.operation")

    override fun getHelpId(): String? = null

    override fun isRefreshAfterCommitNeeded(): Boolean = true

    override fun createCommitOptions(
        commitPanel: CheckinProjectPanel,
        commitContext: CommitContext,
    ): RefreshableOnComponent = PushAfterCommitOption(commitContext)

    override fun commit(
        changes: List<Change>,
        commitMessage: String,
        commitContext: CommitContext,
        feedback: MutableSet<in String>,
    ): List<VcsException> {
        val exceptions = mutableListOf<VcsException>()
        val push = commitContext.getUserData(PUSH_AFTER_COMMIT) ?: true

        LorePaths.groupChanges(changes).forEach { (root, paths) ->
            try {
                // Never stage the containing directory: a directory path stages
                // only files already marked dirty, silently omitting anything
                // the tracker missed. Always enumerate.
                LoreWriteApi.stage(root, paths)
                LoreWriteApi.commit(root, commitMessage)

                if (push) {
                    LoreWriteApi.push(root)
                    feedback += LoreLensBundle.message("checkin.pushed", root.toString())
                }
            } catch (e: RuntimeException) {
                exceptions += VcsException(e)
            }
        }

        VcsDirtyScopeManager.getInstance(project).markEverythingDirty()
        return exceptions
    }

    override fun scheduleMissingFileForDeletion(files: List<FilePath>): List<VcsException> =
        markDirty(LorePaths.groupFilePaths(files))

    override fun scheduleUnversionedFilesForAddition(files: List<VirtualFile>): List<VcsException> =
        markDirty(LorePaths.groupVirtualFiles(files))

    private fun markDirty(grouped: Map<java.nio.file.Path, List<String>>): List<VcsException> {
        val exceptions = mutableListOf<VcsException>()
        grouped.forEach { (root, paths) ->
            try {
                LoreStatusApi.markDirty(root, paths)
            } catch (e: RuntimeException) {
                exceptions += VcsException(e)
            }
        }
        return exceptions
    }

    /**
     * Defaulted on: Lore is centralized, so an unpushed commit is a
     * half-finished action rather than a normal resting state.
     */
    private class PushAfterCommitOption(private val context: CommitContext) : RefreshableOnComponent {

        private val checkBox = JCheckBox(LoreLensBundle.message("checkin.push.after.commit"), true)

        override fun getComponent(): JComponent = JBUI.Panels.simplePanel(checkBox)

        override fun saveState() {
            context.putUserData(PUSH_AFTER_COMMIT, checkBox.isSelected)
        }

        override fun restoreState() {
            checkBox.isSelected = context.getUserData(PUSH_AFTER_COMMIT) ?: true
        }
    }

    private companion object {
        val PUSH_AFTER_COMMIT = Key.create<Boolean>("Lore.PushAfterCommit")
    }
}
