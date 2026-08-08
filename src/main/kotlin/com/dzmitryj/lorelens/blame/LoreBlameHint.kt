package com.dzmitryj.lorelens.blame

import com.dzmitryj.lorelens.dirty.LoreDirtySettings
import com.dzmitryj.lorelens.model.LoreHistoryRecord
import com.dzmitryj.lorelens.repo.LoreRootFinder
import com.intellij.codeInsight.hint.HintManager
import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorCustomElementRenderer
import com.intellij.openapi.editor.Inlay
import com.intellij.openapi.editor.event.CaretEvent
import com.intellij.openapi.editor.event.CaretListener
import com.intellij.openapi.editor.event.EditorMouseEvent
import com.intellij.openapi.editor.event.EditorMouseMotionListener
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.progress.EmptyProgressIndicator
import com.intellij.openapi.progress.ProgressManager
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
    private var running: EmptyProgressIndicator? = null

    init {
        editor.addEditorMouseMotionListener(HoverListener())
    }

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

        // Blame walks history and diffs, so it never runs on the EDT. It runs
        // under an indicator we own, because executeOnPooledThread installs
        // none and the service's checkCanceled would otherwise do nothing --
        // a superseded blame would run to completion in the background.
        val indicator = EmptyProgressIndicator()
        running?.cancel()
        running = indicator

        ApplicationManager.getApplication().executeOnPooledThread {
            val record = runCatching {
                ProgressManager.getInstance().runProcess<LoreHistoryRecord?>(
                    { LoreBlameService.getInstance(project).blame(root.toNioPath(), relative)?.at(line) },
                    indicator,
                )
            }.getOrNull() ?: return@executeOnPooledThread

            ApplicationManager.getApplication().invokeLater {
                if (editor.isDisposed) return@invokeLater
                if (editor.caretModel.logicalPosition.line != line) return@invokeLater
                show(line, HintContext(root.toNioPath(), relative, record))
            }
        }
    }

    private fun show(line: Int, context: HintContext) {
        val offset = editor.document.getLineEndOffset(line.coerceAtMost(editor.document.lineCount - 1))
        val renderer = HintRenderer(project, describe(context.record), context)
        inlay = editor.inlayModel.addAfterLineEndElement(offset, false, renderer)
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

    /**
     * The renderer interface has no tooltip hook, so hovering is hit-tested
     * against the inlay by hand and answered with a hint.
     */
    private inner class HoverListener : EditorMouseMotionListener {

        private var hovered: Inlay<*>? = null

        override fun mouseMoved(event: EditorMouseEvent) {
            val over = editor.inlayModel.getElementAt(event.mouseEvent.point)?.takeIf { it === inlay }
            if (over === hovered) return
            hovered = over

            // The inlay already shows the subject; only a body adds anything.
            val message = (over?.renderer as? HintRenderer)?.context?.record?.message ?: return
            if (message.lineSequence().count() < 2) return
            HintManager.getInstance().showInformationHint(editor, message)
        }
    }

    private class HintContext(
        val root: Path,
        val relativePath: String,
        val record: LoreHistoryRecord,
    )

    private class HintRenderer(
        private val project: Project,
        private val text: String,
        val context: HintContext,
    ) : EditorCustomElementRenderer {

        override fun getContextMenuGroup(inlay: Inlay<*>): ActionGroup =
            loreBlameActions(project, context.root, context.relativePath, context.record)

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
