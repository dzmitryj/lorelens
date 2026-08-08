package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.changes.LoreContentRevision
import com.dzmitryj.lorelens.changes.LoreRevisionNumber
import com.dzmitryj.lorelens.lock.LockState
import com.dzmitryj.lorelens.lock.LoreLockService
import com.dzmitryj.lorelens.model.LoreRevisionId
import com.intellij.diff.DiffContentFactory
import com.intellij.diff.DiffManager
import com.intellij.diff.requests.SimpleDiffRequest
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vcs.LocalFilePath
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import java.nio.file.Files
import java.nio.file.Path

/**
 * Reading a file out of a branch this checkout is not on.
 *
 * Both routes exist because they answer different questions: the diff is for
 * picking hunks out of the other branch, the whole-file take is for when the
 * answer is "all of it".
 */
object LoreCrossBranch {

    private val log = logger<LoreCrossBranch>()

    /**
     * Opens the branch's copy against the working file. The right side is a real
     * document rather than a snapshot, which is what makes the diff viewer's
     * arrows able to pull individual hunks across -- no apply logic of our own.
     */
    fun compareWithWorkingCopy(
        project: Project,
        root: Path,
        relativePath: String,
        revision: LoreRevisionId,
        revisionNumber: Long,
        branch: String,
    ) {
        val file = workingFile(root, relativePath) ?: run {
            notFound(project, relativePath)
            return
        }

        ApplicationManager.getApplication().executeOnPooledThread {
            val bytes = runCatching { contentAt(root, relativePath, revision, revisionNumber) }
                .onFailure { log.warn("Cannot read $relativePath at ${revision.short}", it) }
                .getOrNull()

            ApplicationManager.getApplication().invokeLater {
                if (bytes == null) {
                    notFound(project, relativePath)
                    return@invokeLater
                }

                val factory = DiffContentFactory.getInstance()
                val request = SimpleDiffRequest(
                    LoreLensBundle.message("branch.compare.title", file.name, branch),
                    factory.create(project, String(bytes, Charsets.UTF_8), LocalFilePath(file.path, false)),
                    factory.createDocument(project, file) ?: factory.create(project, file),
                    LoreLensBundle.message("branch.compare.theirs", branch, revisionNumber),
                    LoreLensBundle.message("branch.compare.mine"),
                )
                DiffManager.getInstance().showDiff(project, request)
            }
        }
    }

    /**
     * Replaces the working file wholesale. Asks first, and refuses outright when
     * someone else holds the lock rather than letting the write fail on its own.
     */
    fun takeFromBranch(
        project: Project,
        root: Path,
        relativePath: String,
        revision: LoreRevisionId,
        revisionNumber: Long,
        branch: String,
    ) {
        val file = workingFile(root, relativePath)

        if (file != null && LoreLockService.getInstance(project).stateOf(file) == LockState.LOCKED_BY_OTHER) {
            val owner = LoreLockService.getInstance(project).lockOf(file)?.owner.orEmpty()
            Messages.showErrorDialog(
                project,
                LoreLensBundle.message("branch.take.locked", relativePath, owner),
                LoreLensBundle.message("branch.take.title"),
            )
            return
        }

        val confirmed = Messages.showYesNoDialog(
            project,
            LoreLensBundle.message("branch.take.message", relativePath, branch, revisionNumber),
            LoreLensBundle.message("branch.take.title"),
            Messages.getWarningIcon(),
        )
        if (confirmed != Messages.YES) return

        ApplicationManager.getApplication().executeOnPooledThread {
            val bytes = runCatching { contentAt(root, relativePath, revision, revisionNumber) }
                .onFailure { log.warn("Cannot read $relativePath at ${revision.short}", it) }
                .getOrNull()

            ApplicationManager.getApplication().invokeLater {
                if (bytes == null) {
                    notFound(project, relativePath)
                    return@invokeLater
                }

                val target = root.resolve(relativePath)
                runCatching {
                    Files.createDirectories(target.parent)
                    WriteCommandAction.writeCommandAction(project)
                        .withName(LoreLensBundle.message("branch.take.title"))
                        .run<Exception> {
                            Files.write(target, bytes)
                            VfsUtil.markDirtyAndRefresh(false, false, false, target.toFile())
                        }
                }.onFailure {
                    log.warn("Cannot write $relativePath", it)
                    Messages.showErrorDialog(
                        project,
                        it.message ?: LoreLensBundle.message("branch.take.failed"),
                        LoreLensBundle.message("branch.take.title"),
                    )
                }
            }
        }
    }

    private fun contentAt(
        root: Path,
        relativePath: String,
        revision: LoreRevisionId,
        revisionNumber: Long,
    ): ByteArray? {
        val filePath = LocalFilePath(root.resolve(relativePath).toString(), false)
        return LoreContentRevision(root, filePath, relativePath, LoreRevisionNumber(revision, revisionNumber))
            .contentAsBytes
    }

    private fun workingFile(root: Path, relativePath: String): VirtualFile? =
        VfsUtil.findFile(root.resolve(relativePath), true)

    private fun notFound(project: Project, relativePath: String) =
        Messages.showErrorDialog(
            project,
            LoreLensBundle.message("branch.compare.missing", relativePath),
            LoreLensBundle.message("branch.take.title"),
        )
}
