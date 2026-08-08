package com.dzmitryj.lorelens.api

import com.dzmitryj.lorelens.ui.LoreConsoleLog
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.ProjectManager

/**
 * Bridges the API layer to the console without giving it a Project.
 *
 * Lore calls are made from places that have no project to hand -- the FFI layer
 * is deliberately free of IDE types -- so operations are broadcast to whichever
 * projects are open rather than routed to one.
 */
object LoreOperationLog {

    fun succeeded(operation: String) = each { it.command(operation) }

    fun failed(message: String) = each { it.error(message) }

    private fun each(report: (LoreConsoleLog) -> Unit) {
        // The API layer is also exercised straight from tests, where there is no
        // application at all. Reporting is a convenience, never a requirement.
        if (ApplicationManager.getApplication() == null) return

        ProjectManager.getInstanceIfCreated()?.openProjects
            ?.filterNot { it.isDisposed }
            ?.forEach { project ->
                runCatching { report(LoreConsoleLog.getInstance(project)) }
            }
    }
}
