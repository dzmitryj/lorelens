package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.LoreVcs
import com.dzmitryj.lorelens.api.LoreBranchApi
import com.dzmitryj.lorelens.api.LoreDiffApi
import com.dzmitryj.lorelens.api.LoreHistoryApi
import com.dzmitryj.lorelens.api.LoreHistoryEntry
import com.dzmitryj.lorelens.api.LoreWriteApi
import com.dzmitryj.lorelens.changes.LoreContentRevision
import com.dzmitryj.lorelens.changes.LoreRevisionNumber
import com.dzmitryj.lorelens.lock.LoreLockService
import com.dzmitryj.lorelens.model.LoreBranch
import com.dzmitryj.lorelens.model.LoreBranchLocation
import com.dzmitryj.lorelens.model.LoreBranchTree
import com.dzmitryj.lorelens.model.LoreFileAction
import com.dzmitryj.lorelens.model.LoreRevisionChain
import com.dzmitryj.lorelens.repo.LoreBranchSwitcher
import com.dzmitryj.lorelens.repo.LoreMerger
import com.dzmitryj.lorelens.repo.LoreRepositoryState
import com.dzmitryj.lorelens.repo.LoreRootFinder
import com.dzmitryj.lorelens.update.LoreSyncSession
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.actionSystem.Separator
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.Task
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vcs.AbstractVcsHelper
import com.intellij.openapi.vcs.FileStatus
import com.intellij.openapi.vcs.LocalFilePath
import com.intellij.openapi.vcs.changes.Change
import com.intellij.openapi.vcs.changes.VcsDirtyScopeManager
import com.intellij.openapi.vcs.changes.ui.ChangesBrowserBase
import com.intellij.openapi.vcs.changes.ui.ChangesViewContentProvider
import com.intellij.openapi.vcs.vfs.ContentRevisionVirtualFile
import com.intellij.ui.DoubleClickListener
import com.intellij.ui.FilterComponent
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.PopupHandler
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.TableSpeedSearch
import com.intellij.ui.components.JBLoadingPanel
import com.intellij.ui.content.Content
import com.intellij.ui.table.TableView
import com.intellij.util.ui.ListTableModel
import java.awt.BorderLayout
import java.awt.datatransfer.StringSelection
import java.awt.event.MouseEvent
import java.nio.file.Path
import javax.swing.JPanel
import javax.swing.ListSelectionModel

/**
 * A plain history table rather than a VcsLogProvider. Lore's revision chain is
 * linear, so the Log tab's graph machinery would be paid for and unused, and it
 * persists an index that a decoding mistake would corrupt.
 */
class LoreLogTab(private val project: Project) : ChangesViewContentProvider {

    private val log = logger<LoreLogTab>()
    private var graphRows: List<LoreHistoryLanes.Row> = emptyList()

    /** Revision to branch, once the background attribution has answered. */
    private var branchOf: Map<String, String> = emptyMap()

    /** The rows currently on screen, which the graph column paints against. */
    private var visible: List<LogRow> = emptyList()

    private val model = ListTableModel(
        LoreLogColumn.columns(
            LoreGraphColumn(
                rows = { graphRows },
                authorOf = { index -> visible.getOrNull(index)?.entry?.author },
                isMerge = { index -> visible.getOrNull(index)?.entry?.isMerge == true },
            ),
            rows = { visible },
        ),
        emptyList<LogRow>(),
    )
    private val table = TableView(model)

    private lateinit var changes: LoreChangesBrowser
    private lateinit var loading: JBLoadingPanel

    private var all: List<LogRow> = emptyList()

    /** Empty is the current branch, which is what Lore defaults to. */
    private var branch: String = ""

    // After `all`, which its filter callback reads.
    private val filter = LogFilter()

    /** Non-null while looking at a branch this checkout is not on. */
    private var browsing: LoreBranch? = null

    private var known: List<LoreBranch> = emptyList()

    /** Merge hash to "from branch, into branch", filled in after the table loads. */
    private var mergeLabels: Map<String, Pair<String, String>> = emptyMap()

    private var mergeKey: String = ""

    private val details = LoreCommitDetailsPanel()
    /** Where this checkout sits, in the tab rather than only in the status bar. */
    private val repository = LoreRepositoryPanel(
        onSyncToLatest = { sync(revision = "") },
        onPush = ::push,
        onBrowse = { branch ->
            browsing = branch
            this.branch = branch.name
            refresh()
        },
        onSwitch = { branch ->
            LoreRootFinder.mappedRoots(project).firstOrNull()?.let { root ->
                LoreBranchSwitcher.switch(project, root.toNioPath(), branch.name)
            }
        },
        onMerge = { branch ->
            LoreRootFinder.mappedRoots(project).firstOrNull()?.let { root ->
                LoreMerger.merge(project, root.toNioPath(), branch.name)
            }
        },
        onReturnToCurrent = {
            browsing = null
            branch = ""
            refresh()
        },
        branches = { known },
    )

    override fun initTabContent(content: Content) {
        table.apply {
            setShowGrid(false)
            // Two rows can be selected to compare them.
            selectionModel.selectionMode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
            emptyText.text = LoreLensBundle.message("log.empty")
        }
        TableSpeedSearch.installOn(table)

        changes = LoreChangesBrowser(project)
        table.selectionModel.addListSelectionListener { event ->
            if (!event.valueIsAdjusting) showChangedFiles()
        }

        val actions = DefaultActionGroup(
            RefreshAction(),
            Separator.getInstance(),
            CompareRevisionsAction(),
            OpenAtRevisionAction(),
            ShowFileHistoryAction(),
            Separator.getInstance(),
            SyncToRevisionAction(),
            CompareWithWorkingCopyAction(),
            TakeFromBranchAction(),
            Separator.getInstance(),
            CopyRevisionAction(),
            CopyMessageAction(),
            Separator.getInstance(),
            ActionManager.getInstance().getAction("LoreLens.FullRescan"),
        )
        PopupHandler.installRowSelectionTablePopup(table, actions, "LoreLensLog")
        object : DoubleClickListener() {
            override fun onDoubleClick(event: MouseEvent): Boolean {
                changes.showDiff()
                return true
            }
        }.installOn(table)

        // Changed files above details, both to the right of the revisions, so
        // the table keeps its height instead of being squeezed from below.
        val side = OnePixelSplitter(true, "LoreLens.Log.Side", 0.55f).apply {
            firstComponent = changes
            secondComponent = details
        }

        val splitter = OnePixelSplitter(false, "LoreLens.Log.Main", 0.62f).apply {
            firstComponent = ScrollPaneFactory.createScrollPane(table)
            secondComponent = side
        }

        loading = JBLoadingPanel(BorderLayout(), content).apply {
            add(splitter, BorderLayout.CENTER)
        }

        val header = JPanel(BorderLayout()).apply {
            add(
                JPanel(BorderLayout()).apply {
                    add(
                        ActionManager.getInstance()
                            .createActionToolbar("LoreLensLog", actions, true)
                            .also { it.targetComponent = this }
                            .component,
                        BorderLayout.WEST,
                    )
                    add(filter, BorderLayout.EAST)
                },
                BorderLayout.NORTH,
            )
            add(repository, BorderLayout.SOUTH)
        }

        content.component = JPanel(BorderLayout()).apply {
            add(header, BorderLayout.NORTH)
            add(loading, BorderLayout.CENTER)
        }

        refresh()
    }

    private fun refresh() {
        loading.startLoading()
        val selected = branch

        ApplicationManager.getApplication().executeOnPooledThread {
            val roots = LoreRootFinder.mappedRoots(project).map { it.toNioPath() }
            val entries = roots.flatMap { root -> load(root, selected) }
            val found = roots.firstOrNull()?.let { root ->
                runCatching { LoreBranchApi.list(root).filterNot { it.isArchived } }
                    .onFailure { log.warn("Cannot list Lore branches for $root", it) }
                    .getOrDefault(emptyList())
            }.orEmpty()
            val names = found.map { it.name }.distinct().sorted()
            val state = repositoryState(roots.firstOrNull(), entries)

            ApplicationManager.getApplication().invokeLater {
                all = entries
                known = found
                loadMergeLabels(roots.firstOrNull(), found)
                applyFilter(filter.filter ?: "")
                table.updateColumnSizes()
                repository.show(state)
                loading.stopLoading()
            }
        }
    }

    /**
     * Walks from the remote branch tip rather than the local one, so revisions
     * that exist on the branch but have not been synced here still appear.
     * Everything at or below the checkout's own revision number is synced;
     * Lore's chain is linear, so the number is enough to tell them apart.
     */
    /**
     * Which branches hold this revision. Derived from what is already loaded
     * rather than asked for: every row in the walk belongs to the branch being
     * shown, and asking Lore per row would be a call per selection.
     */
    private fun containing(row: LogRow): List<String> =
        listOfNotNull(browsing?.name ?: currentBranchName().ifEmpty { null })

    /**
     * Naming the branches either side of a merge needs every branch's history,
     * which the table itself does not. So it is done once in the background
     * after the rows are already on screen, and cached against the tips.
     */
    private fun loadMergeLabels(root: Path?, branches: List<LoreBranch>) {
        if (root == null || all.isEmpty()) return

        val key = branches.joinToString(",") { "${it.name}@${it.latest.hex}" }
        if (key == mergeKey) return
        mergeKey = key

        ApplicationManager.getApplication().executeOnPooledThread {
            val walked = runCatching { LoreBranchWalks.attribute(root, branches, HISTORY_LIMIT) }
                .onFailure { log.warn("Cannot resolve branches for $root", it) }
                .getOrNull()
                ?: return@executeOnPooledThread

            val labels = LoreBranchWalks.mergeLabels(walked.attributed)
            val attributed = walked.attributed.associate { it.hash to it.branch }

            ApplicationManager.getApplication().invokeLater {
                mergeLabels = labels
                branchOf = attributed
                all = all.map { it.copy(merged = labels[it.entry.revision.hex]) }
                applyFilter(filter.filter ?: "")
            }
        }
    }

    private fun currentBranchName(): String =
        LoreRootFinder.mappedRoots(project).firstOrNull()
            ?.let { LoreRepositoryState.getInstance(project).cached(it.toNioPath())?.branchName }
            .orEmpty()

    private fun load(root: Path, branch: String): List<LogRow> {
        val branches = runCatching { LoreBranchApi.list(root) }
            .onFailure { log.warn("Cannot list Lore branches for $root", it) }
            .getOrDefault(emptyList())

        val state = LoreRepositoryState.getInstance(project).of(root)
        val wanted = branch.ifEmpty { state?.branchName.orEmpty() }
        val remoteTip = branches
            .firstOrNull { it.name == wanted && it.location == LoreBranchLocation.REMOTE && !it.latest.isNone }
            ?.latest

        // Branch and revision together return nothing, so the tip walk passes
        // the revision alone and the branch filter is only for the plain walk.
        val history = runCatching {
            if (remoteTip != null) {
                LoreHistoryApi.history(root, HISTORY_LIMIT, from = remoteTip.hex)
            } else {
                LoreHistoryApi.history(root, HISTORY_LIMIT, branch = branch)
            }
        }
            .onFailure { log.warn("Cannot read Lore history for $root", it) }
            .getOrDefault(emptyList())
            .ifEmpty {
                // A remote tip this checkout cannot walk is not fatal; fall back
                // to what it does have rather than showing nothing.
                runCatching { LoreHistoryApi.history(root, HISTORY_LIMIT, branch = branch) }
                    .getOrDefault(emptyList())
            }

        // Membership, not numbering: what the checkout's own revision reaches is
        // what it has. Numbers restart per branch, so comparing them against
        // rows walked in from an ancestor branch marks the wrong ones unsynced.
        val here = state?.revision?.hex
            ?.let { at -> runCatching { LoreHistoryApi.history(root, HISTORY_LIMIT, from = at) }.getOrNull() }
            ?.mapTo(HashSet()) { it.revision.hex }

        // One chip per branch, at where the branch actually is. Keying on every
        // entry put "dev-main" at both its local and its remote tip, which read
        // as two branches with the same name.
        val tips = branches
            .filterNot { it.latest.isNone }
            .groupBy { it.name }
            .mapNotNull { (name, sides) ->
                val at = sides.firstOrNull { it.location == LoreBranchLocation.REMOTE } ?: sides.first()
                at.latest.hex to name
            }
            .groupBy({ it.first }, { it.second })
            .mapValues { (_, names) -> names.distinct() }

        return history.map {
            LogRow(
                root = root,
                entry = it,
                synced = here == null || it.revision.hex in here,
                tips = tips[it.revision.hex].orEmpty(),
                merged = mergeLabels[it.revision.hex],
            )
        }
    }

    /** Runs off the EDT: it reads repository state and the lock table. */
    private fun repositoryState(root: Path?, rows: List<LogRow>): LoreRepositoryPanel.State? {
        if (root == null) return null
        val status = LoreRepositoryState.getInstance(project).of(root) ?: return null

        return LoreRepositoryPanel.State(
            browsing = browsing?.name,
            branch = status.branchName,
            localRevision = status.revisionNumber,
            localHash = status.revision,
            remoteRevision = rows.firstOrNull()?.entry?.number,
            behind = rows.count { !it.synced },
            localAhead = status.localAhead,
            remoteAvailable = status.remoteAvailable,
            locksHeld = LoreLockService.getInstance(project).heldByMe(),
        )
    }

    private fun push() {
        val root = LoreRootFinder.mappedRoots(project).firstOrNull()?.toNioPath() ?: return

        object : Task.Backgroundable(project, LoreLensBundle.message("repo.push.progress"), true) {
            private var failure: Throwable? = null

            override fun run(indicator: ProgressIndicator) {
                failure = runCatching { LoreWriteApi.push(root) }.exceptionOrNull()
                    ?.also { log.warn("Cannot push $root", it) }
            }

            override fun onFinished() {
                LoreRepositoryState.getInstance(project).invalidateAll()
                failure?.let {
                    Messages.showErrorDialog(
                        project,
                        it.message ?: LoreLensBundle.message("repo.push.failed"),
                        LoreLensBundle.message("repo.push.failed"),
                    )
                }
                refresh()
            }
        }.queue()
    }

    private fun applyFilter(text: String) {
        val needle = text.trim().lowercase()
        setRows(if (needle.isEmpty()) all else all.filter { row ->
            val entry = row.entry
            entry.subject.orEmpty().lowercase().contains(needle) ||
                entry.author.orEmpty().lowercase().contains(needle) ||
                entry.number.toString().contains(needle)
        })
    }

    /**
     * The graph is laid out for exactly the rows on screen: filtering changes
     * which revisions are visible, and lanes drawn for hidden ones would point
     * at nothing.
     */
    private fun setRows(rows: List<LogRow>) {
        graphRows = LoreHistoryLanes.layout(
            rows.map {
                LoreHistoryLanes.Input(
                    hash = it.entry.revision.hex,
                    parents = it.entry.parents.map { parent -> parent.hex },
                    branch = branchOf[it.entry.revision.hex],
                )
            },
            order = LoreBranchTree.order(known),
        )
        visible = rows
        model.items = rows
        // Lane count and chip widths both come from the rows, so the columns
        // have to be sized again once those rows are in.
        table.updateColumnSizes()
    }

    /**
     * One selected revision shows what it did to its parent; two show the span
     * between them. Pairing against the working tree instead would report "this
     * revision versus whatever is on disk now", a different and usually wrong
     * question.
     */
    private fun showChangedFiles() {
        val selected = table.selectedObjects.takeIf { it.size == 1 }?.single()
        details.show(selected, selected?.let { row -> row.tips.ifEmpty { containing(row) } }.orEmpty())

        val pair = selectedSpan()
        if (pair == null) {
            changes.setChangesToDisplay(emptyList())
            return
        }

        val (root, span) = pair
        val (older, newer) = span

        ApplicationManager.getApplication().executeOnPooledThread {
            val built = runCatching { changesBetween(root, older, newer) }
                .onFailure { log.warn("Cannot read changes for revision ${newer.number}", it) }
                .getOrDefault(emptyList())
            ApplicationManager.getApplication().invokeLater { changes.setChangesToDisplay(built) }
        }
    }

    /** The revision range the selection stands for, oldest first. */
    private fun selectedSpan(): Pair<Path, Pair<LoreHistoryEntry?, LoreHistoryEntry>>? {
        val selected = table.selectedObjects
        return when (selected.size) {
            1 -> {
                // The table sorts as a view, so a parent lookup has to be done
                // against the model, which stays in history order.
                val row = table.convertRowIndexToModel(table.selectedRow)
                val entry = model.items.getOrNull(row) ?: return null
                val parent = LoreRevisionChain.parentOf(model.items, row)?.entry
                entry.root to (parent to entry.entry)
            }

            2 -> {
                val (newer, older) = selected.sortedByDescending { it.entry.number }
                newer.root to (older.entry to newer.entry)
            }

            else -> null
        }
    }

    private fun changesBetween(
        root: Path,
        older: LoreHistoryEntry?,
        newer: LoreHistoryEntry,
    ): List<Change> {
        val newerRevision = LoreRevisionNumber(newer.revision, newer.number)
        val olderRevision = older?.let { LoreRevisionNumber(it.revision, it.number) }

        return LoreDiffApi.revisionDiff(root, older?.revision?.hex.orEmpty(), newer.revision.hex)
            .map { changed ->
                val filePath = LocalFilePath(root.resolve(changed.path).toString(), false)
                val after = LoreContentRevision(root, filePath, changed.path, newerRevision)
                val before = olderRevision?.let { LoreContentRevision(root, filePath, changed.path, it) }

                when (changed.action) {
                    LoreFileAction.ADD -> Change(null, after, FileStatus.ADDED)
                    LoreFileAction.DELETE -> Change(before, null, FileStatus.DELETED)
                    else -> Change(before, after, FileStatus.MODIFIED)
                }
            }
    }

    /**
     * Syncs, then refreshes. Empty revision means the branch tip; anything else
     * moves the checkout to that revision, which Lore treats as a normal sync
     * rather than a detached state.
     */
    private fun sync(revision: String) {
        val root = LoreRootFinder.mappedRoots(project).firstOrNull()?.toNioPath() ?: return

        object : Task.Backgroundable(project, LoreLensBundle.message("update.progress", root), true) {
            private var failure: Throwable? = null

            override fun run(indicator: ProgressIndicator) {
                failure = runCatching { LoreSyncSession.sync(root, revision) }.exceptionOrNull()
                    ?.also { log.warn("Cannot sync $root", it) }
            }

            override fun onFinished() {
                LoreRepositoryState.getInstance(project).invalidateAll()
                VcsDirtyScopeManager.getInstance(project).markEverythingDirty()
                failure?.let {
                    Messages.showErrorDialog(
                        project,
                        it.message ?: LoreLensBundle.message("log.sync.failed"),
                        LoreLensBundle.message("log.sync.failed"),
                    )
                }
                refresh()
            }
        }.queue()
    }

    /** The file selected below, and the revision it should be read at. */
    private fun selectedFile(): Triple<Path, String, LoreHistoryEntry>? {
        val change = changes.selectedChanges.firstOrNull() ?: return null
        val row = table.selectedObjects.firstOrNull() ?: return null
        val path = (change.afterRevision ?: change.beforeRevision)?.file?.path ?: return null
        val relative = runCatching { row.root.relativize(Path.of(path)).toString().replace('\\', '/') }
            .getOrNull()
            ?.takeIf { it.isNotEmpty() && !it.startsWith("..") }
            ?: return null
        return Triple(row.root, relative, row.entry)
    }

    /** Only while browsing: against your own branch this is just the diff above. */
    private inner class CompareWithWorkingCopyAction : AnAction(
        LoreLensBundle.message("branch.compare.action"),
        null,
        com.intellij.icons.AllIcons.Actions.DiffWithClipboard,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun update(e: AnActionEvent) {
            e.presentation.isEnabledAndVisible = browsing != null && selectedFile() != null
        }

        override fun actionPerformed(e: AnActionEvent) {
            val (root, relative, entry) = selectedFile() ?: return
            LoreCrossBranch.compareWithWorkingCopy(
                project, root, relative, entry.revision, entry.number, browsing?.name.orEmpty(),
            )
        }
    }

    private inner class TakeFromBranchAction : AnAction(
        LoreLensBundle.message("branch.take.action"),
        null,
        com.intellij.icons.AllIcons.Actions.Download,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun update(e: AnActionEvent) {
            e.presentation.isEnabledAndVisible = browsing != null && selectedFile() != null
        }

        override fun actionPerformed(e: AnActionEvent) {
            val (root, relative, entry) = selectedFile() ?: return
            LoreCrossBranch.takeFromBranch(
                project, root, relative, entry.revision, entry.number, browsing?.name.orEmpty(),
            )
        }
    }

    private inner class CopyRevisionAction : AnAction(
        LoreLensBundle.message("log.copy.revision"),
        null,
        com.intellij.icons.AllIcons.Actions.Copy,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun update(e: AnActionEvent) {
            e.presentation.isEnabled = table.selectedObjects.size == 1
        }

        override fun actionPerformed(e: AnActionEvent) {
            val row = table.selectedObjects.singleOrNull() ?: return
            CopyPasteManager.getInstance().setContents(StringSelection(row.entry.revision.hex))
        }
    }

    private inner class CopyMessageAction : AnAction(
        LoreLensBundle.message("log.copy.message"),
        null,
        com.intellij.icons.AllIcons.Actions.InlayRenameInComments,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun update(e: AnActionEvent) {
            e.presentation.isEnabled = table.selectedObjects.size == 1
        }

        override fun actionPerformed(e: AnActionEvent) {
            val row = table.selectedObjects.singleOrNull() ?: return
            CopyPasteManager.getInstance()
                .setContents(StringSelection(row.entry.message ?: row.entry.subject.orEmpty()))
        }
    }

    /** Opens the platform's own history view for the file selected below. */
    private inner class ShowFileHistoryAction : AnAction(
        LoreLensBundle.message("log.show.file.history"),
        null,
        com.intellij.icons.AllIcons.Vcs.History,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun update(e: AnActionEvent) {
            e.presentation.isEnabled = changes.selectedChanges.isNotEmpty()
        }

        override fun actionPerformed(e: AnActionEvent) {
            val vcs = LoreVcs.of(project) ?: return
            val path = changes.selectedChanges.firstOrNull()
                ?.let { it.afterRevision ?: it.beforeRevision }
                ?.file
                ?: return
            AbstractVcsHelper.getInstance(project).showFileHistory(vcs.vcsHistoryProvider, path, vcs)
        }
    }

    /** Enabled for a single revision this checkout does not have. */
    private inner class SyncToRevisionAction : AnAction(
        LoreLensBundle.message("log.sync.revision"),
        null,
        com.intellij.icons.AllIcons.Actions.Download,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun update(e: AnActionEvent) {
            e.presentation.isEnabledAndVisible = browsing == null && table.selectedObjects.size == 1
        }

        override fun actionPerformed(e: AnActionEvent) {
            val row = table.selectedObjects.singleOrNull() ?: return
            sync(row.entry.revision.hex)
        }
    }

    private inner class LogFilter : FilterComponent("LoreLens.Log.Filter", 10) {
        override fun filter() = applyFilter(filter ?: "")
    }

    private inner class RefreshAction : AnAction(
        LoreLensBundle.message("log.refresh"),
        null,
        com.intellij.icons.AllIcons.Actions.Refresh,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun actionPerformed(e: AnActionEvent) = refresh()
    }

    /**
     * Opens the pane's contents in a diff window. Going through the browser
     * rather than ShowDiffAction keeps the revision-labelled titles, which are
     * attached by its request producer.
     */
    private inner class CompareRevisionsAction : AnAction(
        LoreLensBundle.message("log.compare"),
        null,
        com.intellij.icons.AllIcons.Actions.Diff,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun update(e: AnActionEvent) {
            e.presentation.isEnabled = table.selectedObjects.size in 1..2
        }

        override fun actionPerformed(e: AnActionEvent) =
            ChangesBrowserBase.showStandaloneDiff(project, changes)
    }

    /** Opens the file as it stood at the selected revision, read only. */
    private inner class OpenAtRevisionAction : AnAction(
        LoreLensBundle.message("log.open.at.revision"),
        null,
        com.intellij.icons.AllIcons.Actions.MenuOpen,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun update(e: AnActionEvent) {
            e.presentation.isEnabled = changes.selectedChanges.isNotEmpty()
        }

        override fun actionPerformed(e: AnActionEvent) {
            changes.selectedChanges
                .mapNotNull { it.beforeRevision ?: it.afterRevision }
                .forEach { revision ->
                    val file = ContentRevisionVirtualFile.create(revision)
                    FileEditorManager.getInstance(project).openFile(file, true)
                }
        }
    }

    private companion object {
        const val HISTORY_LIMIT = 200
    }
}
