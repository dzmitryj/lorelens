package com.dzmitryj.lorevcs.update

import com.dzmitryj.lorevcs.LoreBundle
import com.dzmitryj.lorevcs.LoreVcs
import com.dzmitryj.lorevcs.api.LoreSyncApi
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.openapi.vcs.CheckoutProvider
import com.intellij.openapi.vcs.ui.VcsCloneComponent
import com.intellij.openapi.vcs.ui.cloneDialog.VcsCloneDialogComponentStateListener
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.ui.JBUI
import java.io.File
import java.nio.file.Path
import javax.swing.JComponent

class LoreCloneComponent(
    private val project: Project,
    private val listener: VcsCloneDialogComponentStateListener,
) : VcsCloneComponent {

    private val urlField = JBTextField()
    private val directoryField = TextFieldWithBrowseButton().apply {
        addBrowseFolderListener(
            project,
            FileChooserDescriptorFactory.createSingleFolderDescriptor()
                .withTitle(LoreBundle.message("clone.directory.title")),
        )
    }
    private val sharedStore = JBCheckBox(LoreBundle.message("clone.shared.store"), true)

    private val view = panel {
        row(LoreBundle.message("clone.url")) { cell(urlField).align(com.intellij.ui.dsl.builder.AlignX.FILL) }
        row(LoreBundle.message("clone.directory")) {
            cell(directoryField).align(com.intellij.ui.dsl.builder.AlignX.FILL)
        }
        row { cell(sharedStore) }
        row {
            comment(LoreBundle.message("clone.shared.store.comment"))
        }
    }.apply { border = JBUI.Borders.empty(8) }

    override fun getView(): JComponent = view

    override fun getPreferredFocusedComponent(): JComponent = urlField

    override fun isOkEnabled(): Boolean =
        urlField.text.isNotBlank() && directoryField.text.isNotBlank()

    override fun doValidateAll(): List<ValidationInfo> = buildList {
        if (urlField.text.isBlank()) {
            add(ValidationInfo(LoreBundle.message("clone.url.required"), urlField))
        }
        if (directoryField.text.isBlank()) {
            add(ValidationInfo(LoreBundle.message("clone.directory.required"), directoryField))
        }
    }

    override fun doClone(listener: CheckoutProvider.Listener) {
        val url = urlField.text.trim()
        val destination = File(directoryField.text.trim()).toPath()
        val shared = sharedStore.isSelected

        ProgressManager.getInstance().run(
            object : Task.Backgroundable(project, LoreBundle.message("clone.progress", url), true) {
                override fun run(indicator: ProgressIndicator) {
                    destination.toFile().mkdirs()
                    LoreSyncApi.clone(destination, url, shared) { indicator.checkCanceled() }
                }

                override fun onSuccess() {
                    listener.directoryCheckedOut(destination.toFile(), LoreVcs.KEY)
                    listener.checkoutCompleted()
                }
            },
        )
    }

    override fun onComponentSelected(listener: VcsCloneDialogComponentStateListener) {
        listener.onOkActionEnabled(isOkEnabled())
    }

    override fun dispose() = Unit
}
