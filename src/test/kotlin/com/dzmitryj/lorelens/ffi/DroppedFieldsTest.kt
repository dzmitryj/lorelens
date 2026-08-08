package com.dzmitryj.lorelens.ffi

import org.junit.Assert.assertEquals
import org.junit.Test
import java.nio.file.Files
import java.nio.file.Path

/**
 * Pins the fields the generator could not represent.
 *
 * A skipped field leaves no trace in the generated code, and that has cost real
 * data twice: metadata values vanished when tagged unions were dropped, and
 * revision parents vanished when fixed arrays were, which left the log drawing
 * a straight line through merges. Adding a line here should mean someone
 * decided the field genuinely carries nothing worth exposing.
 */
class DroppedFieldsTest {

    private val report: Path =
        Path.of("src/main/kotlin/com/dzmitryj/lorelens/ffi/generated/dropped-fields.txt")

    @Test
    fun `the generator drops only fields that carry nothing`() {
        val actual = Files.readAllLines(report).filter { it.isNotBlank() }

        assertEquals(
            // A raw pointer with no length beside it; there is nothing to copy.
            listOf("lore_binary_t.payload: Void*"),
            actual,
        )
    }
}
