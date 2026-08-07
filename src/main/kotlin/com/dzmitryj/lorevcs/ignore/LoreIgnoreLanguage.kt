package com.dzmitryj.lorevcs.ignore

import com.dzmitryj.lorevcs.LoreBundle
import com.intellij.openapi.vcs.changes.ignore.lang.IgnoreFileType
import com.intellij.openapi.vcs.changes.ignore.lang.IgnoreLanguage

/**
 * Subclassing the platform's ignore language inherits highlighting, comment
 * toggling, brace matching, directory markers and the duplicate and unused entry
 * inspections, the same way git4idea does for .gitignore.
 */
class LoreIgnoreLanguage private constructor() : IgnoreLanguage("LoreIgnore", "loreignore") {

    override fun getDisplayName(): String = LoreBundle.message("ignore.language")

    companion object {
        @JvmField
        val INSTANCE: LoreIgnoreLanguage = LoreIgnoreLanguage()
    }
}

class LoreIgnoreFileType private constructor() : IgnoreFileType(LoreIgnoreLanguage.INSTANCE) {

    override fun getName(): String = "LoreIgnore file"

    override fun getDescription(): String = LoreBundle.message("ignore.description")

    companion object {
        @JvmField
        val INSTANCE: LoreIgnoreFileType = LoreIgnoreFileType()
    }
}
