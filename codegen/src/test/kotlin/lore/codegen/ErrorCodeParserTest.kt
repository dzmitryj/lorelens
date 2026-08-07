package lore.codegen

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ErrorCodeParserTest {

    @Test
    fun `reads code, name and message`() {
        val codes = ErrorCodeParser.parse(
            """
            // FFI code 12
            #[derive(Debug, Clone, Error, FfiError)]
            #[error("Not authenticated")]
            #[ffi_code(12)]
            pub struct NotAuthenticated;
            """.trimIndent()
        )

        assertEquals(listOf(LoreErrorCode(12, "NotAuthenticated", "Not authenticated")), codes)
    }

    @Test
    fun `keeps the literal when the attribute carries format arguments`() {
        val codes = ErrorCodeParser.parse(
            """
            #[error("Address not found: {}", AddressNotFound::format_address(&self.address))]
            #[ffi_code(2)]
            pub struct AddressNotFound {
                pub address: Address,
            }
            """.trimIndent()
        )

        assertEquals("Address not found: {}", codes.single().message)
    }

    @Test
    fun `ignores types that carry no ffi code`() {
        val codes = ErrorCodeParser.parse(
            """
            #[error("internal")]
            pub struct Internal;

            #[error("slow down")]
            #[ffi_code(5)]
            pub struct SlowDown;
            """.trimIndent()
        )

        assertEquals(listOf(5), codes.map { it.code })
    }

    @Test
    fun `an empty registry fails the build`() {
        assertFailsWith<IllegalStateException> {
            ErrorCodeParser.parse("pub struct Nothing;")
        }
    }

    @Test
    fun `duplicate codes fail the build`() {
        assertFailsWith<IllegalStateException> {
            ErrorCodeParser.parse(
                """
                #[ffi_code(1)]
                pub struct A;
                #[ffi_code(1)]
                pub struct B;
                """.trimIndent()
            )
        }
    }
}
