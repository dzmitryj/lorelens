package lore.codegen

import kotlin.test.Test
import kotlin.test.assertEquals

private fun header(body: String) =
    HeaderParser.parse("#define LORE_INTERFACE_VERSION \"9.9.9\"\n\n$body")

class LayoutTest {

    @Test
    fun `pads to natural alignment and rounds the struct size up`() {
        val parsed = header(
            """
            typedef struct lore_mixed_t {
              uint8_t flag;
              uint32_t count;
              uint8_t tail;
            } lore_mixed_t;
            """.trimIndent()
        )
        val plan = TypeMapper(parsed).layoutOf(parsed.struct("lore_mixed_t").fields)

        assertEquals(listOf(0L, 4L, 8L), plan.members.map { it.offset })
        assertEquals(4L, plan.alignment)
        assertEquals(12L, plan.size)
    }

    @Test
    fun `nested structs contribute their own alignment`() {
        val parsed = header(
            """
            typedef struct lore_string_t {
              const char *string;
              uintptr_t length;
            } lore_string_t;

            typedef struct lore_outer_t {
              uint8_t flag;
              struct lore_string_t name;
            } lore_outer_t;
            """.trimIndent()
        )
        val plan = TypeMapper(parsed).layoutOf(parsed.struct("lore_outer_t").fields)

        assertEquals(listOf(0L, 8L), plan.members.map { it.offset })
        assertEquals(24L, plan.size)
    }

    @Test
    fun `byte arrays have single byte alignment`() {
        val parsed = header(
            """
            typedef struct lore_hash_t {
              uint8_t data[32];
            } lore_hash_t;
            """.trimIndent()
        )
        val types = TypeMapper(parsed)
        val plan = types.layoutOf(parsed.struct("lore_hash_t").fields)

        assertEquals(32L, plan.size)
        assertEquals(1L, plan.alignment)
    }

    @Test
    fun `an alias resolves to the type it names`() {
        val parsed = header(
            """
            typedef uint32_t lore_node_id_t;

            typedef struct lore_node_t {
              lore_node_id_t id;
            } lore_node_t;
            """.trimIndent()
        )
        val types = TypeMapper(parsed)

        assertEquals(4L, types.sizeOf(parsed.struct("lore_node_t").fields.single().type))
    }
}
