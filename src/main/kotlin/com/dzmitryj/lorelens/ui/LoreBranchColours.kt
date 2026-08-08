package com.dzmitryj.lorelens.ui

import com.intellij.ui.JBColor
import java.awt.Color
import kotlin.math.abs

/**
 * One colour per branch, everywhere.
 *
 * Both graphs used to colour by lane index with their own palettes, and lane
 * indices disagree between views by construction, so the same branch changed
 * colour from tab to tab. Colour attaches to the branch name instead: assigned
 * from one palette in hierarchy order -- main first, then dev-main, then its
 * children -- so every surface that names a branch shows it the same way.
 *
 * The assignment shifts only when the branch listing itself changes shape;
 * that is the accepted cost of a curated palette over generated hues.
 */
object LoreBranchColours {

    @Volatile
    private var assigned: Map<String, Color> = emptyMap()

    /**
     * Fixes the palette to the hierarchy. Called wherever the branch order is
     * already computed; every caller passes the same order, so whoever runs
     * first settles the same result.
     */
    fun assign(order: List<String>) {
        if (order.isEmpty()) return
        assigned = order.withIndex().associate { (index, name) ->
            name to PALETTE[index % PALETTE.size]
        }
    }

    /** A branch outside the assignment still gets a stable colour by hash. */
    fun colourOf(branch: String?): Color {
        val name = branch.orEmpty().ifEmpty { return UNKNOWN }
        return assigned[name] ?: PALETTE[abs(name.hashCode()) % PALETTE.size]
    }

    private val UNKNOWN = JBColor(0x9AA0A6, 0x6E7378)

    /** The best of the two palettes the views used to keep separately. */
    private val PALETTE: List<Color> = listOf(
        JBColor(0x4A88C7, 0x548AF7),
        JBColor(0x5B8C3E, 0x6FA85A),
        JBColor(0x7A3E9D, 0xB07DD8),
        JBColor(0xC7752A, 0xD9955B),
        JBColor(0xB0384A, 0xD1707C),
        JBColor(0x0F7B8A, 0x4CA6B5),
        JBColor(0x8A6D1F, 0xC0A24A),
        JBColor(0xA83E5B, 0xC76B84),
    )
}
