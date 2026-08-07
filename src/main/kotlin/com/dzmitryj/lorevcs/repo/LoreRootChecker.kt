package com.dzmitryj.lorevcs.repo

import com.dzmitryj.lorevcs.LoreVcs
import com.intellij.openapi.vcs.VcsKey
import com.intellij.openapi.vcs.VcsRootChecker
import com.intellij.openapi.vfs.VirtualFile

const val LORE_DIRECTORY = ".lore"

/**
 * Validates on `.lore/instance` rather than the directory alone. That file holds
 * the UUIDv7 identifying this working directory, so its presence distinguishes a
 * real checkout from a leftover or half-deleted `.lore`.
 */
fun isLoreRoot(file: VirtualFile): Boolean {
    if (!file.isDirectory) return false
    val administrative = file.findChild(LORE_DIRECTORY) ?: return false
    return administrative.isDirectory && administrative.findChild("instance") != null
}

class LoreRootChecker : VcsRootChecker() {

    override fun getSupportedVcs(): VcsKey = LoreVcs.KEY

    override fun isVcsDir(dirName: String): Boolean = dirName.equals(LORE_DIRECTORY, ignoreCase = true)

    override fun isRoot(file: VirtualFile): Boolean = isLoreRoot(file)
}
