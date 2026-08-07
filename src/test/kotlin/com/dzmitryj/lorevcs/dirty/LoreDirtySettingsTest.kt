package com.dzmitryj.lorevcs.dirty

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LoreDirtySettingsTest {

    @Test
    fun `a repository is scanned once and skipped afterwards`() {
        val settings = LoreDirtySettings()

        assertTrue(settings.needsInitialScan("019f-a"))

        settings.markScanned("019f-a")

        assertFalse(settings.needsInitialScan("019f-a"))
        assertTrue("a different checkout must still be scanned", settings.needsInitialScan("019f-b"))
    }

    @Test
    fun `scanned repositories survive a reload of persisted state`() {
        val saved = LoreDirtySettings().apply { markScanned("019f-a") }.state

        val restored = LoreDirtySettings().apply { loadState(saved) }

        assertFalse(restored.needsInitialScan("019f-a"))
    }
}
