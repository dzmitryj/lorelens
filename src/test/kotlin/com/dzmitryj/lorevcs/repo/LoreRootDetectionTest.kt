package com.dzmitryj.lorevcs.repo

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.nio.file.Files
import kotlin.io.path.createDirectories
import kotlin.io.path.writeText

/**
 * Detection reads the real filesystem rather than the VFS: `findChild` does not
 * refresh, so a `.lore` the VFS has not loaded would silently fail to detect --
 * exactly on the large repositories this plugin exists to serve.
 */
class LoreRootDetectionTest {

    @Test
    fun `a directory holding a lore instance is a root`() {
        val root = Files.createTempDirectory("lore-root")
        root.resolve(".lore").createDirectories().resolve("instance").writeText("id")

        assertTrue(isLoreRoot(root))
    }

    @Test
    fun `a bare lore directory without an instance is not a root`() {
        val root = Files.createTempDirectory("lore-partial")
        root.resolve(".lore").createDirectories()

        assertFalse("a leftover or half-deleted .lore must not count", isLoreRoot(root))
    }

    @Test
    fun `an ordinary directory is not a root`() {
        assertFalse(isLoreRoot(Files.createTempDirectory("plain")))
    }

    @Test
    fun `the instance id is read as hex and is absent without a repository`() {
        val root = Files.createTempDirectory("lore-id")
        root.resolve(".lore").createDirectories().resolve("instance")
            .let { Files.write(it, byteArrayOf(0x01, 0x9f.toByte(), 0x7f)) }

        assertEquals("019f7f", loreInstanceId(root))
        assertNull(loreInstanceId(Files.createTempDirectory("no-repo")))
    }
}
