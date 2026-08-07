package com.dzmitryj.lorelens.model

import com.dzmitryj.lorelens.ffi.generated.Address
import com.dzmitryj.lorelens.ffi.generated.Binary
import com.dzmitryj.lorelens.ffi.generated.Metadata
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class LoreMetadataTest {

    @Test
    fun `splits a git style message into subject and body`() {
        val metadata = LoreMetadata(
            mapOf(
                LoreMetadata.MESSAGE to
                    "feat(vehicle): default the driver camera to first person\n" +
                    "\n" +
                    "Third person starts off and only unlocks under a flag.\n" +
                    "Seated look drops the clamp.",
            ),
        )

        assertEquals("feat(vehicle): default the driver camera to first person", metadata.subject)
        assertEquals(
            "Third person starts off and only unlocks under a flag.\nSeated look drops the clamp.",
            metadata.body,
        )
    }

    @Test
    fun `a subject only message has no body`() {
        val metadata = LoreMetadata(mapOf(LoreMetadata.MESSAGE to "chore: tidy up"))

        assertEquals("chore: tidy up", metadata.subject)
        assertNull(metadata.body)
    }

    /** Revision 70 of the real repository has one. */
    @Test
    fun `an empty message yields no subject`() {
        listOf("", "   ", "\n\n").forEach { raw ->
            val metadata = LoreMetadata(mapOf(LoreMetadata.MESSAGE to raw))
            assertNull("expected no subject for ${raw.replace("\n", "\\n")}", metadata.subject)
            assertNull(metadata.body)
        }
    }

    /**
     * Only StringValue used to survive, which silently discarded the numeric
     * timestamp and the cherry-picked-from hash.
     */
    @Test
    fun `renders every metadata variant rather than dropping it`() {
        assertEquals("hello", LoreMetadata.render("k", Metadata.StringValue("hello")))
        assertEquals("1754400811000", LoreMetadata.render("k", Metadata.NumericValue(1754400811000L)))
        assertEquals("true", LoreMetadata.render("k", Metadata.BooleanValue(1)))
        assertEquals("0aff", LoreMetadata.render("k", Metadata.HashValue(byteArrayOf(0x0a, 0xff.toByte()))))
        assertEquals("01", LoreMetadata.render("k", Metadata.ContextValue(byteArrayOf(0x01))))
        assertEquals(
            "0a-ff",
            LoreMetadata.render(
                "k",
                Metadata.AddressValue(Address(byteArrayOf(0x0a), byteArrayOf(0xff.toByte()))),
            ),
        )
        assertNull(LoreMetadata.render("k", Metadata.BinaryValue(Binary(4))))
        assertNull(LoreMetadata.render("k", Metadata.Unknown(99)))
    }

    @Test
    fun `accepts a timestamp as rfc 1123 text or as epoch seconds and millis`() {
        val text = LoreMetadata(mapOf(LoreMetadata.TIMESTAMP to "Wed, 5 Aug 2026 19:53:31 +0000"))
        assertEquals(1785959611000L, text.timestampMillis)

        val seconds = LoreMetadata(mapOf(LoreMetadata.TIMESTAMP to "1785959611"))
        assertEquals(1785959611000L, seconds.timestampMillis)

        val millis = LoreMetadata(mapOf(LoreMetadata.TIMESTAMP to "1785959611000"))
        assertEquals(1785959611000L, millis.timestampMillis)

        assertNull(LoreMetadata(mapOf(LoreMetadata.TIMESTAMP to "not a date")).timestampMillis)
    }

    @Test
    fun `exposes author and committer separately`() {
        val metadata = LoreMetadata(
            mapOf(
                LoreMetadata.CREATED_BY to "jon.neal@example.com",
                LoreMetadata.COMMITTED_BY to "dimi.mitchell@example.com",
            ),
        )

        assertEquals("jon.neal@example.com", metadata.author)
        assertEquals("dimi.mitchell@example.com", metadata.committer)
    }
}
