package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.text.DateFormatUtil
import com.intellij.util.ui.JBFont
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.awt.BorderLayout
import java.awt.datatransfer.StringSelection
import javax.swing.JPanel

/**
 * What the row cannot hold: the full message, both identities, the whole hash
 * and where the revision sits.
 *
 * Laid out with the UI DSL so labels and values line up on a grid, rather than
 * as a stack of components each deciding its own alignment.
 */
class LoreCommitDetailsPanel : JPanel(BorderLayout()) {

    private val host = JPanel(BorderLayout())

    init {
        add(ScrollPaneFactory.createScrollPane(host, true), BorderLayout.CENTER)
        show(null, emptyList())
    }

    fun show(row: LogRow?, containingBranches: List<String>) {
        host.removeAll()
        host.add(if (row == null) empty() else content(row, containingBranches), BorderLayout.NORTH)
        host.revalidate()
        host.repaint()
    }

    private fun empty(): JPanel = panel {
        row {
            label(LoreLensBundle.message("details.none"))
                .applyToComponent { foreground = UIUtil.getContextHelpForeground() }
        }
    }.apply { border = JBUI.Borders.empty(12) }

    private fun content(row: LogRow, containingBranches: List<String>): JPanel {
        val entry = row.entry
        val author = entry.author.orEmpty()
        val committer = entry.metadata.committer

        return panel {
            row {
                label(entry.subject.orEmpty())
                    .applyToComponent { font = JBFont.label().asBold().biggerOn(1f) }
                    .align(AlignX.FILL)
            }

            entry.metadata.body?.takeIf { it.isNotBlank() }?.let { body ->
                row {
                    // A text area rather than a label: commit bodies wrap, and a
                    // label would paint the whole thing as one clipped line.
                    cell(
                        JBTextArea(body.trim()).apply {
                            isEditable = false
                            isOpaque = false
                            lineWrap = true
                            wrapStyleWord = true
                            border = JBUI.Borders.emptyTop(4)
                            font = JBFont.label()
                        },
                    ).align(AlignX.FILL)
                }
            }

            separator()

            row(LoreLensBundle.message("details.field.revision")) {
                label("${entry.number}")
                link(entry.revision.short) {
                    CopyPasteManager.getInstance().setContents(StringSelection(entry.revision.hex))
                }.applyToComponent {
                    toolTipText = LoreLensBundle.message("details.copy.hash")
                }
            }

            row(LoreLensBundle.message("details.field.author")) {
                label(author)
                if (committer != null && committer != author) {
                    comment(LoreLensBundle.message("details.committer", committer))
                }
            }

            entry.timestampMillis?.let { millis ->
                row(LoreLensBundle.message("details.field.date")) {
                    label(DateFormatUtil.formatDateTime(millis))
                }
            }

            if (entry.parents.isNotEmpty()) {
                row(LoreLensBundle.message("details.field.parents")) {
                    label(entry.parents.joinToString(", ") { it.short })
                    if (entry.isMerge) {
                        comment(LoreLensBundle.message("details.is.merge"))
                    }
                }
            }

            if (containingBranches.isNotEmpty()) {
                row(LoreLensBundle.message("details.field.branch")) {
                    label(containingBranches.joinToString(", "))
                }
            }

            if (!row.synced) {
                row {
                    cell(
                        JBLabel(LoreLensBundle.message("details.not.synced")).apply {
                            foreground = UIUtil.getErrorForeground()
                        },
                    )
                }
            }
        }.apply { border = JBUI.Borders.empty(12) }
    }
}
