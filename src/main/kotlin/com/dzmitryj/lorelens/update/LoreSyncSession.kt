package com.dzmitryj.lorelens.update

import com.dzmitryj.lorelens.api.LoreStatusApi
import com.dzmitryj.lorelens.api.LoreSyncApi
import com.dzmitryj.lorelens.api.LoreWriteApi
import com.dzmitryj.lorelens.ffi.generated.LoreEvent
import com.dzmitryj.lorelens.model.LoreFileStatus
import com.dzmitryj.lorelens.model.LoreNodeType
import com.intellij.openapi.diagnostic.logger
import java.nio.file.Files
import java.nio.file.Path

/**
 * Sync cannot run over a staged set, and Lore says so itself: a commit against a
 * moved branch fails with BRANCH_ADVANCED, "sync and re-stage to commit". This
 * performs that cycle so the staging a user built up survives an update.
 */
object LoreSyncSession {

    private val log = logger<LoreSyncSession>()

    /**
     * What to re-stage after a sync. Paths still on disk go back in one batch;
     * paths that are gone are staged deletions and are attempted separately,
     * because one bad path fails a whole stage call.
     */
    data class RestagePlan(val present: List<String>, val absent: List<String>) {
        val isEmpty: Boolean get() = present.isEmpty() && absent.isEmpty()
    }

    data class Outcome(val restaged: List<String>, val failed: List<String>)

    fun restagePlan(files: List<LoreFileStatus>, exists: (String) -> Boolean): RestagePlan {
        val staged = files
            .filter { it.staged && it.nodeType != LoreNodeType.DIRECTORY }
            .map { it.path }
            .distinct()

        val (present, absent) = staged.partition(exists)
        return RestagePlan(present, absent)
    }

    /** Unstages, syncs, then puts the staged set back. */
    fun sync(root: Path, revision: String = "", observer: ((LoreEvent) -> Unit)? = null): Outcome {
        val plan = restagePlan(LoreStatusApi.status(root).files) { Files.exists(root.resolve(it)) }

        if (!plan.isEmpty) {
            LoreWriteApi.unstage(root, plan.present + plan.absent)
        }

        LoreSyncApi.sync(root, revision, observer)

        if (plan.isEmpty) return Outcome(emptyList(), emptyList())

        val restaged = mutableListOf<String>()
        val failed = mutableListOf<String>()

        restage(root, plan.present, restaged, failed)
        // Staged deletions, one at a time: a path Lore no longer accepts would
        // otherwise take the whole batch down with it.
        plan.absent.forEach { restage(root, listOf(it), restaged, failed) }

        return Outcome(restaged, failed)
    }

    private fun restage(
        root: Path,
        paths: List<String>,
        restaged: MutableList<String>,
        failed: MutableList<String>,
    ) {
        if (paths.isEmpty()) return
        try {
            LoreWriteApi.stage(root, paths)
            restaged += paths
        } catch (e: RuntimeException) {
            log.warn("Cannot re-stage $paths in $root after sync", e)
            failed += paths
        }
    }
}
