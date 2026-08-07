package com.dzmitryj.lorelens.checkin

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.api.LoreStatusApi
import com.dzmitryj.lorelens.api.LoreWriteApi
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.vcs.AbstractVcs
import com.intellij.openapi.vcs.FilePath
import com.intellij.openapi.vcs.VcsVFSListener
import com.intellij.openapi.vfs.VirtualFile
import kotlinx.coroutines.CoroutineScope

/**
 * Additions, deletions and renames. Lore tracks moves first class, so a rename
 * goes through stage move and renders as a rename rather than an add plus a
 * delete.
 */
class LoreVFSListener(vcs: AbstractVcs, scope: CoroutineScope) : VcsVFSListener(vcs, scope) {

    private val log = logger<LoreVFSListener>()

    override fun getAddTitle(): String = LoreLensBundle.message("vfs.add.title")

    override fun getSingleFileAddTitle(): String = LoreLensBundle.message("vfs.add.single.title")

    override fun getSingleFileAddPromptTemplate(): String = LoreLensBundle.message("vfs.add.prompt")

    override fun getDeleteTitle(): String = LoreLensBundle.message("vfs.delete.title")

    override fun getSingleFileDeleteTitle(): String = LoreLensBundle.message("vfs.delete.single.title")

    override fun getSingleFileDeletePromptTemplate(): String = LoreLensBundle.message("vfs.delete.prompt")

    override fun performAdding(
        addedFiles: Collection<VirtualFile>,
        copyFromMap: Map<VirtualFile, VirtualFile>,
    ) {
        mark(addedFiles.map { it.path }, "added")
    }

    override fun performDeletion(filesToDelete: List<FilePath>) {
        mark(filesToDelete.map { it.path }, "deleted")
    }

    override fun performMoveRename(movedFiles: List<MovedFileInfo>) {
        movedFiles.forEach { moved ->
            val root = LorePaths.rootOf(moved.myOldPath) ?: return@forEach
            val from = LorePaths.relativize(root, moved.myOldPath) ?: return@forEach
            val to = LorePaths.relativize(root, moved.myNewPath) ?: return@forEach

            runCatching { LoreWriteApi.stageMove(root, from, to) }
                .onFailure { log.warn("Cannot record move of $from to $to", it) }
        }
    }

    private fun mark(paths: List<String>, what: String) {
        LorePaths.group(paths).forEach { (root, relative) ->
            runCatching { LoreStatusApi.markDirty(root, relative) }
                .onFailure { log.warn("Cannot mark $what files in $root", it) }
        }
    }

    companion object {
        /** The base class does not install its listeners from the constructor. */
        fun create(vcs: AbstractVcs, scope: CoroutineScope): LoreVFSListener =
            LoreVFSListener(vcs, scope).also { it.installListeners() }
    }
}
