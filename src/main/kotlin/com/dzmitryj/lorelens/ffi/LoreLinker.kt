package com.dzmitryj.lorelens.ffi

import com.intellij.openapi.diagnostic.logger
import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.SymbolLookup
import java.lang.invoke.MethodHandle
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists

class LoreNativeUnavailableException(message: String, cause: Throwable? = null) :
    RuntimeException(message, cause)

object LoreLinker {

    /** Overrides the search path; set by tests, which run outside a plugin install. */
    const val NATIVE_DIR_PROPERTY = "lore.native.dir"

    /** A resource only this plugin ships, so no other jar can answer first. */
    private const val MARKER_RESOURCE = "/messages/LoreLensBundle.properties"

    private val log = logger<LoreLinker>()
    private val linker: Linker = Linker.nativeLinker()

    private val lookup: SymbolLookup by lazy { openLibrary() }

    val libraryPath: Path by lazy { resolveLibrary() }

    fun downcall(name: String, descriptor: FunctionDescriptor): MethodHandle {
        val symbol = lookup.find(name).orElseThrow {
            LoreNativeUnavailableException("liblore at $libraryPath does not export '$name'")
        }
        return linker.downcallHandle(symbol, descriptor)
    }

    fun upcall(target: MethodHandle, descriptor: FunctionDescriptor, arena: Arena) =
        linker.upcallStub(target, descriptor, arena)

    private fun openLibrary(): SymbolLookup {
        val path = libraryPath
        log.info("Loading liblore from $path")
        return try {
            SymbolLookup.libraryLookup(path, Arena.global())
        } catch (e: IllegalArgumentException) {
            throw LoreNativeUnavailableException("Failed to load liblore from $path", e)
        }
    }

    private fun resolveLibrary(): Path {
        val platform = NativePlatform.currentOrNull()
            ?: throw LoreNativeUnavailableException(
                "Lore does not ship a native library for ${NativePlatform.describeCurrent()}"
            )

        val path = nativeRoot().resolve(platform.directory).resolve(platform.library)
        if (!path.exists()) {
            throw LoreNativeUnavailableException("Bundled liblore is missing at $path")
        }
        return path
    }

    /**
     * Locates <plugin>/native from the URL of a resource in this plugin's own
     * jar, which is `<plugin>/lib/x.jar`.
     *
     * Neither of the direct routes is usable: protectionDomain.codeSource
     * .location is null under the plugin classloader, and both
     * PluginManagerCore.getPlugin and PluginAwareClassLoader are marked
     * ApiStatus.Internal.
     */
    private fun nativeRoot(): Path {
        System.getProperty(NATIVE_DIR_PROPERTY)?.let { return Path.of(it) }

        val pluginRoot = pluginRootFromResource()
            ?: throw LoreNativeUnavailableException("Cannot locate the plugin directory")

        val candidate = pluginRoot.resolve("native")
        if (!Files.isDirectory(candidate)) {
            throw LoreNativeUnavailableException("No native directory in $pluginRoot")
        }
        return candidate
    }

    private fun pluginRootFromResource(): Path? =
        pluginRootOf(LoreLinker::class.java.getResource(MARKER_RESOURCE)?.toString())

    /** `jar:file:/…/plugins/<plugin>/lib/x.jar!/<resource>` -> `…/<plugin>`. */
    internal fun pluginRootOf(resourceUrl: String?): Path? {
        if (resourceUrl == null || !resourceUrl.startsWith("jar:")) return null

        val jar = runCatching {
            Path.of(URI.create(resourceUrl.removePrefix("jar:").substringBefore("!/")))
        }.getOrNull() ?: return null

        return jar.parent?.parent
    }
}
