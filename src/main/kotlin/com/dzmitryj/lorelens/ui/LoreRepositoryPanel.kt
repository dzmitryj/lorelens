package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.model.LoreRevisionId
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.ui.SimpleColoredComponent
import com.intellij.ui.SimpleTextAttributes
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.FlowLayout
import javax.swing.JPanel

/**
 * Where the repository stands, in the tool window rather than only behind a
 * status bar popup: branch, the revision this checkout is on, the revision the
 * branch is on, and what is holding it back from either end.
 */
class LoreRepositoryPanel(
    private val onSyncToLatest: () -> Unit,
    private val onPush: () -> Unit,
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
    )

    private val line = SimpleColoredComponent().apply { isOpaque = false }

    private val syncAction = SyncToLatestAction()
    private val pushAction = PushAction()

    private var state: State? = null

    init {
        border = JBUI.Borders.empty(2, 8)

        val actions = DefaultActionGroup(syncAction, pushAction)
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

    private inner class SyncToLatestAction : AnAction(
        LoreLensBundle.message("log.sync.latest"),
        null,
        AllIcons.Actions.Download,
    ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun update(e: AnActionEvent) {
            e.presentation.isEnabledAndVisible = state?.let { it.behind > 0 } == true
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
            e.presentation.isEnabledAndVisible = state?.localAhead == true
        }

        override fun actionPerformed(e: AnActionEvent) = onPush()
    }
}
