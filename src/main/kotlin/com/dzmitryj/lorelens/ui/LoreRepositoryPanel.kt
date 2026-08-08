package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.model.LoreBranch
import com.dzmitryj.lorelens.model.LoreRevisionId
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.SimpleColoredComponent
import com.intellij.ui.SimpleTextAttributes
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Cursor
import java.awt.FlowLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JPanel

/**
 * Where the repository stands, in the tool window rather than only behind a
 * status bar popup: branch, the revision this checkout is on, the revision the
 * branch is on, and what is holding it back from either end.
 *
 * The branch name is the control for changing any of it, in the manner of the
 * IDE's own branch widget.
 */
class LoreRepositoryPanel(
    private val onSyncToLatest: () -> Unit,
    private val onPush: () -> Unit,
    private val onBrowse: (LoreBranch) -> Unit,
    private val onSwitch: (LoreBranch) -> Unit,
    private val onReturnToCurrent: () -> Unit,
    private val branches: () -> List<LoreBranch>,
) : JPanel(BorderLayout()) {

    data class State(
        val branch: String,
        val localRevision: Long,
        val localHash: LoreRevisionId?,
        val remoteRevision: Long?,
        val behind: Int,
        val localAhead: Boolean,
        val remoteAvailable: Boolean,
        val locksHeld: Int,
        /** Set while looking at a branch this checkout is not on. */
        val browsing: String? = null,
    ) {
        val isBrowsing: Boolean get() = browsing != null
    }

    private val line = SimpleColoredComponent().apply {
        isOpaque = false
        cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
    }

    private var state: State? = null

    init {
        border = JBUI.Borders.empty(2, 8)

        line.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(event: MouseEvent) = showBranchPopup(event)
        })

        val actions = DefaultActionGroup(
            SyncToLatestAction(),
            PushAction(),
            ReturnToCurrentAction(),
        )
        add(line, BorderLayout.CENTER)
        add(
            JPanel(FlowLayout(FlowLayout.LEFT, 0, 0)).apply {
                isOpaque = false
                add(
                    ActionManager.getInstance()
                        .createActionToolbar("LoreLensRepository", actions, true)
                        .also { it.targetComponent = this@LoreRepositoryPanel }
                        .component,
                )
            },
            BorderLayout.EAST,
        )
    }

    fun show(state: State?) {
        this.state = state
        line.clear()

        if (state == null) {
            line.append(LoreLensBundle.message("repo.none"), SimpleTextAttributes.GRAYED_ATTRIBUTES)
            return
        }

        line.icon = AllIcons.Vcs.Branch

        if (state.isBrowsing) {
            line.append(state.browsing.orEmpty(), SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES)
            line.append(
                "   ${LoreLensBundle.message("repo.browsing", state.branch)}",
                SimpleTextAttributes.GRAYED_ITALIC_ATTRIBUTES,
            )
            return
        }

        line.append(state.branch, SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES)
        line.append(
            "  ${LoreLensBundle.message("repo.at", state.localRevision, state.localHash?.short.orEmpty())}",
            SimpleTextAttributes.REGULAR_ATTRIBUTES,
        )

        when {
            !state.remoteAvailable ->
                line.append(
                    "   ${LoreLensBundle.message("repo.offline")}",
                    SimpleTextAttributes.GRAYED_ITALIC_ATTRIBUTES,
                )

            state.behind > 0 ->
                line.append(
                    "   ${LoreLensBundle.message("repo.behind", state.behind, state.remoteRevision ?: 0)}",
                    SimpleTextAttributes.ERROR_ATTRIBUTES,
                )

            else ->
                line.append(
                    "   ${LoreLensBundle.message("repo.current")}",
                    SimpleTextAttributes.GRAYED_ATTRIBUTES,
                )
        }

        if (state.localAhead) {
            line.append(
                "   ${LoreLensBundle.message("repo.ahead")}",
                SimpleTextAttributes.SYNTHETIC_ATTRIBUTES,
            )
        }

        if (state.locksHeld > 0) {
            line.append(
                "   ${LoreLensBundle.message("repo.locks", state.locksHeld)}",
                SimpleTextAttributes.GRAYED_ATTRIBUTES,
            )
        }
    }

    /**
     * Browsing is separated from switching because only one of them touches the
     * working directory. Reading another branch should never be one misclick
     * away from rewriting files on disk.
     */
    private fun showBranchPopup(event: MouseEvent) {
        val current = state?.branch ?: return
        val all = branches().filterNot { it.isArchived }.distinctBy { it.name }.sortedBy { it.name }

        val group = DefaultActionGroup().apply {
            if (state?.isBrowsing == true) {
                add(ReturnToCurrentAction())
                addSeparator()
            }

            addSeparator(LoreLensBundle.message("repo.popup.browse"))
            all.filterNot { it.name == current }.forEach { branch ->
                add(BranchAction(branch, browse = true))
            }

            addSeparator(LoreLensBundle.message("repo.popup.switch"))
            all.filterNot { it.name == current }.forEach { branch ->
                add(BranchAction(branch, browse = false))
            }
        }

        JBPopupFactory.getInstance()
            .createActionGroupPopup(
                LoreLensBundle.message("repo.popup.title", current),
                group,
                com.intellij.openapi.actionSystem.DataContext.EMPTY_CONTEXT,
                JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                false,
            )
            .show(com.intellij.ui.awt.RelativePoint(event))
    }

    private inner class BranchAction(private val branch: LoreBranch, private val browse: Boolean) :
        AnAction(branch.name, null, if (browse) AllIcons.Actions.Preview else AllIcons.Vcs.Branch) {

        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun actionPerformed(e: AnActionEvent) =
            if (browse) onBrowse(branch) else onSwitch(branch)
    }

    private inner class SyncToLatestAction : AnAction(
        LoreLensBundle.message("log.sync.latest"),
        null,
        AllIcons.Actions.Download,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        // Hidden while browsing: syncing to a revision on a branch this checkout
        // is not on is not something Lore should be asked to do.
        override fun update(e: AnActionEvent) {
            e.presentation.isEnabledAndVisible = state?.let { !it.isBrowsing && it.behind > 0 } == true
        }

        override fun actionPerformed(e: AnActionEvent) = onSyncToLatest()
    }

    private inner class PushAction : AnAction(
        LoreLensBundle.message("repo.push"),
        null,
        AllIcons.Actions.Upload,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun update(e: AnActionEvent) {
            e.presentation.isEnabledAndVisible = state?.let { !it.isBrowsing && it.localAhead } == true
        }

        override fun actionPerformed(e: AnActionEvent) = onPush()
    }

    private inner class ReturnToCurrentAction : AnAction(
        LoreLensBundle.message("repo.return"),
        null,
        AllIcons.Actions.Back,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun update(e: AnActionEvent) {
            e.presentation.isEnabledAndVisible = state?.isBrowsing == true
        }

        override fun actionPerformed(e: AnActionEvent) = onReturnToCurrent()
    }
}
