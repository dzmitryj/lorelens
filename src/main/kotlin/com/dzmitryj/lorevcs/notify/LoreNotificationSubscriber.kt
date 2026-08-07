package com.dzmitryj.lorevcs.notify

import com.dzmitryj.lorevcs.LoreBundle
import com.dzmitryj.lorevcs.ffi.EventPump
import com.dzmitryj.lorevcs.ffi.LoreArgs
import com.dzmitryj.lorevcs.ffi.generated.LoreEvent
import com.dzmitryj.lorevcs.ffi.generated.LoreFunctions
import com.dzmitryj.lorevcs.ffi.generated.NotificationBranchPushedEvent
import com.dzmitryj.lorevcs.ffi.generated.NotificationResourceLockedEvent
import com.dzmitryj.lorevcs.ffi.generated.NotificationResourceUnlockedEvent
import com.dzmitryj.lorevcs.ffi.generated.lore_notification_subscribe_args_t
import com.dzmitryj.lorevcs.lock.LoreLockService
import com.dzmitryj.lorevcs.repo.LoreRootFinder
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import java.lang.foreign.Arena
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Consumes Lore's server-side notification stream. In-process this is a
 * long-running call rather than a supervised subprocess, so there is no stream
 * framing to guess and nothing to leave behind on exit.
 */
@Service(Service.Level.PROJECT)
class LoreNotificationSubscriber(private val project: Project) : Disposable {

    private val log = logger<LoreNotificationSubscriber>()
    private val running = ConcurrentHashMap<Path, AtomicBoolean>()

    fun start() {
        LoreRootFinder.mappedRoots(project).forEach { root -> subscribe(root.toNioPath()) }
    }

    private fun subscribe(root: Path) {
        val flag = running.computeIfAbsent(root) { AtomicBoolean(false) }
        if (!flag.compareAndSet(false, true)) return

        ApplicationManager.getApplication().executeOnPooledThread {
            try {
                Arena.ofConfined().use { arena ->
                    val args = LoreArgs(arena)
                    val globals = args.globals(root)
                    val options = arena.allocate(lore_notification_subscribe_args_t.LAYOUT)

                    EventPump.call(arena, { event -> handle(root, event) }) { callback ->
                        LoreFunctions.lore_notification_subscribe
                            .invokeExact(globals, options, callback) as Int
                    }
                }
            } catch (e: Throwable) {
                log.warn("Lore notification stream ended for $root", e)
            } finally {
                flag.set(false)
            }
        }
    }

    /** Runs on Lore's worker thread, so it only queues work. */
    private fun handle(root: Path, event: LoreEvent) {
        when (event) {
            is NotificationBranchPushedEvent -> notifyPushed(event)
            is NotificationResourceLockedEvent, is NotificationResourceUnlockedEvent ->
                LoreLockService.getInstance(project).refresh(root)
            else -> Unit
        }
    }

    private fun notifyPushed(event: NotificationBranchPushedEvent) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Lore")
            .createNotification(
                LoreBundle.message("notify.pushed.title"),
                LoreBundle.message("notify.pushed.message", event.revision_number, event.user_id),
                NotificationType.INFORMATION,
            )
            .notify(project)
    }

    fun stop() {
        running.clear()
    }

    override fun dispose() {
        running.clear()
    }

    companion object {
        fun getInstance(project: Project): LoreNotificationSubscriber = project.service()
    }
}
