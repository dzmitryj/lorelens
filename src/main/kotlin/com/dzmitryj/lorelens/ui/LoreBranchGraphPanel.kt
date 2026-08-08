package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.model.LoreBranch
import com.dzmitryj.lorelens.model.LoreBranchLocation
import com.intellij.icons.AllIcons
import com.intellij.ui.ColoredTreeCellRenderer
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.treeStructure.Tree
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreePath

/**
 * Where a revision sits among the branches: which branch tips are at it, which
 * branches were cut from it, and where every other branch currently stands.
 *
 * Lore records each branch's branch-point stack, so the shape is real rather
 * than inferred from message text.
 */
class LoreBranchGraphPanel : JPanel(BorderLayout()) {

    private val root = DefaultMutableTreeNode()
    private val model = DefaultTreeModel(root)
    private val tree = Tree(model).apply {
        isRootVisible = false
        showsRootHandles = true
        cellRenderer = Renderer()
        border = JBUI.Borders.empty(4)
    }

    init {
        add(ScrollPaneFactory.createScrollPane(tree, true), BorderLayout.CENTER)
    }

    fun show(branches: List<LoreBranch>, selected: LogRow?) {
        root.removeAllChildren()

        val revision = selected?.entry?.revision?.hex
        val live = branches.filterNot { it.isArchived }.sortedBy { it.name }

        val here = live.filter { it.latest.hex == revision }
        val cutFrom = live.filter { branch -> branch.branchPoints.any { it.hex == revision } }
        val rest = live - here.toSet() - cutFrom.toSet()

        if (revision != null) {
            section(LoreLensBundle.message("graph.tip.here"), here)
            section(LoreLensBundle.message("graph.cut.here"), cutFrom)
        }
        section(LoreLensBundle.message("graph.other"), rest)

        model.reload()
        expandAll()
    }

    private fun section(title: String, branches: List<LoreBranch>) {
        if (branches.isEmpty()) return
        val node = DefaultMutableTreeNode(Section(title, branches.size))
        branches.forEach { node.add(DefaultMutableTreeNode(it)) }
        root.add(node)
    }

    private fun expandAll() {
        var index = 0
        while (index < tree.rowCount) {
            tree.expandRow(index)
            index++
        }
        tree.selectionPath = null
        tree.scrollPathToVisible(TreePath(root.path))
    }

    private class Section(val title: String, val count: Int)

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
            when (val payload = (value as? DefaultMutableTreeNode)?.userObject) {
                is Section -> {
                    append(payload.title, SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES)
                    append("  ${payload.count}", SimpleTextAttributes.GRAYED_ATTRIBUTES)
                }

                is LoreBranch -> {
                    icon = if (payload.isCurrent) AllIcons.Vcs.BranchNode else AllIcons.Vcs.Branch
                    append(
                        payload.name,
                        if (payload.isCurrent) {
                            SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES
                        } else {
                            SimpleTextAttributes.REGULAR_ATTRIBUTES
                        },
                    )
                    append("  ${payload.latest.short}", SimpleTextAttributes.GRAYED_ATTRIBUTES)
                    if (payload.location == LoreBranchLocation.REMOTE) {
                        append(
                            "  ${LoreLensBundle.message("graph.remote")}",
                            SimpleTextAttributes.GRAYED_ITALIC_ATTRIBUTES,
                        )
                    }
                    if (payload.creator.isNotEmpty()) {
                        append(
                            "  ${payload.creator}",
                            SimpleTextAttributes.GRAYED_ATTRIBUTES,
                        )
                    }
                }

                else -> Unit
            }
        }
    }
}
