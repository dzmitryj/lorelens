package com.dzmitryj.lorevcs

import com.dzmitryj.lorevcs.changes.LoreChangeProvider
import com.dzmitryj.lorevcs.changes.LoreDiffProvider
import com.dzmitryj.lorevcs.checkin.LoreCheckinEnvironment
import com.dzmitryj.lorevcs.checkin.LoreRollbackEnvironment
import com.dzmitryj.lorevcs.checkin.LoreVfsListenerService
import com.dzmitryj.lorevcs.lock.LoreEditFileProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.AbstractVcs
import com.intellij.openapi.vcs.EditFileProvider
import com.intellij.openapi.vcs.ProjectLevelVcsManager
import com.intellij.openapi.vcs.VcsKey
import com.intellij.openapi.vcs.changes.ChangeProvider
import com.intellij.openapi.vcs.checkin.CheckinEnvironment
import com.intellij.openapi.vcs.diff.DiffProvider
import com.intellij.openapi.vcs.rollback.RollbackEnvironment

class LoreVcs(project: Project) : AbstractVcs(project, NAME) {

    private val changeProvider = LoreChangeProvider(project)
    private val diffProvider = LoreDiffProvider(project)
    private val checkinEnvironment = LoreCheckinEnvironment(project)
    private val rollbackEnvironment = LoreRollbackEnvironment(project)
    private val editFileProvider = LoreEditFileProvider(project)

    override fun getDisplayName(): String = LoreBundle.message("vcs.name")

    override fun getChangeProvider(): ChangeProvider = changeProvider

    override fun getDiffProvider(): DiffProvider = diffProvider

    override fun createCheckinEnvironment(): CheckinEnvironment = checkinEnvironment

    override fun createRollbackEnvironment(): RollbackEnvironment = rollbackEnvironment

    override fun getEditFileProvider(): EditFileProvider = editFileProvider

    override fun activate() {
        LoreVfsListenerService.getInstance(myProject).start()
    }

    override fun deactivate() {
        LoreVfsListenerService.getInstance(myProject).stop()
    }

    /** `lore link` pins one repository inside another. */
    override fun allowsNestedRoots(): Boolean = true

    companion object {
        const val NAME = "Lore"
        val KEY: VcsKey = createKey(NAME)

        fun of(project: Project): LoreVcs? =
            ProjectLevelVcsManager.getInstance(project).findVcsByName(NAME) as? LoreVcs
    }
}
