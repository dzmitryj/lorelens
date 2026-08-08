package com.dzmitryj.lorelens.repo

import com.dzmitryj.lorelens.LoreVcs
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.ProjectLevelVcsManager
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap

object LoreRootFinder {

    /**
     * Directory to its Lore root, or to [NONE] when it has none.
     *
     * Root detection stats `<dir>/.lore/instance` for every ancestor, and this
     * runs per file from the diff provider, the blame hint and every menu that
     * asks whether history is available. Uncached it is a stat chain per call
     * on paths that are deep by nature.
     */
    private val roots = ConcurrentHashMap<String, Any>()

    /** Stands in for "no root", because the map cannot hold nulls. */
    private object NoRoot

    /** The nearest ancestor of [file] holding a valid `.lore`, or null. */
    fun findRoot(file: VirtualFile): VirtualFile? {
        val start = if (file.isDirectory) file else file.parent
        return start?.let(::rootOf)
    }

    private fun rootOf(directory: VirtualFile): VirtualFile? {
        if (!directory.isValid) return null

        when (val cached = roots[directory.path]) {
            NoRoot -> return null
            is VirtualFile -> if (cached.isValid) return cached else roots.clear()
        }

        // Walking up rather than recursing keeps the whole ancestor chain warm
        // in one pass, which is what makes the next file in the same directory
        // free.
        val visited = mutableListOf<VirtualFile>()
        var current: VirtualFile? = directory
        var found: VirtualFile? = null

        while (current != null) {
            when (val cached = roots[current.path]) {
                NoRoot -> break
                is VirtualFile -> {
                    if (cached.isValid) found = cached
                    break
                }
            }
            visited += current
            if (isLoreRoot(current)) {
                found = current
                break
            }
            current = current.parent
        }

        visited.forEach { roots[it.path] = found ?: NoRoot }
        return found
    }

    fun clearCache() {
        roots.clear()
    }

    fun rootPath(file: VirtualFile): Path? = findRoot(file)?.toNioPath()

    /** Repository-relative path, which is what every Lore verb expects. */
    fun relativePath(root: VirtualFile, file: VirtualFile): String? =
        VfsUtilCore.getRelativePath(file, root, '/')

    fun mappedRoots(project: Project): List<VirtualFile> {
        val vcs = LoreVcs.of(project) ?: return emptyList()
        return ProjectLevelVcsManager.getInstance(project).getRootsUnderVcs(vcs).toList()
    }
}
