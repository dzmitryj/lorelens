package com.dzmitryj.lorevcs

import com.dzmitryj.lorevcs.changes.LoreChangeProvider
import com.dzmitryj.lorevcs.changes.LoreDiffProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.AbstractVcs
import com.intellij.openapi.vcs.ProjectLevelVcsManager
import com.intellij.openapi.vcs.VcsKey
import com.intellij.openapi.vcs.changes.ChangeProvider
import com.intellij.openapi.vcs.diff.DiffProvider

class LoreVcs(project: Project) : AbstractVcs(project, NAME) {

    private val changeProvider = LoreChangeProvider(project)
    private val diffProvider = LoreDiffProvider(project)

    override fun getDisplayName(): String = LoreBundle.message("vcs.name")

    override fun getChangeProvider(): ChangeProvider = changeProvider

    override fun getDiffProvider(): DiffProvider = diffProvider

    /** `lore link` pins one repository inside another. */
    override fun allowsNestedRoots(): Boolean = true

    companion object {
        const val NAME = "Lore"
        val KEY: VcsKey = createKey(NAME)

        fun of(project: Project): LoreVcs? =
            ProjectLevelVcsManager.getInstance(project).findVcsByName(NAME) as? LoreVcs
    }
}
