package com.dzmitryj.lorevcs.ffi

import com.dzmitryj.lorevcs.api.LoreClient
import com.dzmitryj.lorevcs.ffi.generated.AllLayouts
import com.dzmitryj.lorevcs.ffi.generated.CompleteEvent
import com.dzmitryj.lorevcs.ffi.generated.LoreBuildInfo
import com.dzmitryj.lorevcs.ffi.generated.LoreStatus
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.io.path.createTempDirectory

class LoreNativeTest {

    @Test
    fun `bundled library matches the version the bindings were generated against`() {
        assertEquals(LoreBuildInfo.INTERFACE_VERSION, LoreClient.version().substringBefore('+'))
        LoreClient.verifyVersion()
    }

    /**
     * FFM validates alignment when a layout is constructed, so touching every
     * generated layout turns a padding mistake into a failure here instead of a
     * silently wrong read at runtime.
     */
    @Test
    fun `every generated layout is well formed`() {
        assertTrue(AllLayouts.layouts.size > 400)
        AllLayouts.layouts.forEach { layout ->
            assertTrue(layout.name().isPresent)
            assertTrue(layout.byteSize() > 0)
        }
    }

    /**
     * Exercises the whole event path -- upcall stub, correlation by
     * user_context, tagged-union decode -- against a directory that is not a
     * repository, so it needs no server.
     */
    @Test
    fun `a failing call reports through the event pump`() {
        val notARepository = createTempDirectory("lore-test").toFile().also { it.deleteOnExit() }

        val result = LoreClient.repositoryInfo(notARepository.toPath())

        assertFalse("expected failure for a non-repository", result.succeeded)
        assertTrue("expected at least one event", result.events.isNotEmpty())
        assertTrue(
            "expected a Complete event, got ${result.events.map { it::class.simpleName }}",
            result.events.any { it is CompleteEvent },
        )
    }

    @Test
    fun `error codes resolve`() {
        assertEquals(LoreStatus.NOT_AUTHENTICATED, LoreStatus.of(12))
        assertEquals(LoreStatus.NOT_SUPPORTED, LoreStatus.of(18))
        assertEquals(null, LoreStatus.of(9999))
    }
}
