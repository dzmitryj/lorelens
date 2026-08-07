package com.dzmitryj.lorelens.blame

import com.dzmitryj.lorelens.dirty.LoreDirtySettings
import com.dzmitryj.lorelens.model.LoreHistoryRecord
import com.dzmitryj.lorelens.repo.LoreRootFinder
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorCustomElementRenderer
import com.intellij.openapi.editor.Inlay
import com.intellij.openapi.editor.event.CaretEvent
import com.intellij.openapi.editor.event.CaretListener
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.util.Alarm
import com.intellij.util.text.DateFormatUtil
import com.intellij.util.ui.UIUtil
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import java.nio.file.Path

/**
 * A single end-of-line hint on the caret line, in the manner of GitLens:
 *
 *     dimi.mitchell, 2 hours ago - feat(vehicle): default the driver camera
 *
 * Only the caret line is annotated, so the cost stays one lookup into an
 * already-computed blame rather than a gutter-wide render.
 */
class LoreBlameHint(private val project: Project, private val editor: Editor) : CaretListener {

    private val alarm = Alarm(Alarm.ThreadToUse.SWING_THREAD, LoreBlameDisposable(editor))
    private var inlay: Inlay<*>? = null
    private var shownLine = -1

    override fun caretPositionChanged(event: CaretEvent) = schedule()

    fun schedule() {
        alarm.cancelAllRequests()
        alarm.addRequest(::update, DEBOUNCE_MILLIS)
    }

    private fun update() {
        if (editor.isDisposed || !LoreDirtySettings.getInstance().showBlameHint) {
            clear()
            return
        }

        val line = editor.caretModel.logicalPosition.line
        if (line == shownLine) return
        clear()

        val file = FileDocumentManager.getInstance().getFile(editor.document) ?: return
        val root = LoreRootFinder.findRoot(file) ?: return
        val relative = LoreRootFinder.relativePath(root, file) ?: return

        // Blame walks history and diffs, so it never runs on the EDT.
        ApplicationManager.getApplication().executeOnPooledThread {
            val record = LoreBlameService.getInstance(project)
                .blame(root.toNioPath(), relative)
                ?.at(line)
                ?: return@executeOnPooledThread

            ApplicationManager.getApplication().invokeLater {
                if (editor.isDisposed) return@invokeLater
                if (editor.caretModel.logicalPosition.line != line) return@invokeLater
                show(line, record)
            }
        }
    }

    private fun show(line: Int, record: LoreHistoryRecord) {
        val offset = editor.document.getLineEndOffset(line.coerceAtMost(editor.document.lineCount - 1))
        inlay = editor.inlayModel.addAfterLineEndElement(offset, false, HintRenderer(describe(record)))
        shownLine = line
    }

    private fun clear() {
        inlay?.let { Disposer.dispose(it) }
        inlay = null
        shownLine = -1
    }

    private fun describe(record: LoreHistoryRecord): String = buildString {
        append(record.author ?: "unknown")
        record.timestampMillis?.let { append(", ").append(DateFormatUtil.formatPrettyDateTime(it)) }
        record.subject?.let { append("  ").append(it) }
    }

    private class HintRenderer(private val text: String) : EditorCustomElementRenderer {

        override fun calcWidthInPixels(inlay: Inlay<*>): Int =
            inlay.editor.contentComponent.getFontMetrics(font(inlay.editor)).stringWidth(padded())

        override fun paint(
            inlay: Inlay<*>,
            g: Graphics2D,
            targetRegion: Rectangle2D,
            textAttributes: TextAttributes,
        ) {
            val editor = inlay.editor
            g.font = font(editor)
            g.color = UIUtil.getInactiveTextColor()
            g.drawString(
                padded(),
                targetRegion.x.toFloat(),
                (targetRegion.y + editor.ascent).toFloat(),
            )
        }

        private fun padded() = "    $text"

        private fun font(editor: Editor) = editor.colorsScheme.getFont(com.intellij.openapi.editor.colors.EditorFontType.ITALIC)
    }

    /** Ties the debounce alarm to the editor's lifetime. */
    private class LoreBlameDisposable(private val editor: Editor) : com.intellij.openapi.Disposable {
        override fun dispose() = Unit
    }

    private companion object {
        const val DEBOUNCE_MILLIS = 250
    }
}
