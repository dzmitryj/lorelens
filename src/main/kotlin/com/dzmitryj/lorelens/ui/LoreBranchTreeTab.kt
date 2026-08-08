package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.api.LoreBranchApi
import com.dzmitryj.lorelens.model.LoreBranch
import com.dzmitryj.lorelens.model.LoreBranchLocation
import com.dzmitryj.lorelens.model.LoreBranchTree
import com.dzmitryj.lorelens.repo.LoreBranchSwitcher
import com.dzmitryj.lorelens.repo.LoreMerger
import com.dzmitryj.lorelens.repo.LoreRootFinder
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.ui.ChangesViewContentProvider
import com.intellij.ui.ColoredTreeCellRenderer
import com.intellij.ui.PopupHandler
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.content.Content
import com.intellij.ui.treeStructure.Tree
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreeSelectionModel

/**
 * The branch hierarchy, which is a different question from the commit graph:
 * `main` at the root, `dev-main` beneath it, the per-developer branches beneath
 * that. Read from the branch points Lore records rather than guessed at.
 *
 * One `lore_branch_list` call, so this can load on show without the per-branch
 * history the commit graph needs.
 */
class LoreBranchTreeTab(private val project: Project) : ChangesViewContentProvider {

    private val log = logger<LoreBranchTreeTab>()
    private val root = DefaultMutableTreeNode()
    private val model = DefaultTreeModel(root)

    private val tree = Tree(model).apply {
        isRootVisible = false
        showsRootHandles = true
        selectionModel.selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION
        cellRenderer = Renderer()
        border = JBUI.Borders.empty(4)
    }

    override fun initTabContent(content: Content) {
        val actions = DefaultActionGroup(
            RefreshAction(),
            SwitchAction(),
            MergeAction(),
        )
        PopupHandler.installPopupMenu(tree, actions, "LoreLensBranchTree")

        content.component = JPanel(BorderLayout()).apply {
            add(
                ActionManager.getInstance()
                    .createActionToolbar("LoreLensBranchTree", actions, false)
                    .also { it.targetComponent = tree }
                    .component,
                BorderLayout.WEST,
            )
            add(ScrollPaneFactory.createScrollPane(tree, true), BorderLayout.CENTER)
        }

        refresh()
    }

    private fun refresh() {
        val path = LoreRootFinder.mappedRoots(project).firstOrNull()?.toNioPath() ?: return

        ApplicationManager.getApplication().executeOnPooledThread {
            val branches = runCatching { LoreBranchApi.list(path) }
                .onFailure { log.warn("Cannot list Lore branches for $path", it) }
                .getOrDefault(emptyList())

            ApplicationManager.getApplication().invokeLater { show(branches) }
        }
    }

    private fun show(branches: List<LoreBranch>) {
        root.removeAllChildren()
        LoreBranchTree.build(branches.filterNot { it.isArchived }).forEach { root.add(nodeOf(it)) }
        model.reload()

        var index = 0
        while (index < tree.rowCount) {
            tree.expandRow(index)
            index++
        }
    }

    private fun nodeOf(node: LoreBranchTree.Node): DefaultMutableTreeNode =
        DefaultMutableTreeNode(node.branch).apply {
            node.children.forEach { add(nodeOf(it)) }
        }

    private fun selected(): LoreBranch? =
        (tree.selectionPath?.lastPathComponent as? DefaultMutableTreeNode)?.userObject as? LoreBranch

    private inner class RefreshAction :
        AnAction(LoreLensBundle.message("log.refresh"), null, AllIcons.Actions.Refresh) {

        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun actionPerformed(e: AnActionEvent) = refresh()
    }

    private inner class SwitchAction :
        AnAction(LoreLensBundle.message("tree.switch"), null, AllIcons.Vcs.Branch) {

        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun update(e: AnActionEvent) {
            e.presentation.isEnabled = selected()?.isCurrent == false
        }

        override fun actionPerformed(e: AnActionEvent) {
            val branch = selected() ?: return
            val path = LoreRootFinder.mappedRoots(project).firstOrNull()?.toNioPath() ?: return
            LoreBranchSwitcher.switch(project, path, branch.name)
        }
    }

    private inner class MergeAction :
        AnAction(LoreLensBundle.message("tree.merge"), null, AllIcons.Vcs.Merge) {

        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun update(e: AnActionEvent) {
            e.presentation.isEnabled = selected()?.isCurrent == false
        }

        override fun actionPerformed(e: AnActionEvent) {
            val branch = selected() ?: return
            val path = LoreRootFinder.mappedRoots(project).firstOrNull()?.toNioPath() ?: return
            LoreMerger.merge(project, path, branch.name)
        }
    }

    private class Renderer : ColoredTreeCellRenderer() {
        override fun customizeCellRenderer(
            tree: JTree,
            value: Any?,
            selected: Boolean,
            expanded: Boolean,
            leaf: Boolean,
            row: Int,
            hasFocus: Boolean,
        ) {
            val branch = (value as? DefaultMutableTreeNode)?.userObject as? LoreBranch ?: return

            icon = if (branch.isCurrent) AllIcons.Vcs.BranchNode else AllIcons.Vcs.Branch
            append(
                branch.name,
                if (branch.isCurrent) {
                    SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES
                } else {
                    SimpleTextAttributes.REGULAR_ATTRIBUTES
                },
            )
            append("  ${branch.latest.short}", SimpleTextAttributes.GRAYED_ATTRIBUTES)

            if (branch.location == LoreBranchLocation.REMOTE) {
                append("  ${LoreLensBundle.message("graph.remote")}", SimpleTextAttributes.GRAYED_ITALIC_ATTRIBUTES)
            }
            if (branch.creator.isNotEmpty()) {
                append("  ${branch.creator}", SimpleTextAttributes.GRAYED_ATTRIBUTES)
            }
        }
    }
}

/** Shown wherever a Lore root is mapped. */
class LoreBranchTreeTabVisibility : java.util.function.Predicate<Project> {
    override fun test(project: Project): Boolean =
        LoreRootFinder.mappedRoots(project).isNotEmpty()
}
