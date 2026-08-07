package com.dzmitryj.lorevcs.changes

import com.dzmitryj.lorevcs.api.LoreStatusApi
import com.dzmitryj.lorevcs.repo.LoreRootFinder
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.FilePath
import com.intellij.openapi.vcs.changes.ContentRevision
import com.intellij.openapi.vcs.diff.DiffProvider
import com.intellij.openapi.vcs.diff.ItemLatestState
import com.intellij.openapi.vcs.history.VcsRevisionNumber
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.vcsUtil.VcsUtil

/**
 * Supplies the base content the line status tracker draws gutter markers from.
 */
class LoreDiffProvider(private val project: Project) : DiffProvider {

    private val log = logger<LoreDiffProvider>()

    override fun getCurrentRevision(file: VirtualFile): VcsRevisionNumber? {
        val root = LoreRootFinder.findRoot(file) ?: return null
        return try {
            LoreStatusApi.status(root.toNioPath()).revision
                ?.let { LoreRevisionNumber(it.revision, it.revisionNumber) }
        } catch (e: RuntimeException) {
            log.warn("Cannot read current revision for ${file.path}", e)
            null
        }
    }

    override fun getLastRevision(filePath: FilePath): ItemLatestState? {
        val file = filePath.virtualFile ?: return null
        val revision = getCurrentRevision(file) ?: return null
        return ItemLatestState(revision, true, false)
    }

    override fun getLastRevision(file: VirtualFile): ItemLatestState? =
        getLastRevision(VcsUtil.getFilePath(file))

    override fun createFileContent(revisionNumber: VcsRevisionNumber, file: VirtualFile): ContentRevision? {
        if (revisionNumber !is LoreRevisionNumber) return null

        val root = LoreRootFinder.findRoot(file) ?: return null
        val relative = LoreRootFinder.relativePath(root, file) ?: return null

        return LoreContentRevision(root.toNioPath(), VcsUtil.getFilePath(file), relative, revisionNumber)
    }

    override fun getLatestCommittedRevision(vcsRoot: VirtualFile): VcsRevisionNumber? =
        getCurrentRevision(vcsRoot)
}
