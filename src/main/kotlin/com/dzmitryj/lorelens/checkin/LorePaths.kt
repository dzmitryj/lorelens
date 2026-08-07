package com.dzmitryj.lorelens.checkin

import com.intellij.openapi.vcs.FilePath
import com.intellij.openapi.vcs.changes.Change
import com.intellij.openapi.vfs.VirtualFile
import java.io.File
import java.nio.file.Path

/**
 * Groups paths by the Lore root that contains them. Resolution walks the real
 * filesystem rather than the VFS, because deletions arrive after the
 * VirtualFile is gone.
 */
object LorePaths {

    fun groupChanges(changes: List<Change>): Map<Path, List<String>> =
        group(changes.mapNotNull { (it.afterRevision?.file ?: it.beforeRevision?.file)?.path })

    fun groupFilePaths(paths: List<FilePath>): Map<Path, List<String>> =
        group(paths.map { it.path })

    fun groupVirtualFiles(files: List<VirtualFile>): Map<Path, List<String>> =
        group(files.map { it.path })

    fun group(paths: List<String>): Map<Path, List<String>> {
        val grouped = mutableMapOf<Path, MutableList<String>>()
        paths.forEach { path ->
            val root = rootOf(path) ?: return@forEach
            val relative = relativize(root, path) ?: return@forEach
            grouped.computeIfAbsent(root) { mutableListOf() } += relative
        }
        return grouped
    }

    fun rootOf(path: String): Path? {
        var current: File? = File(path)
        while (current != null) {
            if (File(current, ".lore/instance").exists()) return current.toPath()
            current = current.parentFile
        }
        return null
    }

    fun relativize(root: Path, path: String): String? =
        runCatching { root.relativize(Path.of(path)).toString().replace('\\', '/') }
            .getOrNull()
            ?.takeIf { it.isNotEmpty() && !it.startsWith("..") }
}
