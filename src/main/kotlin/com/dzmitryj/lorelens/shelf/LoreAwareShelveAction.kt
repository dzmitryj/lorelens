package com.dzmitryj.lorelens.shelf

import com.dzmitryj.lorelens.LoreVcs
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.ProjectLevelVcsManager
import com.intellij.openapi.vcs.actions.commit.AbstractCommitChangesAction
import com.intellij.openapi.vcs.changes.CommitExecutor
import com.intellij.openapi.vcs.changes.shelf.ShelveChangesCommitExecutor

/**
 * Replaces the platform's Shelve action so it disappears in Lore projects.
 *
 * Shelving is patch-based and knows nothing about Lore's staged state, and an
 * unshelve writes files that may be locked. Registered with overrides="true"
 * rather than unregistered, because actions are application-level: a project on
 * another VCS in the same IDE has to keep working, so this delegates to the
 * platform's own executor whenever Lore is not the VCS in play.
 */
class LoreAwareShelveAction : AbstractCommitChangesAction() {

    override fun getExecutor(project: Project): CommitExecutor = ShelveChangesCommitExecutor(project)

    override fun update(e: AnActionEvent) {
        val project = e.project
        if (project != null && isLoreOnly(project)) {
            e.presentation.isEnabledAndVisible = false
            return
        }
        super.update(e)
    }
}

/**
 * True when Lore is the only VCS in play. Anything else means another VCS's
 * project is using the action and it has to keep working.
 */
internal fun isLoreOnly(project: Project): Boolean =
    ProjectLevelVcsManager.getInstance(project).getSingleVCS() is LoreVcs
