package com.dzmitryj.lorelens.ui

import com.intellij.ui.JBColor
import java.awt.Color
import kotlin.math.abs

/**
 * A stable colour and initial per author, so the same person reads the same way
 * in the commit graph and the branch graph.
 *
 * Lore records an email and nothing else -- there is no avatar to fetch, and
 * fetching one would put a network call on a paint path and hand the address to
 * a third party. An initial on a derived colour is the honest equivalent.
 */
object LoreAuthorColours {

    fun colourOf(author: String?): Color {
        val name = author.orEmpty().ifEmpty { return UNKNOWN }
        return PALETTE[abs(name.lowercase().hashCode()) % PALETTE.size]
    }

    /** First letter of the local part, which is what distinguishes teammates. */
    fun initialOf(author: String?): String =
        author.orEmpty()
            .substringBefore('@')
            .firstOrNull { it.isLetterOrDigit() }
            ?.uppercase()
            ?: "?"

    private val UNKNOWN = JBColor(0x9AA0A6, 0x6E7378)

    private val PALETTE: List<Color> = listOf(
        JBColor(0x4A88C7, 0x548AF7),
        JBColor(0x00875A, 0x499C54),
        JBColor(0xC7752A, 0xD9955B),
        JBColor(0x7A3E9D, 0xB07DD8),
        JBColor(0xB0384A, 0xD1707C),
        JBColor(0x0F7B8A, 0x4CA6B5),
        JBColor(0x8A6D1F, 0xC0A24A),
        JBColor(0x3C6E4F, 0x5F9E77),
    )
}
