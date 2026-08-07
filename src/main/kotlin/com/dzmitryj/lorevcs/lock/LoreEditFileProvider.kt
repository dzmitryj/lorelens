package com.dzmitryj.lorevcs.lock

import com.dzmitryj.lorevcs.LoreBundle
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.EditFileProvider
import com.intellij.openapi.vcs.VcsException
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile

/**
 * The platform routes here whenever a read-only file under the VCS root is
 * edited, so this is what turns "start typing" into "acquire the lock" -- the
 * Perforce-shaped workflow a studio expects.
 */
class LoreEditFileProvider(private val project: Project) : EditFileProvider {

    override fun getRequestText(): String = LoreBundle.message("lock.request")

    override fun editFiles(files: Array<out VirtualFile>) {
        val service = LoreLockService.getInstance(project)

        val blocked = files.filter { service.stateOf(it) == LockState.LOCKED_BY_OTHER }
        if (blocked.isNotEmpty()) {
            val owners = blocked.joinToString(", ") { file ->
                "${file.name} (${service.lockOf(file)?.owner ?: "unknown"})"
            }
            throw VcsException(LoreBundle.message("lock.held.by.others", owners))
        }

        try {
            service.acquire(files.toList())
        } catch (e: RuntimeException) {
            throw VcsException(e)
        }

        files.forEach { it.isWritable = true }
        VfsUtil.markDirtyAndRefresh(true, false, false, *files)
    }
}
