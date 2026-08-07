package com.dzmitryj.lorevcs

import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.AbstractVcs
import com.intellij.openapi.vcs.ProjectLevelVcsManager
import com.intellij.openapi.vcs.VcsKey

class LoreVcs(project: Project) : AbstractVcs(project, NAME) {

    override fun getDisplayName(): String = LoreBundle.message("vcs.name")

    /** `lore link` pins one repository inside another. */
    override fun allowsNestedRoots(): Boolean = true

    companion object {
        const val NAME = "Lore"
        val KEY: VcsKey = createKey(NAME)

        fun of(project: Project): LoreVcs? =
            ProjectLevelVcsManager.getInstance(project).findVcsByName(NAME) as? LoreVcs
    }
}
