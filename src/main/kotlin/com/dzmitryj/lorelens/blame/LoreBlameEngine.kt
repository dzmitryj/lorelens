package com.dzmitryj.lorelens.blame

/**
 * Carries per-line attribution forward across a unified diff.
 *
 * Lore has no blame verb, so attribution is reconstructed from a file's own
 * history. The cost is bounded by how often *that file* changed -- typically a
 * handful of revisions, not the repository's whole history.
 *
 * Pure functions: no IDE, no FFI, so the line arithmetic is unit-testable.
 */
object LoreBlameEngine {

    private val HUNK = Regex("""^@@ -(\d+)(?:,(\d+))? \+(\d+)(?:,(\d+))? @@""")

    data class Hunk(val oldStart: Int, val newStart: Int, val lines: List<String>)

    /**
     * Hunks from a unified diff. Header lines before the first `@@` are skipped,
     * which covers Lore's bare-path line and the `---`/`+++` pair.
     */
    fun parseHunks(patch: String): List<Hunk> {
        val hunks = mutableListOf<Hunk>()
        var oldStart = 0
        var newStart = 0
        var body: MutableList<String>? = null

        patch.lineSequence().forEach { line ->
            val header = HUNK.find(line)
            if (header != null) {
                body?.let { hunks += Hunk(oldStart, newStart, it) }
                oldStart = header.groupValues[1].toInt()
                newStart = header.groupValues[3].toInt()
                body = mutableListOf()
                return@forEach
            }

            val current = body ?: return@forEach
            // A bare path line starts the next file's section in a multi-file patch.
            if (line.isNotEmpty() && line[0] !in " +-\\") {
                hunks += Hunk(oldStart, newStart, current)
                body = null
                return@forEach
            }
            current += line
        }

        body?.let { hunks += Hunk(oldStart, newStart, it) }
        return hunks
    }

    /**
     * Rewrites [previous] -- attribution per line of the older revision -- into
     * attribution for the newer one. Unchanged and context lines keep their
     * origin; added lines take [introduced].
     */
    fun <T> advance(previous: List<T>, patch: String, introduced: T): List<T> {
        val result = mutableListOf<T>()
        var old = 0

        parseHunks(patch).forEach { hunk ->
            // Lines before this hunk are untouched, so they carry over.
            while (result.size < hunk.newStart - 1 && old < previous.size) {
                result += previous[old++]
            }

            hunk.lines.forEach { line ->
                when (line.firstOrNull()) {
                    ' ' -> if (old < previous.size) result += previous[old++] else result += introduced
                    '-' -> old++
                    '+' -> result += introduced
                    else -> Unit // "\ No newline at end of file" and blank padding
                }
            }
        }

        while (old < previous.size) result += previous[old++]
        return result
    }
}
