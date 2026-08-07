package com.dzmitryj.lorevcs.update

import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.CheckoutProvider
import com.intellij.openapi.vcs.ui.VcsCloneComponent
import com.intellij.openapi.vcs.ui.cloneDialog.VcsCloneDialogComponentStateListener

class LoreCheckoutProvider : CheckoutProvider {

    /** The leading underscore is the mnemonic convention the built-in providers use. */
    override fun getVcsName(): String = "_Lore"

    override fun buildVcsCloneComponent(
        project: Project,
        modalityState: ModalityState,
        dialogStateListener: VcsCloneDialogComponentStateListener,
    ): VcsCloneComponent = LoreCloneComponent(project, dialogStateListener)
}
