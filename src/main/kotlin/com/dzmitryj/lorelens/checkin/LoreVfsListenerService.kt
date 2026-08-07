package com.dzmitryj.lorelens.checkin

import com.dzmitryj.lorelens.LoreVcs
import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import kotlinx.coroutines.CoroutineScope

@Service(Service.Level.PROJECT)
class LoreVfsListenerService(
    private val project: Project,
    private val scope: CoroutineScope,
) : Disposable {

    private var listener: LoreVFSListener? = null

    fun start() {
        if (listener != null) return
        val vcs = LoreVcs.of(project) ?: return
        listener = LoreVFSListener.create(vcs, scope).also { Disposer.register(this, it) }
    }

    fun stop() {
        listener?.let { Disposer.dispose(it) }
        listener = null
    }

    override fun dispose() {
        listener = null
    }

    companion object {
        fun getInstance(project: Project): LoreVfsListenerService = project.service()
    }
}
