package com.dzmitryj.lorelens.blame

import com.dzmitryj.lorelens.api.LoreDiffApi
import com.dzmitryj.lorelens.api.LoreStatusApi
import com.dzmitryj.lorelens.model.LoreHistoryRecord
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.project.Project
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap

/** Per-line attribution for one file. Index 0 is line 1. */
class LoreBlame(val lines: List<LoreHistoryRecord>) {

    fun at(lineNumber: Int): LoreHistoryRecord? = lines.getOrNull(lineNumber)

    val isEmpty: Boolean get() = lines.isEmpty()
}

/**
 * Reconstructs blame from a file's own history, because Lore exposes no blame
 * verb. Cost is bounded by how often that file changed, not by the size of the
 * repository -- a handful of revisions for a typical source file.
 *
 * Results are cached on the head revision. Revisions are immutable, so an entry
 * never needs invalidating; it is simply superseded when the head moves.
 */
@Service(Service.Level.PROJECT)
class LoreBlameService(private val project: Project) {

    private val log = logger<LoreBlameService>()
    private val cache = ConcurrentHashMap<Key, LoreBlame>()

    private data class Key(val root: Path, val path: String, val head: String)

    /** Runs on a background thread; honours cancellation. */
    fun blame(root: Path, relativePath: String): LoreBlame? {
        val history = runCatching { LoreDiffApi.fileHistory(root, relativePath, MAX_REVISIONS) }
            .onFailure { log.warn("Cannot read history for $relativePath", it) }
            .getOrNull()
            ?.takeIf { it.isNotEmpty() }
            ?: return null

        val key = Key(root, relativePath, history.first().revision.hex)
        cache[key]?.let { return it }

        return runCatching { compute(root, relativePath, history) }
            .onFailure { log.warn("Cannot blame $relativePath", it) }
            .getOrNull()
            ?.also { cache[key] = it }
    }

    /**
     * History arrives newest first. Attribution starts from the oldest revision
     * and is carried forward one diff at a time.
     */
    private fun compute(
        root: Path,
        relativePath: String,
        history: List<LoreHistoryRecord>,
    ): LoreBlame {
        val ordered = history.reversed()
        val oldest = ordered.first()

        var attribution: List<LoreHistoryRecord> = List(lineCount(root, relativePath, oldest)) { oldest }

        ordered.zipWithNext().forEach { (previous, next) ->
            ProgressManager.checkCanceled()

            // Identical content addresses mean identical bytes, so the diff is
            // provably empty and the call can be skipped outright.
            if (previous.address == next.address) return@forEach

            val patch = LoreDiffApi
                .fileDiff(root, listOf(relativePath), previous.revision.hex, next.revision.hex, contextLines = 0)
                .firstOrNull()
                ?.patch
                ?: return@forEach

            attribution = LoreBlameEngine.advance(attribution, patch, next)
        }

        return LoreBlame(attribution)
    }

    /** Line count of the file as it stood at [record]. */
    private fun lineCount(root: Path, relativePath: String, record: LoreHistoryRecord): Int {
        val directory = Files.createTempDirectory("lorelens-blame")
        val output = directory.resolve("content")
        return try {
            LoreStatusApi.writeFile(root, relativePath, record.revision.hex, output)
            Files.readAllLines(output).size
        } catch (e: RuntimeException) {
            log.warn("Cannot read $relativePath at ${record.revision.short}", e)
            0
        } finally {
            Files.deleteIfExists(output)
            Files.deleteIfExists(directory)
        }
    }

    companion object {
        /**
         * A pathological file degrades to "attributed as far back as this"
         * rather than hanging.
         */
        const val MAX_REVISIONS = 100

        fun getInstance(project: Project): LoreBlameService = project.service()
    }
}
