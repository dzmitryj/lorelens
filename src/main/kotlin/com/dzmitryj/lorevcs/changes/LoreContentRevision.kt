package com.dzmitryj.lorevcs.changes

import com.dzmitryj.lorevcs.api.LoreStatusApi
import com.dzmitryj.lorevcs.model.LoreRevisionId
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vcs.FilePath
import com.intellij.openapi.vcs.VcsException
import com.intellij.openapi.vcs.history.VcsRevisionNumber
import com.intellij.openapi.vcs.changes.ByteBackedContentRevision
import com.intellij.util.containers.ContainerUtil
import java.nio.file.Files
import java.nio.file.Path

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
 * Lore is content-addressed, so identical content across revisions shares a
 * cache entry. A Git-shaped integration has to key on (revision, path) and
 * invalidate conservatively; here the hash is the key.
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

        return try {
            val hash = LoreStatusApi.hash(root, listOf(relativePath)).firstOrNull()?.hash
            hash?.let { cache[it.hex] }?.let { return it }

            val content = fetch()
            hash?.let { cache[it.hex] = content }
            content
        } catch (e: RuntimeException) {
            throw VcsException("Cannot read $relativePath at ${revision.asString()}", e)
        }
    }

    override fun getContent(): String? = getContentAsBytes()?.toString(Charsets.UTF_8)

    private fun fetch(): ByteArray {
        val temp = FileUtil.createTempFile("lore-content", null, true).toPath()
        return try {
            LoreStatusApi.writeFile(root, relativePath, revision.id.hex, temp)
            Files.readAllBytes(temp)
        } finally {
            Files.deleteIfExists(temp)
        }
    }

    private companion object {
        val cache: MutableMap<String, ByteArray> = ContainerUtil.createConcurrentSoftValueMap()
    }
}
