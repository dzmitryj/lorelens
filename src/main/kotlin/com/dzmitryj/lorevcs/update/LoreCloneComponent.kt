package com.dzmitryj.lorevcs.update

import com.dzmitryj.lorevcs.LoreBundle
import com.dzmitryj.lorevcs.LoreVcs
import com.dzmitryj.lorevcs.api.LoreSyncApi
import com.dzmitryj.lorevcs.ffi.generated.FilterExcludeEvent
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
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
import java.util.concurrent.atomic.AtomicInteger
import javax.swing.JComponent

class LoreCloneComponent(
    private val project: Project,
    private val listener: VcsCloneDialogComponentStateListener,
) : VcsCloneComponent {

    private val urlField = JBTextField()
    private val directoryField = TextFieldWithBrowseButton().apply {
        addBrowseFolderListener(
            project,
            FileChooserDescriptorFactory.singleDir()
                .withTitle(LoreBundle.message("clone.directory.title")),
        )
    }
    private val sharedStore = JBCheckBox(LoreBundle.message("clone.shared.store"), true)

    /**
     * v0.8.6 accepts a view filter only when cloning, and exposes no API to read
     * or change one afterwards, so this is a file the user supplies rather than
     * something the IDE can render or edit.
     */
    private val viewFilterField = TextFieldWithBrowseButton().apply {
        addBrowseFolderListener(
            project,
            FileChooserDescriptorFactory.singleFile()
                .withTitle(LoreBundle.message("clone.view.title")),
        )
    }

    private val view = panel {
        row(LoreBundle.message("clone.url")) { cell(urlField).align(com.intellij.ui.dsl.builder.AlignX.FILL) }
        row(LoreBundle.message("clone.directory")) {
            cell(directoryField).align(com.intellij.ui.dsl.builder.AlignX.FILL)
        }
        row { cell(sharedStore) }
        row { comment(LoreBundle.message("clone.shared.store.comment")) }
        collapsibleGroup(LoreBundle.message("clone.advanced")) {
            row(LoreBundle.message("clone.view")) {
                cell(viewFilterField).align(com.intellij.ui.dsl.builder.AlignX.FILL)
            }
            row { comment(LoreBundle.message("clone.view.comment")) }
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
        val viewFilter = viewFilterField.text.trim()

        ProgressManager.getInstance().run(
            object : Task.Backgroundable(project, LoreBundle.message("clone.progress", url), true) {
                private val excluded = AtomicInteger()

                override fun run(indicator: ProgressIndicator) {
                    destination.toFile().mkdirs()
                    LoreSyncApi.clone(destination, url, shared, viewFilter) { event ->
                        indicator.checkCanceled()
                        if (event is FilterExcludeEvent) excluded.incrementAndGet()
                    }
                }

                override fun onSuccess() {
                    // A view filter silently materialises less than the whole
                    // repository, so say how much was left out.
                    if (excluded.get() > 0) {
                        NotificationGroupManager.getInstance()
                            .getNotificationGroup("Lore")
                            .createNotification(
                                LoreBundle.message("clone.excluded.title"),
                                LoreBundle.message("clone.excluded", excluded.get()),
                                NotificationType.INFORMATION,
                            )
                            .notify(project)
                    }
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
