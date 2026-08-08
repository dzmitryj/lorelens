package com.dzmitryj.lorelens

import com.dzmitryj.lorelens.api.LoreDiffApi
import com.dzmitryj.lorelens.api.LoreHistoryApi
import com.dzmitryj.lorelens.api.LoreStatusApi
import com.dzmitryj.lorelens.blame.LoreBlameEngine
import com.dzmitryj.lorelens.blame.LoreBlameService
import com.dzmitryj.lorelens.model.LoreHistoryRecord
import org.junit.Assert.assertEquals
import org.junit.Assume.assumeTrue
import org.junit.Test
import java.nio.file.Files
import java.nio.file.Path

/**
 * Validates blame against a real repository, which synthetic fixtures cannot do:
 * the bugs it has caught came from history shapes only a real project produces.
 * Read only, and skipped unless -Dlore.probe.repo names a Lore working directory.
 */
class LoreRealRepoProbeTest {

    private val root: Path? = System.getProperty("lore.probe.repo")?.let(Path::of)

    private fun repository(): Path {
        val repository = root
        assumeTrue("no -Dlore.probe.repo", repository != null && Files.isDirectory(repository.resolve(".lore")))
        return repository!!
    }

    /**
     * Attribution must stay exactly as long as the file at every step. A single
     * mis-parsed hunk header desynchronises the two and every line after it
     * lands on its neighbour's revision.
     */
    @Test
    fun `blame tracks the line count of every revision`() {
        val repository = repository()
        val relative = System.getProperty("lore.probe.file") ?: pickFile(repository)
        println("PROBE file=$relative")

        val history = LoreDiffApi.fileHistory(repository, relative, LoreBlameService.MAX_REVISIONS)
        assumeTrue("file has no history", history.size >= 2)

        val ordered = history.reversed()
        val oldest = ordered.first()
        var attribution: List<LoreHistoryRecord> = List(contentLines(repository, relative, oldest).size) { oldest }

        ordered.zipWithNext().forEach { (previous, next) ->
            if (previous.address == next.address) return@forEach
            val patch = LoreDiffApi
                .fileDiff(repository, listOf(relative), previous.revision.hex, next.revision.hex, contextLines = 0)
                .firstOrNull()?.patch ?: return@forEach

            attribution = LoreBlameEngine.advance(attribution, patch, next)
            assertEquals(
                "line count after n${next.number}",
                contentLines(repository, relative, next).size,
                attribution.size,
            )
        }

        println("PROBE attribution by revision: ${attribution.groupingBy { it.number }.eachCount().toSortedMap()}")
        dump(repository, relative, history.first(), attribution)
    }

    @Test
    fun `file history arrives newest first`() {
        val repository = repository()

        val files = LoreHistoryApi.history(repository, 40).zipWithNext()
            .flatMap { (newer, older) ->
                LoreDiffApi.revisionDiff(repository, older.revision.hex, newer.revision.hex).map { it.path }
            }
            .distinct()

        files.forEach { path ->
            val numbers = runCatching { LoreDiffApi.fileHistory(repository, path, 100) }
                .getOrDefault(emptyList())
                .map { it.number }
            if (numbers.size < 2) return@forEach
            assertEquals("history order for $path", numbers.sortedDescending(), numbers)
        }
        println("PROBE ordering checked=${files.size}")
    }

    /** The source file with the longest history among recent revisions' changes. */
    private fun pickFile(repository: Path): String =
        LoreHistoryApi.history(repository, 12).zipWithNext()
            .flatMap { (newer, older) ->
                LoreDiffApi.revisionDiff(repository, older.revision.hex, newer.revision.hex).map { it.path }
            }
            .filter { it.endsWith(".cpp") || it.endsWith(".h") }
            .distinct()
            .maxByOrNull { runCatching { LoreDiffApi.fileHistory(repository, it, 100).size }.getOrDefault(0) }
            ?: error("no source file changed recently")

    private fun dump(
        repository: Path,
        relative: String,
        head: LoreHistoryRecord,
        attribution: List<LoreHistoryRecord>,
    ) {
        val text = contentLines(repository, relative, head)
        val output = Path.of(System.getProperty("java.io.tmpdir"), "lore-blame-dump.txt")
        Files.write(
            output,
            attribution.mapIndexed { index, record ->
                "%4d n%-4d %-24s %s".format(
                    index + 1,
                    record.number,
                    record.author?.substringBefore('@') ?: "unknown",
                    text.getOrNull(index).orEmpty(),
                )
            },
        )
        println("PROBE dump=$output")
    }

    private fun contentLines(repository: Path, relative: String, record: LoreHistoryRecord): List<String> {
        val directory = Files.createTempDirectory("lore-probe")
        val output = directory.resolve("content")
        return try {
            LoreStatusApi.writeFile(repository, relative, record.revision.hex, output)
            Files.readAllLines(output)
        } finally {
            Files.deleteIfExists(output)
            Files.deleteIfExists(directory)
        }
    }
}
