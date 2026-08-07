package com.dzmitryj.lorelens.ffi

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.net.URLClassLoader
import java.nio.file.Files
import java.util.jar.JarEntry
import java.util.jar.JarOutputStream
import kotlin.io.path.createDirectories

/**
 * The plugin directory is found from a resource URL because the direct routes
 * are unusable: protectionDomain.codeSource.location is null under the plugin
 * classloader, and PluginManagerCore.getPlugin and PluginAwareClassLoader are
 * both ApiStatus.Internal. Shipping that lookup untested once already left the
 * native library unloadable, so it is exercised against a real jar here.
 */
class PluginRootTest {

    @Test
    fun `resolves the plugin directory from a jar resource url`() {
        val plugins = Files.createTempDirectory("plugins")
        val pluginDir = plugins.resolve("LoreLens")
        val lib = pluginDir.resolve("lib").also { it.createDirectories() }
        pluginDir.resolve("native").createDirectories()

        val jar = lib.resolve("LoreLens.jar")
        JarOutputStream(Files.newOutputStream(jar)).use { out ->
            out.putNextEntry(JarEntry("messages/LoreLensBundle.properties"))
            out.write("vcs.name=Lore\n".toByteArray())
            out.closeEntry()
        }

        URLClassLoader(arrayOf(jar.toUri().toURL()), null).use { loader ->
            val url = loader.getResource("messages/LoreLensBundle.properties")?.toString()

            assertEquals(pluginDir, LoreLinker.pluginRootOf(url))
        }
    }

    @Test
    fun `a non jar url yields no plugin directory`() {
        assertNull(LoreLinker.pluginRootOf("file:/tmp/classes/messages/LoreLensBundle.properties"))
        assertNull(LoreLinker.pluginRootOf(null))
        assertNull(LoreLinker.pluginRootOf("jar:nonsense"))
    }
}
