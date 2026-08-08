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
 * Every check here is about staying free when nobody is watching: this sits on
 * the path of every Lore call, including the per-file ones.
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
            runCatching {
                val log = LoreConsoleLog.getInstance(project)
                if (log.isListening) report(log)
            }
        }
    }

    private fun openProjects(): Array<Project> =
        ProjectManager.getInstanceIfCreated()
            ?.openProjects
            ?.filterNot { it.isDisposed }
            ?.toTypedArray()
            ?: emptyArray()
}
