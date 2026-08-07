package com.dzmitryj.lorevcs.ui

import com.dzmitryj.lorevcs.repo.LoreRootFinder
import com.intellij.openapi.project.Project
import java.util.function.Predicate

class LoreLogTabVisibility : Predicate<Project> {

    override fun test(project: Project): Boolean =
        LoreRootFinder.mappedRoots(project).isNotEmpty()
}
