package com.dzmitryj.lorevcs.update

import com.dzmitryj.lorevcs.LoreBundle
import com.dzmitryj.lorevcs.LoreVcs
import com.dzmitryj.lorevcs.api.LoreClient
import com.dzmitryj.lorevcs.api.LoreSyncApi
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.openapi.vcs.ProjectLevelVcsManager
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import java.io.File
import javax.swing.JComponent

/**
 * A deliberate action rather than a VcsRepositoryInitializer. That extension
 * point is invoked from "Enable Version Control Integration" and from project
 * creation, both of which expect a local, non-interactive operation. Lore is
 * centralized and cannot create a repository without a server, so failing
 * inside a flow the user never opted into would be the wrong behaviour.
 */
class LoreCreateRepositoryAction : AnAction() {

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val dialog = CreateRepositoryDialog(project)
        if (!dialog.showAndGet()) return

        val url = dialog.url
        val directory = File(dialog.directory).toPath()
        val shared = dialog.useSharedStore

        ProgressManager.getInstance().run(
            object : Task.Backgroundable(project, LoreBundle.message("create.progress"), true) {
                override fun run(indicator: ProgressIndicator) {
                    directory.toFile().mkdirs()
                    LoreClient.createRepository(directory, url)
                    LoreSyncApi.clone(directory, url, shared)
                }

                override fun onSuccess() {
                    LocalFileSystem.getInstance().refreshAndFindFileByNioFile(directory) ?: return
                    // Property syntax on directoryMappings resolves to the
                    // deprecated DoNotUse pair; this is the supported call.
                    ProjectLevelVcsManager.getInstance(project)
                        .setDirectoryMapping(directory.toString(), LoreVcs.NAME)
                }
            },
        )
    }

    private class CreateRepositoryDialog(project: Project) : DialogWrapper(project) {

        private val urlField = JBTextField()
        private val directoryField = TextFieldWithBrowseButton().apply {
            addBrowseFolderListener(
                project,
                FileChooserDescriptorFactory.createSingleFolderDescriptor()
                    .withTitle(LoreBundle.message("clone.directory.title")),
            )
        }
        private val sharedStore = JBCheckBox(LoreBundle.message("clone.shared.store"), true)

        val url: String get() = urlField.text.trim()
        val directory: String get() = directoryField.text.trim()
        val useSharedStore: Boolean get() = sharedStore.isSelected

        init {
            title = LoreBundle.message("create.title")
            init()
        }

        override fun createCenterPanel(): JComponent = panel {
            row {
                comment(LoreBundle.message("create.centralized.notice"))
            }
            row(LoreBundle.message("create.url")) { cell(urlField).align(AlignX.FILL) }
            row(LoreBundle.message("clone.directory")) { cell(directoryField).align(AlignX.FILL) }
            row { cell(sharedStore) }
            row { comment(LoreBundle.message("clone.shared.store.comment")) }
        }

        override fun doValidate(): ValidationInfo? = when {
            url.isEmpty() -> ValidationInfo(LoreBundle.message("clone.url.required"), urlField)
            directory.isEmpty() ->
                ValidationInfo(LoreBundle.message("clone.directory.required"), directoryField)
            else -> null
        }
    }
}
