package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.vcs.changes.ui.ChangesViewContentProvider
import com.intellij.ui.content.Content
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.ConcurrentLinkedQueue
import javax.swing.JPanel
import java.awt.BorderLayout

/**
 * What Lore was asked to do, and what it said back.
 *
 * The IDE's own VCS console belongs to Git and reports `git` invocations, which
 * is worse than useless here -- this plugin never runs Git and never runs a
 * process at all, it calls into liblore. This records those calls instead.
 */
@Service(Service.Level.PROJECT)
class LoreConsoleLog {

    /** One line of the log. */
    data class Entry(val millis: Long, val text: String, val kind: Kind)

    enum class Kind { COMMAND, OUTPUT, ERROR }

    private val entries = ConcurrentLinkedQueue<Entry>()
    private val listeners = mutableListOf<(Entry) -> Unit>()

    /**
     * Nothing is recorded while no console is open. This is checked on the path
     * of every Lore call, including the per-file ones, so it has to be cheap
     * and it has to come first.
     */
    @Volatile
    var isListening: Boolean = false
        private set

    /** Reads are only worth showing when someone has asked for everything. */
    @Volatile
    var isVerbose: Boolean = false

    fun command(text: String) = add(Entry(System.currentTimeMillis(), text, Kind.COMMAND))

    fun output(text: String) = add(Entry(System.currentTimeMillis(), text, Kind.OUTPUT))

    fun error(text: String) = add(Entry(System.currentTimeMillis(), text, Kind.ERROR))

    fun history(): List<Entry> = entries.toList()

    @Synchronized
    fun subscribe(listener: (Entry) -> Unit) {
        listeners += listener
        isListening = true
    }

    @Synchronized
    fun unsubscribe(listener: (Entry) -> Unit) {
        listeners -= listener
        isListening = listeners.isNotEmpty()
    }

    fun clear() = entries.clear()

    @Synchronized
    private fun add(entry: Entry) {
        entries += entry
        // Bounded: an asset repository can produce a great many operations and
        // this is a diagnostic aid, not a record.
        while (entries.size > LIMIT) entries.poll()
        listeners.toList().forEach { it(entry) }
    }

    companion object {
        private const val LIMIT = 2000

        fun getInstance(project: Project): LoreConsoleLog = project.service()
    }
}

class LoreConsoleTab(private val project: Project) : ChangesViewContentProvider {

    override fun initTabContent(content: Content) {
        val console: ConsoleView = TextConsoleBuilderFactory.getInstance()
            .createBuilder(project)
            .console

        val log = LoreConsoleLog.getInstance(project)
        log.history().forEach { console.print(render(it), typeOf(it.kind)) }

        val listener: (LoreConsoleLog.Entry) -> Unit = { entry ->
            console.print(render(entry), typeOf(entry.kind))
        }
        log.subscribe(listener)

        val actions = DefaultActionGroup(ClearAction(console, log), VerboseAction(log))

        content.component = JPanel(BorderLayout()).apply {
            add(
                ActionManager.getInstance()
                    .createActionToolbar("LoreLensConsole", actions, false)
                    .also { it.targetComponent = console.component }
                    .component,
                BorderLayout.WEST,
            )
            add(console.component, BorderLayout.CENTER)
        }

        content.setDisposer(
            Disposable {
                log.unsubscribe(listener)
                Disposer.dispose(console)
            },
        )
    }

    private fun render(entry: LoreConsoleLog.Entry): String =
        "${TIME.format(Date(entry.millis))}  ${entry.text}\n"

    private fun typeOf(kind: LoreConsoleLog.Kind): ConsoleViewContentType = when (kind) {
        LoreConsoleLog.Kind.COMMAND -> ConsoleViewContentType.USER_INPUT
        LoreConsoleLog.Kind.ERROR -> ConsoleViewContentType.ERROR_OUTPUT
        LoreConsoleLog.Kind.OUTPUT -> ConsoleViewContentType.NORMAL_OUTPUT
    }

    /**
     * File reads run per file and per batch; reporting each one is what made
     * the console cost the editor real time. Off unless asked for.
     */
    private class VerboseAction(private val log: LoreConsoleLog) :
        com.intellij.openapi.actionSystem.ToggleAction(
            LoreLensBundle.message("console.verbose"),
            null,
            com.intellij.icons.AllIcons.Actions.Show,
        ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun isSelected(e: AnActionEvent): Boolean = log.isVerbose

        override fun setSelected(e: AnActionEvent, state: Boolean) {
            log.isVerbose = state
        }
    }

    private class ClearAction(private val console: ConsoleView, private val log: LoreConsoleLog) :
        AnAction(
            LoreLensBundle.message("console.clear"),
            null,
            com.intellij.icons.AllIcons.Actions.GC,
        ) {
        override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

        override fun actionPerformed(e: AnActionEvent) {
            log.clear()
            console.clear()
        }
    }

    private companion object {
        val TIME = SimpleDateFormat("HH:mm:ss.SSS")
    }
}

/** Shown wherever a Lore root is mapped. */
class LoreConsoleTabVisibility : java.util.function.Predicate<Project> {
    override fun test(project: Project): Boolean =
        com.dzmitryj.lorelens.repo.LoreRootFinder.mappedRoots(project).isNotEmpty()
}
