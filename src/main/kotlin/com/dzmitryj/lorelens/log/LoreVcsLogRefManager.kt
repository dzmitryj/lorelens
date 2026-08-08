package com.dzmitryj.lorelens.log

import com.intellij.ui.JBColor
import com.intellij.vcs.log.RefGroup
import com.intellij.vcs.log.VcsLogRefManager
import com.intellij.vcs.log.VcsRef
import com.intellij.vcs.log.VcsRefType
import java.awt.Color
import java.io.DataInput
import java.io.DataOutput

/**
 * Lore has branches and nothing else -- no tags, no stashes -- so refs come in
 * two kinds and sort by name.
 */
object LoreRefTypes {

    val LOCAL: VcsRefType = LoreRefType(JBColor(0x00A000, 0x499C54))
    val REMOTE: VcsRefType = LoreRefType(JBColor(0x1A5FB4, 0x548AF7))

    private class LoreRefType(private val color: Color) : VcsRefType {
        override fun isBranch(): Boolean = true

        override fun getBackgroundColor(): Color = color
    }
}

class LoreVcsLogRefManager : VcsLogRefManager {

    private val favorites = mutableSetOf<String>()

    override fun getBranchLayoutComparator(): Comparator<VcsRef> = BY_KIND_THEN_NAME

    override fun getLabelsOrderComparator(): Comparator<VcsRef> = BY_KIND_THEN_NAME

    override fun groupForBranchFilter(refs: Collection<VcsRef>): List<RefGroup> =
        listOf(
            LoreRefGroup(LOCAL_GROUP, refs.filter { it.type === LoreRefTypes.LOCAL }.sortedWith(BY_KIND_THEN_NAME)),
            LoreRefGroup(REMOTE_GROUP, refs.filter { it.type === LoreRefTypes.REMOTE }.sortedWith(BY_KIND_THEN_NAME)),
        ).filter { it.refs.isNotEmpty() }

    /** One label per ref in the table, local first so the checkout reads first. */
    override fun groupForTable(
        refs: Collection<VcsRef>,
        compact: Boolean,
        showTagNames: Boolean,
    ): List<RefGroup> = refs.sortedWith(BY_KIND_THEN_NAME).map { LoreRefGroup(it.name, listOf(it)) }

    override fun serialize(out: DataOutput, type: VcsRefType) {
        out.writeBoolean(type === LoreRefTypes.REMOTE)
    }

    override fun deserialize(input: DataInput): VcsRefType =
        if (input.readBoolean()) LoreRefTypes.REMOTE else LoreRefTypes.LOCAL

    override fun isFavorite(reference: VcsRef): Boolean = favorites.contains(reference.name)

    override fun setFavorite(reference: VcsRef, favorite: Boolean) {
        if (favorite) favorites += reference.name else favorites -= reference.name
    }

    private class LoreRefGroup(private val groupName: String, private val members: List<VcsRef>) : RefGroup {
        override fun getName(): String = groupName

        override fun getRefs(): List<VcsRef> = members

        override fun getColors(): List<Color> = members.map { it.type.backgroundColor }
    }

    private companion object {
        const val LOCAL_GROUP = "Local"
        const val REMOTE_GROUP = "Remote"

        val BY_KIND_THEN_NAME: Comparator<VcsRef> =
            compareBy<VcsRef> { if (it.type === LoreRefTypes.LOCAL) 0 else 1 }.thenBy { it.name }
    }
}
