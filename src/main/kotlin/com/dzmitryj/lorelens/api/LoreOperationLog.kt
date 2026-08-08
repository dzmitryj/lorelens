package com.dzmitryj.lorelens.api

import com.dzmitryj.lorelens.ui.LoreConsoleLog
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager

/**
 * Bridges the API layer to the console without giving it a Project.
 *
 * Lore calls are made from places that have no project to hand -- the FFI layer
 * is deliberately free of IDE types -- so operations are broadcast to whichever
 * projects are open rather than routed to one.
 *
 * Recording is always on: it is a bounded append of one string, and gating it
 * on an open console meant everything before the tab's first open was lost and
 * the console opened blank. What stays gated is the expensive part -- verbose
 * per-file detail, and live printing, which only happens with a subscriber.
 */
object LoreOperationLog {

    fun succeeded(operation: String) = each { it.command(operation) }

    fun failed(message: String) = each { it.error(message) }

    /** Reads, which only reach the console when it is showing everything. */
    fun detail(operation: () -> String) {
        each { log -> if (log.isVerbose) log.output(operation()) }
    }

    private fun each(report: (LoreConsoleLog) -> Unit) {
        // The API layer is also exercised straight from tests, where there is no
        // application at all. Reporting is a convenience, never a requirement.
        if (ApplicationManager.getApplication() == null) return

        openProjects().forEach { project ->
            runCatching { report(LoreConsoleLog.getInstance(project)) }
        }
    }

    private fun openProjects(): Array<Project> =
        ProjectManager.getInstanceIfCreated()
            ?.openProjects
            ?.filterNot { it.isDisposed }
            ?.toTypedArray()
            ?: emptyArray()
}
