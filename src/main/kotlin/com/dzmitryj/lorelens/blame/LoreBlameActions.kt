package com.dzmitryj.lorelens.blame

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.LoreVcs
import com.dzmitryj.lorelens.api.LoreDiffApi
import com.dzmitryj.lorelens.changes.LoreContentRevision
import com.dzmitryj.lorelens.changes.LoreRevisionNumber
import com.dzmitryj.lorelens.model.LoreHistoryRecord
import com.intellij.diff.DiffContentFactory
import com.intellij.diff.DiffManager
import com.intellij.diff.contents.DiffContent
import com.intellij.diff.requests.SimpleDiffRequest
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.AbstractVcsHelper
import com.intellij.openapi.vcs.FilePath
import com.intellij.openapi.vcs.LocalFilePath
import java.awt.datatransfer.StringSelection
import java.nio.file.Path

/** Right-click menu on the blame hint. */
fun loreBlameActions(
    project: Project,
    root: Path,
    relativePath: String,
    record: LoreHistoryRecord,
): DefaultActionGroup = DefaultActionGroup(
    ShowRevisionDiffAction(project, root, relativePath, record),
    CopyRevisionAction(record),
    ShowFileHistoryAction(project, root, relativePath),
)

private fun filePathOf(root: Path, relativePath: String): FilePath =
    LocalFilePath(root.resolve(relativePath).toString(), false)

private fun label(record: LoreHistoryRecord): String =
    "r${record.number} (${record.revision.short})"

/**
 * The diff of the one file, at the revision the line is attributed to, against
 * whatever that file looked like immediately before -- which is the question
 * the hint provokes.
 */
private class ShowRevisionDiffAction(
    private val project: Project,
    private val root: Path,
    private val relativePath: String,
    private val record: LoreHistoryRecord,
) : AnAction(LoreLensBundle.message("blame.action.diff"), null, AllIcons.Actions.Diff) {

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    override fun actionPerformed(e: AnActionEvent) {
        ApplicationManager.getApplication().executeOnPooledThread {
            val request = runCatching { build() }.getOrNull() ?: return@executeOnPooledThread
            ApplicationManager.getApplication().invokeLater {
                DiffManager.getInstance().showDiff(project, request)
            }
        }
    }

    private fun build(): SimpleDiffRequest? {
        val history = LoreDiffApi.fileHistory(root, relativePath, LoreBlameService.MAX_REVISIONS)
        val index = history.indexOfFirst { it.revision.hex == record.revision.hex }
        if (index < 0) return null
        val previous = history.getOrNull(index + 1)

        val filePath = filePathOf(root, relativePath)
        val after = content(filePath, record) ?: return null
        // An empty document rather than EmptyContent: the one-side viewer that
        // EmptyContent forces has no side-by-side/unified switch at all.
        val before = previous?.let { content(filePath, it) }
            ?: DiffContentFactory.getInstance().create(project, "", filePath)

        return SimpleDiffRequest(
            "${filePath.name} (${previous?.let(::label) ?: NONE} → ${label(record)})",
            before,
            after,
            previous?.let(::label) ?: NONE,
            label(record),
        ).also { it.putUserData(com.intellij.diff.util.DiffUserDataKeys.PLACE, com.intellij.diff.util.DiffPlaces.CHANGES_VIEW) }
    }

    private fun content(filePath: FilePath, at: LoreHistoryRecord): DiffContent? {
        val revision = LoreRevisionNumber(at.revision, at.number)
        // By address: the record's path is unreadable once the file moved.
        val bytes = LoreContentRevision(root, filePath, at.path, revision, address = at.address).contentAsBytes
            ?: return null
        return DiffContentFactory.getInstance()
            .create(project, String(bytes, Charsets.UTF_8), filePath)
    }

    private companion object {
        const val NONE = "—"
    }
}

private class CopyRevisionAction(private val record: LoreHistoryRecord) :
    AnAction(LoreLensBundle.message("blame.action.copy"), null, AllIcons.Actions.Copy) {

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    override fun actionPerformed(e: AnActionEvent) =
        CopyPasteManager.getInstance().setContents(StringSelection(record.revision.hex))
}

private class ShowFileHistoryAction(
    private val project: Project,
    private val root: Path,
    private val relativePath: String,
) : AnAction(LoreLensBundle.message("blame.action.history"), null, AllIcons.Vcs.History) {

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

    override fun actionPerformed(e: AnActionEvent) {
        val vcs = LoreVcs.of(project) ?: return
        AbstractVcsHelper.getInstance(project)
            .showFileHistory(vcs.vcsHistoryProvider, filePathOf(root, relativePath), vcs)
    }
}
