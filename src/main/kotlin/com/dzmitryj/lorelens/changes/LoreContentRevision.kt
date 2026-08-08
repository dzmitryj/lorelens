package com.dzmitryj.lorelens.changes

import com.dzmitryj.lorelens.api.LoreStatusApi
import com.dzmitryj.lorelens.model.LoreRevisionId
import com.intellij.openapi.vcs.FilePath
import com.intellij.openapi.vcs.VcsException
import com.intellij.openapi.vcs.history.VcsRevisionNumber
import com.intellij.openapi.vcs.changes.ByteBackedContentRevision
import com.intellij.util.containers.ContainerUtil
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.atomic.AtomicLong

data class LoreRevisionNumber(val id: LoreRevisionId, val number: Long) : VcsRevisionNumber {

    override fun asString(): String = id.short

    override fun compareTo(other: VcsRevisionNumber?): Int = when (other) {
        is LoreRevisionNumber -> number.compareTo(other.number)
        else -> 0
    }
}

/**
 * Content is fetched by writing it out to a temp file, because lore_file_write
 * is the verb that yields bytes -- lore_file_dump only reports sizes.
 *
 * Revisions are immutable, so a cache entry never needs invalidating.
 */
class LoreContentRevision(
    private val root: Path,
    private val filePath: FilePath,
    private val relativePath: String,
    private val revision: LoreRevisionNumber,
) : ByteBackedContentRevision {

    override fun getFile(): FilePath = filePath

    override fun getRevisionNumber(): VcsRevisionNumber = revision

    override fun getContentAsBytes(): ByteArray? {
        if (revision.id.isNone) return null

        val key = Key(root, relativePath, revision.id.hex)
        cache[key]?.let { return it }

        return try {
            fetch().also { cache[key] = it }
        } catch (e: RuntimeException) {
            throw VcsException("Cannot read $relativePath at ${revision.asString()}", e)
        }
    }

    override fun getContent(): String? = getContentAsBytes()?.toString(Charsets.UTF_8)

    /**
     * lore_file_write refuses to overwrite an existing output file, so the
     * destination must be a path that does not exist yet -- a temp *directory*
     * with a name inside it, never a temp file.
     */
    private fun fetch(): ByteArray {
        val temp = scratch.resolve("content-${counter.incrementAndGet()}")
        return try {
            LoreStatusApi.writeFile(root, relativePath, revision.id.hex, temp)
            Files.readAllBytes(temp)
        } finally {
            Files.deleteIfExists(temp)
        }
    }

    private data class Key(val root: Path, val path: String, val revision: String)

    private companion object {
        val cache: MutableMap<Key, ByteArray> = ContainerUtil.createConcurrentSoftValueMap()

        /** One directory for the session; a fetch only needs a name inside it. */
        val scratch: Path by lazy { Files.createTempDirectory("lorelens-content") }
        val counter = AtomicLong()
    }
}
