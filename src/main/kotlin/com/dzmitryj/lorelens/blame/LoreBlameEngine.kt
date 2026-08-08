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

    data class Hunk(
        val oldStart: Int,
        val oldCount: Int,
        val newStart: Int,
        val newCount: Int,
        val lines: List<String>,
    ) {
        /**
         * How many old lines precede this hunk. A range of zero lines is
         * numbered by the line *before* it, so it anchors one later than a
         * range that actually contains lines.
         */
        val oldAnchor: Int get() = if (oldCount == 0) oldStart else oldStart - 1
    }

    /**
     * Hunks from a unified diff. Header lines before the first `@@` are skipped,
     * which covers Lore's bare-path line and the `---`/`+++` pair.
     */
    fun parseHunks(patch: String): List<Hunk> {
        val hunks = mutableListOf<Hunk>()
        var oldStart = 0
        var oldCount = 0
        var newStart = 0
        var newCount = 0
        var body: MutableList<String>? = null

        fun close(lines: List<String>) {
            hunks += Hunk(oldStart, oldCount, newStart, newCount, lines)
        }

        patch.lineSequence().forEach { line ->
            val header = HUNK.find(line)
            if (header != null) {
                body?.let(::close)
                oldStart = header.groupValues[1].toInt()
                oldCount = header.groupValues[2].toIntOrNull() ?: 1
                newStart = header.groupValues[3].toInt()
                newCount = header.groupValues[4].toIntOrNull() ?: 1
                body = mutableListOf()
                return@forEach
            }

            val current = body ?: return@forEach
            // A bare path line starts the next file's section in a multi-file patch.
            if (line.isNotEmpty() && line[0] !in " +-\\") {
                close(current)
                body = null
                return@forEach
            }
            current += line
        }

        body?.let(::close)
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
            // Lines before this hunk are untouched, so they carry over. The
            // anchor is taken from the old side, which is what `previous` is
            // indexed by; driving it from the new side desynchronises the two
            // as soon as a hunk adds or removes a different number of lines.
            while (old < hunk.oldAnchor && old < previous.size) {
                result += previous[old++]
            }

            var index = 0
            while (index < hunk.lines.size) {
                val line = hunk.lines[index]
                when (line.firstOrNull()) {
                    ' ' -> {
                        result += if (old < previous.size) previous[old++] else introduced
                        index++
                    }

                    '-', '+' -> {
                        val removed = hunk.lines.subList(index, hunk.lines.size)
                            .takeWhile { it.startsWith('-') }
                        val added = hunk.lines.subList(index + removed.size, hunk.lines.size)
                            .takeWhile { it.startsWith('+') }

                        emitBlock(result, previous, old, removed, added, introduced)
                        old += removed.size
                        index += removed.size + added.size
                    }

                    else -> index++ // "\ No newline at end of file" and blank padding
                }
            }
        }

        while (old < previous.size) result += previous[old++]
        return result
    }

    /**
     * Lore's diff is not minimal: it emits blocks that delete lines and re-add
     * them byte for byte. Taking that literally hands every such line to the
     * revision being advanced to, which is how a copyright header ends up
     * attributed to a commit that never touched it. Matching the two sides by
     * content instead keeps unchanged lines on their original revision.
     */
    private fun <T> emitBlock(
        result: MutableList<T>,
        previous: List<T>,
        old: Int,
        removed: List<String>,
        added: List<String>,
        introduced: T,
    ) {
        val matches = if (removed.size * added.size <= MAX_ALIGNMENT_CELLS) {
            commonSubsequence(removed.map(::text), added.map(::text))
        } else {
            emptyMap()
        }

        added.indices.forEach { index ->
            val origin = matches[index]?.let { previous.getOrNull(old + it) }
            result += origin ?: introduced
        }
    }

    /** Line ending and trailing whitespace churn must not steal authorship. */
    private fun text(line: String): String = line.drop(1).trimEnd()

    /**
     * Longest common subsequence, as a map from index in [added] to the index in
     * [removed] it pairs with. Blocks are a hunk's changed region rather than a
     * whole file, so the quadratic table is small.
     */
    private fun commonSubsequence(removed: List<String>, added: List<String>): Map<Int, Int> {
        val lengths = Array(removed.size + 1) { IntArray(added.size + 1) }
        for (r in removed.indices.reversed()) {
            for (a in added.indices.reversed()) {
                lengths[r][a] = if (removed[r] == added[a]) {
                    lengths[r + 1][a + 1] + 1
                } else {
                    maxOf(lengths[r + 1][a], lengths[r][a + 1])
                }
            }
        }

        val pairs = mutableMapOf<Int, Int>()
        var r = 0
        var a = 0
        while (r < removed.size && a < added.size) {
            when {
                removed[r] == added[a] -> pairs[a++] = r++
                lengths[r + 1][a] >= lengths[r][a + 1] -> r++
                else -> a++
            }
        }
        return pairs
    }

    /** Beyond this a block falls back to "everything here is new". */
    private const val MAX_ALIGNMENT_CELLS = 500 * 500
}
