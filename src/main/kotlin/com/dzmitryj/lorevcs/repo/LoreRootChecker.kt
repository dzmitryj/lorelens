package com.dzmitryj.lorevcs.repo

import com.dzmitryj.lorevcs.LoreVcs
import com.intellij.openapi.vcs.VcsKey
import com.intellij.openapi.vcs.VcsRootChecker
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import java.nio.file.Files
import java.nio.file.Path

const val LORE_DIRECTORY = ".lore"

/**
 * Validates on `.lore/instance` rather than the directory alone. That file holds
 * the UUIDv7 identifying this working directory, so its presence distinguishes a
 * real checkout from a leftover or half-deleted `.lore`.
 *
 * Checked on disk rather than through the VFS: on a large repository `.lore` may
 * not have been loaded yet, and `findChild` does not refresh, so detection would
 * silently fail for exactly the repositories this plugin exists to serve.
 */
fun isLoreRoot(file: VirtualFile): Boolean {
    // The whole body is guarded: this runs from the VFS listener, where a file
    // can be invalidated between the event and this call. Touching a stale
    // VirtualFile throws InvalidVirtualFileAccessException.
    if (!file.isValid) return false

    val path = runCatching {
        if (!file.isDirectory || file.fileSystem !is LocalFileSystem) return false
        file.toNioPath()
    }.getOrNull() ?: return false

    return isLoreRoot(path)
}

fun isLoreRoot(path: Path): Boolean = Files.isRegularFile(instanceFile(path))

fun instanceFile(root: Path): Path = root.resolve(LORE_DIRECTORY).resolve("instance")

/**
 * The UUIDv7 identifying this working directory. Used as repository identity so
 * that a re-clone is treated as new while a moved directory is not.
 */
fun loreInstanceId(root: Path): String? =
    runCatching { Files.readAllBytes(instanceFile(root)) }
        .getOrNull()
        ?.takeIf { it.isNotEmpty() }
        ?.joinToString("") { "%02x".format(it) }

class LoreRootChecker : VcsRootChecker() {

    override fun getSupportedVcs(): VcsKey = LoreVcs.KEY

    override fun isVcsDir(dirName: String): Boolean = dirName.equals(LORE_DIRECTORY, ignoreCase = true)

    // isRoot(String) is deprecated and unused by the platform: VcsRootDetectorImpl
    // calls isRoot(VirtualFile) and VcsRootErrorsFinder calls validateRoot.
    override fun isRoot(file: VirtualFile): Boolean = isLoreRoot(file)

    override fun validateRoot(file: VirtualFile): Boolean = isLoreRoot(file)
}
