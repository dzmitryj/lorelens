package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.repo.LoreRepositoryState
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.openapi.vcs.ProjectLevelVcsManager
import com.intellij.openapi.vcs.VcsMappingListener
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
            },
        )

        project.getService(StatusBarWidgetsManager::class.java)
            ?.updateWidget(LoreStatusBarWidgetFactory::class.java)
    }
}
