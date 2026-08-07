package com.dzmitryj.lorelens.ignore

import com.dzmitryj.lorelens.LoreLensBundle
import com.intellij.openapi.vcs.changes.ignore.lang.IgnoreFileType
import com.intellij.openapi.vcs.changes.ignore.lang.IgnoreLanguage

/**
 * Subclassing the platform's ignore language inherits highlighting, comment
 * toggling, brace matching, directory markers and the duplicate and unused entry
 * inspections, the same way the bundled Git plugin does for .gitignore.
 */
class LoreIgnoreLanguage private constructor() : IgnoreLanguage("LoreIgnore", "loreignore") {

    override fun getDisplayName(): String = LoreLensBundle.message("ignore.language")

    companion object {
        @JvmField
        val INSTANCE: LoreIgnoreLanguage = LoreIgnoreLanguage()
    }
}

class LoreIgnoreFileType private constructor() : IgnoreFileType(LoreIgnoreLanguage.INSTANCE) {

    override fun getName(): String = "LoreIgnore file"

    override fun getDescription(): String = LoreLensBundle.message("ignore.description")

    companion object {
        @JvmField
        val INSTANCE: LoreIgnoreFileType = LoreIgnoreFileType()
    }
}
