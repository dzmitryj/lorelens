package com.dzmitryj.lorelens.history

import com.dzmitryj.lorelens.api.LoreDiffApi
import com.dzmitryj.lorelens.changes.LoreContentRevision
import com.dzmitryj.lorelens.changes.LoreRevisionNumber
import com.dzmitryj.lorelens.model.LoreFileAction
import com.dzmitryj.lorelens.model.LoreHistoryRecord
import com.dzmitryj.lorelens.repo.LoreRootFinder
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.FilePath
import com.intellij.openapi.vcs.VcsException
import com.intellij.openapi.vcs.history.VcsAbstractHistorySession
import com.intellij.openapi.vcs.history.VcsAppendableHistorySessionPartner
import com.intellij.openapi.vcs.history.VcsDependentHistoryComponents
import com.intellij.openapi.vcs.history.VcsFileRevision
import com.intellij.openapi.vcs.history.VcsHistoryProvider
import com.intellij.openapi.vcs.history.VcsHistorySession
import com.intellij.openapi.vcs.history.VcsRevisionNumber
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.vcsUtil.VcsUtil
import java.nio.file.Path
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Date
import javax.swing.JComponent

/**
 * Puts Lore history into the IDE's own Show History UI.
 *
 * getAuthor returns null throughout: Lore records no author on a revision. The
 * complete metadata key set is message, branch and timestamp, and no history or
 * info struct carries an identity. Returning a placeholder would be a lie in the
 * one column users would trust most.
 */
class LoreHistoryProvider(private val project: Project) : VcsHistoryProvider {

    override fun getUICustomization(session: VcsHistorySession, forShortcutRegistration: JComponent?) =
        VcsDependentHistoryComponents.createOnlyColumns(emptyArray())

    override fun getAdditionalActions(refresher: Runnable): Array<AnAction> = emptyArray()

    override fun isDateOmittable(): Boolean = false

    override fun getHelpId(): String? = null

    override fun getHistoryDiffHandler() = null

    override fun supportsHistoryForDirectories(): Boolean = false

    override fun canShowHistoryFor(file: VirtualFile): Boolean =
        !file.isDirectory && LoreRootFinder.findRoot(file) != null

    override fun createSessionFor(filePath: FilePath): VcsHistorySession? {
        val revisions = load(filePath) ?: return null
        return LoreHistorySession(revisions)
    }

    override fun reportAppendableHistory(
        path: FilePath,
        partner: VcsAppendableHistorySessionPartner,
    ) {
        val revisions = load(path) ?: return
        partner.reportCreatedEmptySession(LoreHistorySession(emptyList()))
        revisions.forEach(partner::acceptRevision)
    }

    private fun load(filePath: FilePath): List<VcsFileRevision>? {
        val anchor = filePath.virtualFile ?: filePath.parentPath?.virtualFile ?: return null
        val root = LoreRootFinder.findRoot(anchor)?.toNioPath() ?: return null
        val relative = relative(root, filePath.path) ?: return null

        return try {
            LoreDiffApi.fileHistory(root, relative).map { LoreFileRevision(root, filePath, it) }
        } catch (e: RuntimeException) {
            throw VcsException("Cannot read Lore history for $relative", e)
        }
    }

    private fun relative(root: Path, path: String): String? =
        runCatching { root.relativize(Path.of(path)).toString().replace('\\', '/') }
            .getOrNull()
            ?.takeIf { it.isNotEmpty() && !it.startsWith("..") }

    private class LoreHistorySession(revisions: List<VcsFileRevision>) :
        VcsAbstractHistorySession(revisions) {

        override fun calcCurrentRevisionNumber(): VcsRevisionNumber? =
            revisionList.firstOrNull()?.revisionNumber

        override fun copy(): VcsHistorySession = LoreHistorySession(revisionList)

        override fun isContentAvailable(revision: VcsFileRevision): Boolean =
            (revision as? LoreFileRevision)?.record?.action != LoreFileAction.DELETE
    }

    private class LoreFileRevision(
        private val root: Path,
        private val filePath: FilePath,
        val record: LoreHistoryRecord,
    ) : VcsFileRevision {

        private val number = LoreRevisionNumber(record.revision, record.number)

        override fun getRevisionNumber(): VcsRevisionNumber = number

        override fun getRevisionDate(): Date? = record.timestamp?.let(::parseDate)

        /** Lore records no author. See the class doc. */
        override fun getAuthor(): String? = null

        override fun getCommitMessage(): String? = record.message

        override fun getBranchName(): String? = record.metadata["branch"]

        override fun getChangedRepositoryPath() = null

        override fun loadContent(): ByteArray? =
            LoreContentRevision(root, filePath, record.path, number).contentAsBytes

        // Deprecated but still abstract on VcsFileContent, and no platform base
        // class implements it, so it has to be overridden.
        @Deprecated("Use loadContent", ReplaceWith("loadContent()"))
        override fun getContent(): ByteArray? = loadContent()

        private fun parseDate(text: String): Date? =
            runCatching { Date.from(Instant.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse(text))) }
                .getOrElse {
                    if (it is DateTimeParseException) null else throw it
                }
    }
}
