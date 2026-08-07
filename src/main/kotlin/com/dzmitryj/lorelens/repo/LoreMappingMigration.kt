package com.dzmitryj.lorelens.repo

import com.dzmitryj.lorelens.LoreVcs
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.openapi.vcs.ProjectLevelVcsManager
import java.nio.file.Path

/**
 * Repoints directory mappings left behind by an earlier plugin name.
 *
 * The platform resolves a mapping's VCS by plain string match and has no rename
 * hook, so a mapping naming a VCS that no longer exists is silently inert: the
 * root stops being version controlled, and because unregistered-root detection
 * filters purely by path, the stale mapping also suppresses any offer to add the
 * correct one. Nothing warns. This turns that into a non-event.
 */
class LoreMappingMigration : ProjectActivity {

    override suspend fun execute(project: Project) {
        val manager = ProjectLevelVcsManager.getInstance(project)

        val stale = manager.directoryMappings.filter { mapping ->
            mapping.vcs in FORMER_VCS_NAMES && isLoreRoot(directoryOf(project, mapping.directory) ?: return@filter false)
        }
        if (stale.isEmpty()) return

        stale.forEach { mapping ->
            log.info("Repointing ${mapping.directory.ifEmpty { "<Project>" }} from ${mapping.vcs} to ${LoreVcs.NAME}")
            manager.setDirectoryMapping(mapping.directory, LoreVcs.NAME)
        }
    }

    /** An empty directory is the `<Project>` mapping, which means the base path. */
    private fun directoryOf(project: Project, directory: String): Path? =
        runCatching { Path.of(directory.ifEmpty { project.basePath ?: return null }) }.getOrNull()

    private companion object {
        val log = logger<LoreMappingMigration>()

        /** Names this plugin registered under before it was called LoreLens. */
        val FORMER_VCS_NAMES = setOf("Lore")
    }
}
