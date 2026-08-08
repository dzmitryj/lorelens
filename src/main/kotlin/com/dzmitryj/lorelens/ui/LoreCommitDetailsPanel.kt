package com.dzmitryj.lorelens.ui

import com.dzmitryj.lorelens.LoreLensBundle
import com.dzmitryj.lorelens.api.LoreHistoryEntry
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.SimpleColoredComponent
import com.intellij.ui.SimpleTextAttributes
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.datatransfer.StringSelection
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JPanel

/**
 * What the row cannot hold: the full message, both identities, the whole hash
 * and the branches the revision sits on.
 *
 * Hand-rolled from SimpleColoredComponent because the platform's own commit
 * details panel is internal.
 */
class LoreCommitDetailsPanel : JPanel(BorderLayout()) {

    private val body = JPanel().apply {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        border = JBUI.Borders.empty(8, 12)
        isOpaque = false
    }

    private var hash: String = ""

    init {
        add(ScrollPaneFactory.createScrollPane(body, true), BorderLayout.CENTER)
    }

    fun show(row: LogRow?, containingBranches: List<String>) {
        body.removeAll()

        if (row == null) {
            body.add(
                line { append(LoreLensBundle.message("details.none"), SimpleTextAttributes.GRAYED_ATTRIBUTES) },
            )
            revalidateBody()
            return
        }

        val entry = row.entry
        hash = entry.revision.hex

        body.add(
            line {
                append(entry.subject.orEmpty(), SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES)
                if (entry.isMerge) {
                    append("   ${LoreLensBundle.message("log.merge")}", SimpleTextAttributes.GRAYED_BOLD_ATTRIBUTES)
                }
            },
        )

        entry.metadata.body?.lineSequence()?.filter { it.isNotBlank() }?.forEach { text ->
            body.add(line { append(text, SimpleTextAttributes.REGULAR_ATTRIBUTES) })
        }

        body.add(Box.createVerticalStrut(JBUI.scale(8)))

        body.add(
            line {
                append(
                    LoreLensBundle.message("details.revision", entry.number),
                    SimpleTextAttributes.REGULAR_ATTRIBUTES,
                )
                append("  ${entry.revision.hex}", SimpleTextAttributes.GRAYED_ATTRIBUTES)
            }.also { component ->
                // The full hash is the one thing here worth copying.
                component.toolTipText = LoreLensBundle.message("details.copy.hash")
                component.addMouseListener(object : MouseAdapter() {
                    override fun mouseClicked(event: MouseEvent) =
                        CopyPasteManager.getInstance().setContents(StringSelection(hash))
                })
            },
        )

        val author = entry.author.orEmpty()
        val committer = entry.metadata.committer
        body.add(
            line {
                append(LoreLensBundle.message("details.author", author), SimpleTextAttributes.REGULAR_ATTRIBUTES)
                if (committer != null && committer != author) {
                    append(
                        "   ${LoreLensBundle.message("details.committer", committer)}",
                        SimpleTextAttributes.GRAYED_ATTRIBUTES,
                    )
                }
            },
        )

        entry.timestampMillis?.let { millis ->
            body.add(
                line {
                    append(
                        com.intellij.util.text.DateFormatUtil.formatDateTime(millis),
                        SimpleTextAttributes.GRAYED_ATTRIBUTES,
                    )
                },
            )
        }

        if (entry.parents.isNotEmpty()) {
            body.add(
                line {
                    append(
                        LoreLensBundle.message("details.parents", entry.parents.joinToString(", ") { it.short }),
                        SimpleTextAttributes.GRAYED_ATTRIBUTES,
                    )
                },
            )
        }

        if (containingBranches.isNotEmpty()) {
            body.add(
                line {
                    append(
                        LoreLensBundle.message("details.branches", containingBranches.joinToString(", ")),
                        SimpleTextAttributes.GRAYED_ATTRIBUTES,
                    )
                },
            )
        }

        if (!row.synced) {
            body.add(
                line {
                    append(
                        LoreLensBundle.message("details.not.synced"),
                        SimpleTextAttributes.GRAYED_ITALIC_ATTRIBUTES,
                    )
                },
            )
        }

        revalidateBody()
    }

    private fun line(build: SimpleColoredComponent.() -> Unit): SimpleColoredComponent =
        SimpleColoredComponent().apply {
            isOpaque = false
            alignmentX = LEFT_ALIGNMENT
            build()
        }

    private fun revalidateBody() {
        body.revalidate()
        body.repaint()
    }
}
