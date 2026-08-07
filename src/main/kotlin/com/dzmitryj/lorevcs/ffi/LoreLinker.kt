package com.dzmitryj.lorevcs.ffi

import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.extensions.PluginId
import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.SymbolLookup
import java.lang.invoke.MethodHandle
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists

class LoreNativeUnavailableException(message: String, cause: Throwable? = null) :
    RuntimeException(message, cause)

object LoreLinker {

    private const val PLUGIN_ID = "com.dzmitryj.LoreVersionControl"

    /** Overrides the search path; set by tests, which run outside a plugin install. */
    const val NATIVE_DIR_PROPERTY = "lore.native.dir"

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

        val root = nativeRoot()
        val path = root.resolve(platform.directory).resolve(platform.library)
        if (!path.exists()) {
            throw LoreNativeUnavailableException("Bundled liblore is missing at $path")
        }
        return path
    }

    private fun nativeRoot(): Path {
        System.getProperty(NATIVE_DIR_PROPERTY)?.let { return Path.of(it) }

        val descriptor = PluginManagerCore.getPlugin(PluginId.getId(PLUGIN_ID))
            ?: throw LoreNativeUnavailableException("Plugin $PLUGIN_ID is not registered")

        val candidate = descriptor.pluginPath.resolve("native")
        if (!Files.isDirectory(candidate)) {
            throw LoreNativeUnavailableException("No native directory in ${descriptor.pluginPath}")
        }
        return candidate
    }
}
