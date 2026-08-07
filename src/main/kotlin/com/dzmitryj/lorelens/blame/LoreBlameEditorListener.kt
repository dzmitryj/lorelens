package com.dzmitryj.lorelens.blame

import com.dzmitryj.lorelens.repo.LoreRootFinder
import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener
import com.intellij.openapi.fileEditor.FileDocumentManager

/**
 * Attaches the caret-line blame hint to editors opened on files under a Lore
 * root. Editors elsewhere are left untouched, so nothing is computed for files
 * this plugin does not track.
 */
class LoreBlameEditorListener : EditorFactoryListener {

    override fun editorCreated(event: EditorFactoryEvent) {
        val editor = event.editor
        val project = editor.project ?: return
        val file = FileDocumentManager.getInstance().getFile(editor.document) ?: return
        if (LoreRootFinder.findRoot(file) == null) return

        val hint = LoreBlameHint(project, editor)
        editor.caretModel.addCaretListener(hint)
        hint.schedule()
    }
}
