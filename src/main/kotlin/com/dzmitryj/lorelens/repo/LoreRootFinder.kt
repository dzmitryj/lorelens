package com.dzmitryj.lorelens.repo

import com.dzmitryj.lorelens.LoreVcs
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.ProjectLevelVcsManager
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import java.nio.file.Path

object LoreRootFinder {

    /** The nearest ancestor of [file] holding a valid `.lore`, or null. */
    fun findRoot(file: VirtualFile): VirtualFile? {
        var current: VirtualFile? = if (file.isDirectory) file else file.parent
        while (current != null) {
            if (isLoreRoot(current)) return current
            current = current.parent
        }
        return null
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
