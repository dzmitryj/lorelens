package com.dzmitryj.lorelens.checkin

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.api.LoreWriteApi
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.FilePath
import com.intellij.openapi.vcs.VcsException
import com.intellij.openapi.vcs.changes.Change
import com.intellij.openapi.vcs.changes.VcsDirtyScopeManager
import com.intellij.openapi.vcs.rollback.RollbackEnvironment
import com.intellij.openapi.vcs.rollback.RollbackProgressListener
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile

/**
 * Safe to wire to Revert because lore_file_reset restores file content and
 * leaves the revision pointer alone, verified against a real repository. If that
 * semantic ever changes upstream, this destroys uncommitted work.
 */
class LoreRollbackEnvironment(private val project: Project) : RollbackEnvironment {

    override fun getRollbackOperationName(): String = LoreLensBundle.message("rollback.operation")

    override fun rollbackChanges(
        changes: List<Change>,
        exceptions: MutableList<VcsException>,
        listener: RollbackProgressListener,
    ) {
        reset(LorePaths.groupChanges(changes), exceptions, listener)

        val paths = changes.mapNotNull { it.afterRevision?.file ?: it.beforeRevision?.file }
        VfsUtil.markDirtyAndRefresh(
            false,
            false,
            false,
            *paths.mapNotNull { it.virtualFile }.toTypedArray(),
        )
        VcsDirtyScopeManager.getInstance(project).filePathsDirty(paths, null)
    }

    override fun rollbackMissingFileDeletion(
        files: List<FilePath>,
        exceptions: MutableList<in VcsException>,
        listener: RollbackProgressListener,
    ) {
        reset(LorePaths.groupFilePaths(files), exceptions, listener)
    }

    override fun rollbackModifiedWithoutCheckout(
        files: List<VirtualFile>,
        exceptions: MutableList<in VcsException>,
        listener: RollbackProgressListener,
    ) = Unit

    private fun reset(
        grouped: Map<java.nio.file.Path, List<String>>,
        exceptions: MutableList<in VcsException>,
        listener: RollbackProgressListener,
    ) {
        grouped.forEach { (root, paths) ->
            listener.determinate()
            try {
                LoreWriteApi.reset(root, paths)
            } catch (e: RuntimeException) {
                exceptions += VcsException(e)
            }
        }
    }
}
