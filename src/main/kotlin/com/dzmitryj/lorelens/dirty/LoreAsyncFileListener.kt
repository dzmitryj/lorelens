package com.dzmitryj.lorelens.dirty

import com.dzmitryj.lorelens.repo.LORE_DIRECTORY
import com.dzmitryj.lorelens.repo.LoreRootFinder
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.vfs.AsyncFileListener
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.newvfs.events.VFileContentChangeEvent
import com.intellij.openapi.vfs.newvfs.events.VFileCopyEvent
import com.intellij.openapi.vfs.newvfs.events.VFileCreateEvent
import com.intellij.openapi.vfs.newvfs.events.VFileDeleteEvent
import com.intellij.openapi.vfs.newvfs.events.VFileEvent
import com.intellij.openapi.vfs.newvfs.events.VFileMoveEvent
import com.intellij.openapi.vfs.newvfs.events.VFilePropertyChangeEvent

/**
 * AsyncFileListener rather than BulkFileListener because prepareChange runs off
 * the EDT. A content refresh can deliver tens of thousands of events, and doing
 * that work on the EDT is a freeze.
 */
class LoreAsyncFileListener : AsyncFileListener {

    override fun prepareChange(events: List<VFileEvent>): AsyncFileListener.ChangeApplier? {
        if (!LoreDirtySettings.getInstance().markEditsDirty) return null

        val projects = ProjectManager.getInstance().openProjects.filterNot { it.isDisposed }
        if (projects.isEmpty()) return null

        val touched = events.mapNotNull(::fileOf).filter(::isTracked)
        if (touched.isEmpty()) return null

        return object : AsyncFileListener.ChangeApplier {
            override fun afterVfsChange() {
                projects.forEach { project ->
                    LoreDirtyMarkQueue.getInstance(project).enqueue(touched)
                }
            }
        }
    }

    private fun fileOf(event: VFileEvent): VirtualFile? = when (event) {
        is VFileContentChangeEvent -> event.file
        is VFileCreateEvent -> event.file
        is VFileDeleteEvent -> event.file
        is VFileMoveEvent -> event.file
        is VFileCopyEvent -> event.file
        is VFilePropertyChangeEvent -> event.file
        else -> event.file
    }

    private fun isTracked(file: VirtualFile): Boolean {
        if (file.isDirectory) return false
        if (file.path.contains("/$LORE_DIRECTORY/")) return false
        return LoreRootFinder.findRoot(file) != null
    }
}
