package com.dzmitryj.lorevcs.changes

import com.dzmitryj.lorevcs.LoreVcs
import com.dzmitryj.lorevcs.api.LoreStatusApi
import com.dzmitryj.lorevcs.model.LoreFileAction
import com.dzmitryj.lorevcs.model.LoreFileStatus
import com.dzmitryj.lorevcs.model.LoreNodeType
import com.dzmitryj.lorevcs.repo.LoreRootFinder
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.FilePath
import com.intellij.openapi.vcs.FileStatus
import com.intellij.openapi.vcs.VcsException
import com.intellij.openapi.vcs.changes.Change
import com.intellij.openapi.vcs.changes.ChangeListManagerGate
import com.intellij.openapi.vcs.changes.ChangeProvider
import com.intellij.openapi.vcs.changes.ChangelistBuilder
import com.intellij.openapi.vcs.changes.CurrentContentRevision
import com.intellij.openapi.vcs.changes.VcsDirtyScope
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.vcsUtil.VcsUtil
import java.nio.file.Path

/**
 * Bridges two things that share a name and are one layer apart. IntelliJ's dirty
 * scope is "paths the IDE suspects changed"; Lore's dirty set is "paths Lore has
 * been told changed, and will therefore report without walking the filesystem".
 *
 * Pushing the former into the latter is what makes refresh O(files the user
 * touched) rather than O(repository), which on an asset repository is the
 * difference between instant and minutes.
 */
class LoreChangeProvider(private val project: Project) : ChangeProvider {

    private val log = logger<LoreChangeProvider>()

    override fun isModifiedDocumentTrackingRequired(): Boolean = true

    override fun getChanges(
        dirtyScope: VcsDirtyScope,
        builder: ChangelistBuilder,
        progress: ProgressIndicator,
        addGate: ChangeListManagerGate,
    ) {
        val roots = LoreRootFinder.mappedRoots(project)
        if (roots.isEmpty()) return

        roots.forEach { root ->
            progress.checkCanceled()
            collect(root, dirtyScope, builder)
        }
    }

    private fun collect(root: VirtualFile, dirtyScope: VcsDirtyScope, builder: ChangelistBuilder) {
        val rootPath = root.toNioPath()
        val scoped = scopedPaths(root, dirtyScope)

        try {
            // A scope covering the whole root, or the first refresh after
            // opening, has nothing useful to narrow by, so reconcile instead.
            val full = scoped == null
            if (!full && scoped!!.isEmpty()) return

            if (!full) {
                LoreStatusApi.markDirty(rootPath, scoped!!)
            }

            val status = LoreStatusApi.status(
                root = rootPath,
                paths = scoped.orEmpty(),
                scan = full,
                checkDirty = !full,
            )

            val revision = status.revision?.let {
                LoreRevisionNumber(it.revision, it.revisionNumber)
            } ?: return

            status.files
                .filter { it.nodeType != LoreNodeType.DIRECTORY }
                .forEach { file -> report(builder, rootPath, revision, file) }
        } catch (e: VcsException) {
            log.warn("Lore status failed for $rootPath", e)
        } catch (e: RuntimeException) {
            log.warn("Lore status failed for $rootPath", e)
        }
    }

    /** Null means "no useful narrowing, reconcile the whole root". */
    private fun scopedPaths(root: VirtualFile, dirtyScope: VcsDirtyScope): List<String>? {
        val recursive = dirtyScope.recursivelyDirtyDirectories
        if (recursive.any { it.virtualFile == root || it.path == root.path }) return null

        val paths = mutableSetOf<String>()
        dirtyScope.dirtyFiles.forEach { path -> relative(root, path)?.let(paths::add) }
        recursive.forEach { path -> relative(root, path)?.let(paths::add) }
        return paths.toList()
    }

    private fun relative(root: VirtualFile, path: FilePath): String? {
        val absolute = path.path
        val rootPath = root.path
        if (!absolute.startsWith(rootPath)) return null
        return absolute.removePrefix(rootPath).trimStart('/').ifEmpty { null }
    }

    private fun report(
        builder: ChangelistBuilder,
        root: Path,
        revision: LoreRevisionNumber,
        file: LoreFileStatus,
    ) {
        val filePath = VcsUtil.getFilePath(root.resolve(file.path).toFile(), false)

        when (file.action) {
            LoreFileAction.ADD -> builder.processChange(
                Change(null, CurrentContentRevision(filePath), FileStatus.ADDED),
                LoreVcs.KEY,
            )

            LoreFileAction.DELETE -> builder.processChange(
                Change(base(root, filePath, file.path, revision), null, FileStatus.DELETED),
                LoreVcs.KEY,
            )

            // Lore tracks moves and copies first class, so these must render as
            // renames rather than an add plus a delete.
            LoreFileAction.MOVE, LoreFileAction.COPY -> {
                val from = file.fromPath
                builder.processChange(
                    Change(
                        from?.let { base(root, VcsUtil.getFilePath(root.resolve(it).toFile(), false), it, revision) },
                        CurrentContentRevision(filePath),
                        FileStatus.MODIFIED,
                    ),
                    LoreVcs.KEY,
                )
            }

            LoreFileAction.KEEP -> if (file.dirty) {
                builder.processChange(
                    Change(
                        base(root, filePath, file.path, revision),
                        CurrentContentRevision(filePath),
                        status(file),
                    ),
                    LoreVcs.KEY,
                )
            }

            LoreFileAction.UNKNOWN -> log.warn("Unknown Lore file action for ${file.path}")
        }
    }

    private fun base(root: Path, filePath: FilePath, relativePath: String, revision: LoreRevisionNumber) =
        LoreContentRevision(root, filePath, relativePath, revision)

    private fun status(file: LoreFileStatus): FileStatus = when {
        file.conflictUnresolved -> FileStatus.MERGED_WITH_CONFLICTS
        file.conflicted -> FileStatus.MERGE
        else -> FileStatus.MODIFIED
    }
}
