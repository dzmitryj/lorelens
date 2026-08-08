package com.dzmitryj.lorelens.log

import com.dzmitryj.lorelens.LoreVcs
import com.dzmitryj.lorelens.api.LoreBranchApi
import com.dzmitryj.lorelens.api.LoreDiffApi
import com.dzmitryj.lorelens.api.LoreHistoryApi
import com.dzmitryj.lorelens.api.LoreHistoryEntry
import com.dzmitryj.lorelens.api.LoreLockApi
import com.dzmitryj.lorelens.changes.LoreContentRevision
import com.dzmitryj.lorelens.changes.LoreRevisionNumber
import com.dzmitryj.lorelens.model.LoreBranch
import com.dzmitryj.lorelens.model.LoreBranchLocation
import com.dzmitryj.lorelens.model.LoreFileAction
import com.dzmitryj.lorelens.repo.LoreRepositoryState
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.FileStatus
import com.intellij.openapi.vcs.LocalFilePath
import com.intellij.openapi.vcs.ProjectLevelVcsManager
import com.intellij.openapi.vcs.VcsKey
import com.intellij.openapi.vcs.VcsMappingListener
import com.intellij.openapi.vcs.changes.Change
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.Consumer
import com.intellij.vcs.log.Hash
import com.intellij.vcs.log.VcsCommitMetadata
import com.intellij.vcs.log.VcsFullCommitDetails
import com.intellij.vcs.log.VcsLogObjectsFactory
import com.intellij.vcs.log.VcsLogProperties
import com.intellij.vcs.log.VcsLogProvider
import com.intellij.vcs.log.VcsLogRefManager
import com.intellij.vcs.log.VcsLogRefresher
import com.intellij.vcs.log.VcsRef
import com.intellij.vcs.log.VcsUser
import com.intellij.vcs.log.TimedVcsCommit
import java.nio.file.Path

/**
 * Puts Lore into the platform's Log tool window.
 *
 * This was left out at first on the grounds that a linear chain does not need
 * the graph. That was the wrong trade: the graph is the cheap part, and
 * declining it also declined the branch tree, the branch/user/date/path
 * filters, the commit details pane and the changed-files tree that come with
 * it. A linear chain simply draws as a straight line.
 *
 * Indexing stays off. Lore is centralised and its history is one cheap call, so
 * the index would buy little and is persisted state a decoding mistake could
 * corrupt.
 */
class LoreVcsLogProvider(private val project: Project) : VcsLogProvider {

    private val factory get() = project.getService(VcsLogObjectsFactory::class.java)

    override val supportedVcs: VcsKey get() = LoreVcs.KEY

    override val referenceManager: VcsLogRefManager get() = refManager

    private val refManager = LoreVcsLogRefManager()

    override fun <T : Any?> getPropertyValue(property: VcsLogProperties.VcsLogProperty<T>): T? {
        @Suppress("UNCHECKED_CAST")
        return when (property) {
            VcsLogProperties.SUPPORTS_INDEXING -> false as T
            VcsLogProperties.HAS_COMMITTER -> true as T
            VcsLogProperties.LIGHTWEIGHT_BRANCHES -> true as T
            VcsLogProperties.SUPPORTS_LOG_DIRECTORY_HISTORY -> false as T
            else -> null
        }
    }

    override fun getCurrentBranch(root: VirtualFile): String? =
        LoreRepositoryState.getInstance(project).of(root.toNioPath())?.branchName

    override fun getCurrentUser(root: VirtualFile): VcsUser? =
        LoreLockApi.currentUserId(root.toNioPath())?.let { factory.createUser(it, it) }

    /**
     * Deprecated in favour of the suspend readRecentCommits, which takes an
     * experimental RefsLoadingPolicy. Trading a deprecation for an experimental
     * type is not an improvement for a plugin pinned to 261-262, so this stays
     * until the replacement settles.
     */
    @Suppress("OVERRIDE_DEPRECATION")
    override fun readFirstBlock(
        root: VirtualFile,
        requirements: VcsLogProvider.Requirements,
    ): VcsLogProvider.DetailedLogData {
        val walk = walk(root, requirements.commitCount)
        return DetailedLogData(walk.map { metadata(root, it) }, refs(root))
    }

    override fun readAllHashes(
        root: VirtualFile,
        commitConsumer: Consumer<in TimedVcsCommit>,
    ): VcsLogProvider.LogData {
        val walk = walk(root, UNLIMITED)
        walk.forEach { commitConsumer.consume(factory.createTimedCommit(hash(it.entry), parents(it), it.entry.timestampMillis ?: 0L)) }

        val users = walk.mapNotNull { it.entry.author }.distinct().map { factory.createUser(it, it) }.toSet()
        return LogData(refs(root), users)
    }

    override fun readMetadata(
        root: VirtualFile,
        hashes: List<String>,
        consumer: Consumer<in VcsCommitMetadata>,
    ) {
        val wanted = hashes.toHashSet()
        walk(root, UNLIMITED)
            .filter { it.entry.revision.hex in wanted }
            .forEach { consumer.consume(metadata(root, it)) }
    }

    override fun readFullDetails(
        root: VirtualFile,
        hashes: List<String>,
        commitConsumer: Consumer<in VcsFullCommitDetails>,
    ) {
        val wanted = hashes.toHashSet()
        walk(root, UNLIMITED)
            .filter { it.entry.revision.hex in wanted }
            .forEach { commitConsumer.consume(FullDetails(root, it)) }
    }

    override fun getContainingBranches(root: VirtualFile, commitHash: Hash): Collection<String> =
        branches(root.toNioPath())
            .filter { branch -> walkOf(root.toNioPath(), branch).any { it.entry.revision.hex == commitHash.asString() } }
            .map { it.name }
            .distinct()

    /**
     * The log refreshes when the mapping set changes or a Lore write lands.
     * Both funnel through the cached repository state, so the same signal that
     * invalidates it drives this.
     */
    override fun subscribeToRootRefreshEvents(
        roots: Collection<VirtualFile>,
        refresher: VcsLogRefresher,
    ): Disposable {
        val connection = project.messageBus.connect()
        connection.subscribe(
            ProjectLevelVcsManager.VCS_CONFIGURATION_CHANGED,
            VcsMappingListener { roots.forEach(refresher::refresh) },
        )
        return connection
    }

    // -- data -------------------------------------------------------------

    /** One revision plus the revision under it, which is its parent. */
    private class Step(val entry: LoreHistoryEntry, val parent: LoreHistoryEntry?)

    /**
     * Walks from the remote branch tip when there is one, so revisions the
     * checkout has not synced still appear rather than the log stopping short.
     */
    private fun walk(root: VirtualFile, limit: Int): List<Step> = walkOf(root.toNioPath(), currentBranch(root), limit)

    private fun walkOf(root: Path, branch: LoreBranch?, limit: Int = UNLIMITED): List<Step> {
        val name = branch?.name.orEmpty()
        val from = branch?.takeIf { it.location == LoreBranchLocation.REMOTE }?.latest?.hex.orEmpty()

        val history = runCatching { LoreHistoryApi.history(root, limit, branch = name, from = from) }
            .getOrDefault(emptyList())
            .ifEmpty { runCatching { LoreHistoryApi.history(root, limit, branch = name) }.getOrDefault(emptyList()) }

        return history.mapIndexed { index, entry -> Step(entry, history.getOrNull(index + 1)) }
    }

    private fun currentBranch(root: VirtualFile): LoreBranch? {
        val name = getCurrentBranch(root) ?: return null
        val all = branches(root.toNioPath()).filter { it.name == name }
        return all.firstOrNull { it.location == LoreBranchLocation.REMOTE } ?: all.firstOrNull()
    }

    private fun branches(root: Path): List<LoreBranch> =
        runCatching { LoreBranchApi.list(root).filterNot { it.isArchived } }.getOrDefault(emptyList())

    private fun refs(root: VirtualFile): Set<VcsRef> =
        branches(root.toNioPath())
            .filterNot { it.latest.isNone }
            .map {
                val type = if (it.location == LoreBranchLocation.REMOTE) LoreRefTypes.REMOTE else LoreRefTypes.LOCAL
                factory.createRef(factory.createHash(it.latest.hex), it.name, type, root)
            }
            .toSet()

    private fun hash(entry: LoreHistoryEntry): Hash = factory.createHash(entry.revision.hex)

    private fun parents(step: Step): List<Hash> = listOfNotNull(step.parent?.let(::hash))

    private fun user(name: String?): VcsUser = (name ?: "unknown").let { factory.createUser(it, it) }

    private fun metadata(root: VirtualFile, step: Step): VcsCommitMetadata {
        val entry = step.entry
        val author = entry.author ?: "unknown"
        val committer = entry.metadata.committer ?: author
        val time = entry.timestampMillis ?: 0L

        return factory.createCommitMetadata(
            hash(entry),
            parents(step),
            time,
            root,
            entry.subject.orEmpty(),
            author,
            author,
            entry.message.orEmpty(),
            committer,
            committer,
            time,
        )
    }

    /**
     * Implemented directly rather than through VcsChangesLazilyParsedDetails,
     * which is marked experimental; the interface is small and stable.
     */
    private inner class FullDetails(private val root: VirtualFile, private val step: Step) : VcsFullCommitDetails {

        private val loaded: Collection<Change> by lazy { load() }

        override fun getId(): Hash = hash(step.entry)

        override fun getParents(): List<Hash> = parents(step)

        override fun getTimestamp(): Long = step.entry.timestampMillis ?: 0L

        override fun getCommitTime(): Long = timestamp

        override fun getAuthorTime(): Long = timestamp

        override fun getRoot(): VirtualFile = root

        override fun getSubject(): String = step.entry.subject.orEmpty()

        override fun getFullMessage(): String = step.entry.message.orEmpty()

        override fun getAuthor(): VcsUser = user(step.entry.author)

        override fun getCommitter(): VcsUser = user(step.entry.metadata.committer ?: step.entry.author)

        override fun getChanges(): Collection<Change> = loaded

        override fun getChanges(parent: Int): Collection<Change> = loaded

        private fun load(): Collection<Change> {
            val path = root.toNioPath()
            val newer = LoreRevisionNumber(step.entry.revision, step.entry.number)
            val older = step.parent?.let { LoreRevisionNumber(it.revision, it.number) }

            return runCatching {
                LoreDiffApi.revisionDiff(path, step.parent?.revision?.hex.orEmpty(), step.entry.revision.hex)
                    .map { changed ->
                        val filePath = LocalFilePath(path.resolve(changed.path).toString(), false)
                        val after = LoreContentRevision(path, filePath, changed.path, newer)
                        val before = older?.let { LoreContentRevision(path, filePath, changed.path, it) }

                        when (changed.action) {
                            LoreFileAction.ADD -> Change(null, after, FileStatus.ADDED)
                            LoreFileAction.DELETE -> Change(before, null, FileStatus.DELETED)
                            else -> Change(before, after, FileStatus.MODIFIED)
                        }
                    }
            }.getOrDefault(emptyList())
        }
    }

    /**
     * Implements refsIterable rather than refs: the platform's default for refs
     * throws NotImplementedError with the message "Consider implementing
     * refsIterable", which is as clear a direction as it gets.
     */
    private class DetailedLogData(
        override val commits: List<VcsCommitMetadata>,
        private val references: Set<VcsRef>,
    ) : VcsLogProvider.DetailedLogData {
        override val refsIterable: Iterable<VcsRef> get() = references
    }

    private class LogData(
        override val refs: Set<VcsRef>,
        override val users: Set<VcsUser>,
    ) : VcsLogProvider.LogData

    private companion object {
        /** Lore reads 0 as "no limit". */
        const val UNLIMITED = 0
    }
}
