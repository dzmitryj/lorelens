package com.dzmitryj.lorelens.shelf

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.vcs.VcsDataKeys
import com.intellij.openapi.vcs.changes.ChangeListManager
import com.intellij.openapi.vcs.changes.shelf.ShelveChangesManager

/**
 * Reimplements the platform's silent shelve actions so they can hide in Lore
 * projects.
 *
 * Subclassing was not an option: ShelveSilentlyActionBase is annotated
 * @ApiStatus.Internal at class level, and both concrete actions are final. The
 * behaviour is small and rests entirely on public API, so it is reproduced
 * rather than stubbed -- a stub would break silent shelving for a project on
 * another VCS in the same IDE, since actions are application-level.
 *
 * The one omission is the first-run got-it tooltip, whose provider is internal.
 */
abstract class LoreAwareShelveSilentlyActionBase(private val rollbackChanges: Boolean) :
    DumbAwareAction() {

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    override fun update(e: AnActionEvent) {
        val project = e.project
        if (project == null || isLoreOnly(project)) {
            e.presentation.isEnabledAndVisible = false
            return
        }

        e.presentation.isVisible = true
        e.presentation.isEnabled = !e.getData(VcsDataKeys.CHANGES).isNullOrEmpty() &&
            ChangeListManager.getInstance(project).areChangeListsEnabled()
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val changes = e.getData(VcsDataKeys.CHANGES) ?: return

        FileDocumentManager.getInstance().saveAllDocuments()
        ShelveChangesManager.getInstance(project)
            .shelveSilentlyUnderProgress(changes.toList(), rollbackChanges)
    }
}

/** Shelves and removes the changes. */
class LoreAwareShelveSilentlyAction : LoreAwareShelveSilentlyActionBase(rollbackChanges = true)

/** Shelves and keeps the changes in place. */
class LoreAwareSaveToShelveAction : LoreAwareShelveSilentlyActionBase(rollbackChanges = false)
