package com.dzmitryj.lorelens.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Collections
import kotlin.concurrent.thread

class LoreConsoleLogTest {

    /** The bug this guards: everything before the tab's first open was lost. */
    @Test
    fun `entries recorded before any subscriber replay on subscribe`() {
        val log = LoreConsoleLog()
        log.command("sync r172")
        log.error("merge failed")

        val replay = log.subscribe { }

        assertEquals(listOf("sync r172", "merge failed"), replay.map { it.text })
    }

    /** Replay and live delivery meet exactly: every entry seen once. */
    @Test
    fun `no entry falls between replay and subscription`() {
        repeat(20) {
            val log = LoreConsoleLog()
            val live = Collections.synchronizedList(mutableListOf<LoreConsoleLog.Entry>())

            val writer = thread { repeat(500) { n -> log.command("op$n") } }
            val replay = log.subscribe { live += it }
            writer.join()

            val seen = replay.map { it.text } + live.map { it.text }
            assertEquals("each entry exactly once", 500, seen.size)
            assertEquals(500, seen.distinct().size)
        }
    }

    @Test
    fun `the log is bounded`() {
        val log = LoreConsoleLog()
        repeat(3000) { log.command("op$it") }

        val replay = log.subscribe { }

        assertTrue(replay.size <= 2000)
        assertEquals("op2999", replay.last().text)
    }
}
