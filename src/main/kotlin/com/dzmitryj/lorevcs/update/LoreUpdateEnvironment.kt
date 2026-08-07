package com.dzmitryj.lorevcs.update

import com.dzmitryj.lorevcs.LoreBundle
import com.dzmitryj.lorevcs.api.LoreSyncApi
import com.dzmitryj.lorevcs.checkin.LorePaths
import com.dzmitryj.lorevcs.ffi.generated.RevisionSyncFileEvent
import com.dzmitryj.lorevcs.ffi.generated.RevisionSyncProgressEvent
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.util.Ref
import com.intellij.openapi.vcs.FilePath
import com.intellij.openapi.vcs.VcsException
import com.intellij.openapi.vcs.update.FileGroup
import com.intellij.openapi.vcs.update.SequentialUpdatesContext
import com.intellij.openapi.vcs.update.UpdateEnvironment
import com.intellij.openapi.vcs.update.UpdateSession
import com.intellij.openapi.vcs.update.UpdateSessionAdapter
import com.intellij.openapi.vcs.update.UpdatedFiles

class LoreUpdateEnvironment : UpdateEnvironment {

    override fun fillGroups(updatedFiles: UpdatedFiles?) = Unit

    override fun updateDirectories(
        contentRoots: Array<out FilePath>,
        updatedFiles: UpdatedFiles,
        progressIndicator: ProgressIndicator,
        context: Ref<SequentialUpdatesContext>,
    ): UpdateSession {
        val exceptions = mutableListOf<VcsException>()

        contentRoots.mapNotNull { LorePaths.rootOf(it.path) }.distinct().forEach { root ->
            progressIndicator.checkCanceled()
            progressIndicator.text = LoreBundle.message("update.progress", root.fileName ?: root)

            try {
                LoreSyncApi.sync(root) { event ->
                    when (event) {
                        is RevisionSyncProgressEvent -> report(progressIndicator, event)
                        is RevisionSyncFileEvent ->
                            updatedFiles.getGroupById(FileGroup.UPDATED_ID)
                                ?.add(root.resolve(event.path).toString(), com.dzmitryj.lorevcs.LoreVcs.KEY, null)
                        else -> Unit
                    }
                }
            } catch (e: RuntimeException) {
                exceptions += VcsException(e)
            }
        }

        return UpdateSessionAdapter(exceptions, false)
    }

    /** Runs on Lore's worker thread, so it only touches the indicator. */
    private fun report(indicator: ProgressIndicator, event: RevisionSyncProgressEvent) {
        val total = event.file_update_total + event.file_delete_total
        if (total > 0) {
            indicator.isIndeterminate = false
            indicator.fraction = (event.file_update + event.file_delete).toDouble() / total
        }
        indicator.text2 = LoreBundle.message(
            "update.progress.files",
            event.file_update + event.file_delete,
            total,
        )
    }

    override fun validateOptions(roots: Collection<FilePath>): Boolean = true

    override fun createConfigurable(files: Collection<FilePath>) = null
}
