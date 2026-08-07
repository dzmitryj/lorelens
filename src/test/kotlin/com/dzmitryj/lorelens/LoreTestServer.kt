package com.dzmitryj.lorelens

import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.isExecutable

/**
 * Runs loreserver on loopback so integration tests need no external server.
 * The server takes its defaults from the binary, so no configuration is needed.
 */
class LoreTestServer private constructor(private val executable: Path) : AutoCloseable {

    private var process: Process? = null
    private lateinit var workingDirectory: Path

    val url: String get() = "lore://localhost:$PORT"

    fun start() {
        workingDirectory = Files.createTempDirectory("loreserver")

        process = ProcessBuilder(executable.toString())
            .directory(workingDirectory.toFile())
            .redirectErrorStream(true)
            .redirectOutput(workingDirectory.resolve("server.log").toFile())
            .start()

        if (!awaitPort()) {
            close()
            throw IllegalStateException(
                "loreserver did not accept connections on $PORT within ${STARTUP_TIMEOUT_MS}ms"
            )
        }
    }

    private fun awaitPort(): Boolean {
        val deadline = System.currentTimeMillis() + STARTUP_TIMEOUT_MS
        while (System.currentTimeMillis() < deadline) {
            if (process?.isAlive == false) return false
            try {
                Socket().use { it.connect(InetSocketAddress("localhost", PORT), 250) }
                return true
            } catch (_: IOException) {
                Thread.sleep(100)
            }
        }
        return false
    }

    override fun close() {
        process?.let { running ->
            running.destroy()
            if (!running.waitFor(10, java.util.concurrent.TimeUnit.SECONDS)) {
                running.destroyForcibly()
            }
        }
        process = null
    }

    companion object {
        private const val PORT = 41337
        private const val STARTUP_TIMEOUT_MS = 30_000L

        /**
         * The binary is fetched by the build for the host platform only, so a
         * platform Lore does not publish simply has no integration coverage.
         */
        fun findExecutable(): Path? {
            val directory = System.getProperty("lore.server.dir")?.let(Path::of) ?: return null
            return listOf("loreserver.exe", "loreserver")
                .map(directory::resolve)
                .firstOrNull { it.exists() && (it.isExecutable() || it.toString().endsWith(".exe")) }
        }

        fun isAvailable(): Boolean = findExecutable() != null

        fun startNew(): LoreTestServer =
            LoreTestServer(requireNotNull(findExecutable()) { "loreserver was not fetched" })
                .also { it.start() }
    }
}
