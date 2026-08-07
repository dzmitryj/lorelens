package com.dzmitryj.lorevcs.dirty

import com.dzmitryj.lorevcs.repo.LoreRootFinder
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

/**
 * Insurance against edits made while the IDE was closed, which no VFS listener
 * can have seen.
 */
class LoreStartupRescan : ProjectActivity {

    override suspend fun execute(project: Project) {
        if (LoreRootFinder.mappedRoots(project).isEmpty()) return
        LoreFullRescanAction.rescan(project)
    }
}
