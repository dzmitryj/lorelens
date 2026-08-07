package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.changes.LoreRevisionNumber
import com.intellij.diff.util.DiffUserDataKeysEx
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.vcs.changes.Change
import com.intellij.openapi.vcs.changes.ContentRevision
import com.intellij.openapi.vcs.changes.actions.diff.ChangeDiffRequestProducer
import com.intellij.openapi.vcs.changes.ui.ChangeDiffRequestChain
import com.intellij.openapi.vcs.changes.ui.SimpleAsyncChangesBrowser

/**
 * Without a context the diff sides are titled with bare short hashes, which say
 * nothing about ordering. The producer's own title getters are internal, so the
 * titles go in through the context map instead.
 */
class LoreChangesBrowser(private val project: Project) :
    SimpleAsyncChangesBrowser(project, false, false) {

    override fun getDiffRequestProducer(userObject: Any): ChangeDiffRequestChain.Producer? {
        val change = userObject as? Change ?: return super.getDiffRequestProducer(userObject)
        return ChangeDiffRequestProducer.create(project, change, titles(change))
            ?: super.getDiffRequestProducer(userObject)
    }

    private fun titles(change: Change): Map<Key<*>, Any> {
        val left = label(change.beforeRevision)
        val right = label(change.afterRevision)
        val name = (change.afterRevision ?: change.beforeRevision)?.file?.name

        return buildMap {
            left?.let { put(DiffUserDataKeysEx.VCS_DIFF_LEFT_CONTENT_TITLE, it) }
            right?.let { put(DiffUserDataKeysEx.VCS_DIFF_RIGHT_CONTENT_TITLE, it) }
            if (name != null && (left != null || right != null)) {
                put(
                    DiffUserDataKeysEx.VCS_DIFF_EDITOR_TAB_TITLE,
                    "$name (${left ?: NONE} → ${right ?: NONE})",
                )
            }
        }
    }

    private fun label(revision: ContentRevision?): String? =
        (revision?.revisionNumber as? LoreRevisionNumber)
            ?.let { "r${it.number} (${it.id.short})" }

    private companion object {
        const val NONE = "—"
    }
}
