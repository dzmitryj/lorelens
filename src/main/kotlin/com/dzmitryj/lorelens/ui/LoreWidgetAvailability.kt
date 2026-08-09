package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.repo.LoreRepositoryState
import com.dzmitryj.lorelens.repo.LoreRootFinder
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.openapi.vcs.ProjectLevelVcsManager
import com.intellij.openapi.vcs.VcsMappingListener
import com.intellij.openapi.vcs.changes.ui.ChangesViewContentManager
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.openapi.wm.impl.status.widget.StatusBarWidgetsManager

/**
 * The platform asks a widget factory whether it is available once, while the
 * status bar is being built. Directory mappings resolve after that, so a factory
 * keyed on "is a Lore root mapped" always answers no and the widget never
 * appears. Nothing re-asks on its own.
 */
class LoreWidgetAvailability : ProjectActivity {

    override suspend fun execute(project: Project) {
        project.messageBus.connect().subscribe(
            ProjectLevelVcsManager.VCS_CONFIGURATION_CHANGED,
            VcsMappingListener {
                LoreRepositoryState.getInstance(project).invalidateAll()
                project.getService(StatusBarWidgetsManager::class.java)
                    ?.updateWidget(LoreStatusBarWidgetFactory::class.java)
                brandToolWindow(project)
            },
        )

        project.getService(StatusBarWidgetsManager::class.java)
            ?.updateWidget(LoreStatusBarWidgetFactory::class.java)
        brandToolWindow(project)
    }

    /**
     * Puts the Lore mark on the Version Control tool window stripe and header
     * once a Lore root is mapped. The platform names the window after the sole
     * VCS but keeps its generic icon; the icon is per-project UI state, so it is
     * set here rather than declared.
     */
    private fun brandToolWindow(project: Project) {
        ApplicationManager.getApplication().invokeLater {
            if (project.isDisposed || LoreRootFinder.mappedRoots(project).isEmpty()) return@invokeLater
            ToolWindowManager.getInstance(project)
                .getToolWindow(ChangesViewContentManager.TOOLWINDOW_ID)
                ?.setIcon(LoreIcons.ToolWindow)
        }
    }
}
