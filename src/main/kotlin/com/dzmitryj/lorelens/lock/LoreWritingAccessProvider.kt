package com.dzmitryj.lorelens.lock

import com.dzmitryj.lorelens.LoreLensBundle
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.WritingAccessProvider

/**
 * Denies writes to files another user holds. Prevents the most demoralising
 * failure mode in centralized version control: an hour of work on a file that
 * was never going to be committable.
 */
class LoreWritingAccessProvider(private val project: Project) : WritingAccessProvider() {

    /** Returns the files that may *not* be written. */
    override fun requestWriting(files: Collection<VirtualFile>): Collection<VirtualFile> {
        val service = LoreLockService.getInstance(project)
        return files.filter { service.stateOf(it) == LockState.LOCKED_BY_OTHER }
    }

    override fun getReadOnlyMessage(): String = LoreLensBundle.message("lock.readonly.message")
}
