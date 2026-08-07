package com.dzmitryj.lorevcs.ignore

import com.dzmitryj.lorevcs.LoreVcs
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.VcsKey
import com.intellij.openapi.vcs.changes.IgnoredFileContentProvider
import com.intellij.openapi.vcs.changes.IgnoredFileDescriptor
import com.intellij.openapi.vcs.changes.IgnoredFileProvider
import com.intellij.openapi.vfs.VirtualFile

const val LOREIGNORE = ".loreignore"

/** Backs the "Add to .loreignore" action in the Changes view. */
class LoreIgnoredFileContentProvider(private val project: Project) : IgnoredFileContentProvider {

    override fun getSupportedVcs(): VcsKey = LoreVcs.KEY

    override fun getFileName(): String = LOREIGNORE

    override fun buildIgnoreFileContent(
        ignoreFileRoot: VirtualFile,
        ignoredFileProviders: Array<out IgnoredFileProvider>,
    ): String = buildString {
        ignoredFileProviders.forEach { provider ->
            val entries = provider.getIgnoredFiles(project)
                .map { buildIgnoreEntryContent(ignoreFileRoot, it) }
                .filter(String::isNotBlank)
                .distinct()
            if (entries.isEmpty()) return@forEach

            if (isNotEmpty()) appendLine()
            appendLine("# ${buildIgnoreGroupDescription(provider)}")
            entries.forEach { appendLine(it) }
        }
    }

    override fun buildUnignoreContent(ignorePattern: String): String = "!$ignorePattern"

    override fun buildIgnoreEntryContent(
        ignoreEntryRoot: VirtualFile,
        file: IgnoredFileDescriptor,
    ): String {
        val path = file.path ?: file.mask ?: return ""
        return path.removePrefix(ignoreEntryRoot.path).trimStart('/').ifEmpty { path }
    }

    override fun buildIgnoreGroupDescription(ignoredFileProvider: IgnoredFileProvider): String =
        ignoredFileProvider.ignoredGroupDescription
}
