package com.dzmitryj.lorelens

import com.dzmitryj.lorelens.changes.LoreChangeProvider
import com.dzmitryj.lorelens.changes.LoreDiffProvider
import com.dzmitryj.lorelens.checkin.LoreCheckinEnvironment
import com.dzmitryj.lorelens.checkin.LoreRollbackEnvironment
import com.dzmitryj.lorelens.checkin.LoreVfsListenerService
import com.dzmitryj.lorelens.history.LoreHistoryProvider
import com.dzmitryj.lorelens.lock.LoreEditFileProvider
import com.dzmitryj.lorelens.notify.LoreNotificationSubscriber
import com.dzmitryj.lorelens.update.LoreUpdateEnvironment
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.AbstractVcs
import com.intellij.openapi.vcs.EditFileProvider
import com.intellij.openapi.vcs.ProjectLevelVcsManager
import com.intellij.openapi.vcs.VcsKey
import com.intellij.openapi.vcs.changes.ChangeProvider
import com.intellij.openapi.vcs.checkin.CheckinEnvironment
import com.intellij.openapi.vcs.diff.DiffProvider
import com.intellij.openapi.vcs.history.VcsHistoryProvider
import com.intellij.openapi.vcs.rollback.RollbackEnvironment
import com.intellij.openapi.vcs.update.UpdateEnvironment

class LoreVcs(project: Project) : AbstractVcs(project, NAME) {

    private val changeProvider = LoreChangeProvider(project)
    private val diffProvider = LoreDiffProvider(project)
    private val checkinEnvironment = LoreCheckinEnvironment(project)
    private val rollbackEnvironment = LoreRollbackEnvironment(project)
    private val editFileProvider = LoreEditFileProvider(project)
    private val updateEnvironment = LoreUpdateEnvironment(project)
    private val historyProvider = LoreHistoryProvider(project)

    override fun getDisplayName(): String = LoreLensBundle.message("vcs.name")

    override fun getChangeProvider(): ChangeProvider = changeProvider

    override fun getDiffProvider(): DiffProvider = diffProvider

    override fun getVcsHistoryProvider(): VcsHistoryProvider = historyProvider

    override fun createCheckinEnvironment(): CheckinEnvironment = checkinEnvironment

    override fun createRollbackEnvironment(): RollbackEnvironment = rollbackEnvironment

    override fun getEditFileProvider(): EditFileProvider = editFileProvider

    override fun createUpdateEnvironment(): UpdateEnvironment = updateEnvironment

    override fun activate() {
        LoreVfsListenerService.getInstance(myProject).start()
        // Without this the lock cache is only ever refreshed by hand, so the
        // lock banner cannot appear until the user has already refreshed -- and
        // the banner is the only thing offering that refresh.
        LoreNotificationSubscriber.getInstance(myProject).start()
    }

    override fun deactivate() {
        LoreVfsListenerService.getInstance(myProject).stop()
        LoreNotificationSubscriber.getInstance(myProject).stop()
    }

    /** `lore link` pins one repository inside another. */
    override fun allowsNestedRoots(): Boolean = true

    /**
     * Hides the platform's Shelf tab. Shelving is patch-based and sidesteps
     * Lore's staged state; there is no Lore-native equivalent to put in its
     * place, so the tab simply goes.
     */
    override fun isWithCustomShelves(): Boolean = true

    companion object {
        const val NAME = "LoreLens"
        val KEY: VcsKey = createKey(NAME)

        fun of(project: Project): LoreVcs? =
            ProjectLevelVcsManager.getInstance(project).findVcsByName(NAME) as? LoreVcs
    }
}
