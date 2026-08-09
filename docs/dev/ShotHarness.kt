package com.dzmitryj.lorelens.demo

import com.intellij.openapi.application.EDT
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.openapi.wm.WindowManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * Temporary README-screenshot harness, active only with -Dlorelens.shots.dir.
 * Renders the real IDE frame to PNG off the screen: no input injection, no
 * window focus, nothing the user's desktop can notice. Removed before commit.
 */
class ShotHarness : ProjectActivity {

    override suspend fun execute(project: Project) {
        val dir = System.getProperty("lorelens.shots.dir") ?: return
        File(dir).mkdirs()

        delay(20_000) // project open, mapping, startup rescan, log load

        suspend fun shoot(name: String) = withContext(Dispatchers.EDT) {
            val frame = WindowManager.getInstance().getFrame(project) ?: return@withContext
            val root = frame.rootPane
            val image = BufferedImage(root.width * 2, root.height * 2, BufferedImage.TYPE_INT_RGB)
            val g = image.createGraphics()
            g.scale(2.0, 2.0)
            root.paint(g)
            g.dispose()
            ImageIO.write(image, "png", File(dir, name))
            println("SHOT $name ${root.width}x${root.height}")
        }

        suspend fun resize(w: Int, h: Int) = withContext(Dispatchers.EDT) {
            WindowManager.getInstance().getFrame(project)?.setSize(w, h)
        }

        suspend fun open(path: String, line: Int) = withContext(Dispatchers.EDT) {
            val file = LocalFileSystem.getInstance().refreshAndFindFileByPath(project.basePath + path)
            if (file != null) {
                FileEditorManager.getInstance(project)
                    .openTextEditor(OpenFileDescriptor(project, file, line, 8), false)
            }
        }

        // Renderer.cpp open in the background so the blame shot has a target.
        resize(1920, 1380)
        open("/Source/Engine/Renderer.cpp", 3)
        delay(4_000)

        val toolWindow = withContext(Dispatchers.EDT) {
            ToolWindowManager.getInstance(project).getToolWindow("Version Control")
        } ?: run { println("SHOTS DONE (no toolwindow)"); return }

        suspend fun selectTab(tabName: String) = withContext(Dispatchers.EDT) {
            toolWindow.show(null)
            (toolWindow as? com.intellij.openapi.wm.ex.ToolWindowEx)?.stretchHeight(220)
            val manager = toolWindow.contentManager
            manager.contents.firstOrNull { it.tabName == tabName || it.displayName == tabName }
                ?.let { manager.setSelectedContent(it, false) }
        }

        // History reads the checked-out branch's ancestry; the seeder leaves main
        // checked out, which is the branch that has absorbed every merge.
        resize(2200, 1400)
        selectTab("History")
        delay(8_000)
        shoot("history.png")

        // The whole graph is one 22px column per commit; a wide frame keeps all
        // ~100 columns on screen, since the panel only paints its viewport.
        resize(2600, 1500)
        selectTab("Branch Graph")
        delay(8_000)
        shoot("branch-graph.png")

        // Blame: hide the tool window so the editor fills the frame.
        resize(1920, 1380)
        withContext(Dispatchers.EDT) { toolWindow.hide(null) }
        delay(3_000)
        shoot("blame.png")

        // Local Changes + locks: a binary asset held by another user shows the
        // lock banner; the tool window lists the staged and unstaged edits. The
        // lock table is server state the plugin only reads on demand, so prime
        // it before opening the file the banner is expected on.
        withContext(Dispatchers.EDT) {
            com.dzmitryj.lorelens.lock.LoreLockService.getInstance(project).refreshAll()
        }
        delay(2_000)
        open("/Assets/Characters/Hero.mesh", 0)
        selectTab("Local Changes")
        delay(4_000)
        shoot("local-changes.png")

        println("SHOTS DONE")
    }
}
