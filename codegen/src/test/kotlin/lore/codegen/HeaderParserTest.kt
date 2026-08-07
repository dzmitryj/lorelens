package lore.codegen

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

private val PREAMBLE = """
    #define LORE_INTERFACE_VERSION "9.9.9"
""".trimIndent()

private fun parse(body: String) = HeaderParser.parse("$PREAMBLE\n\n$body")

class HeaderParserTest {

    @Test
    fun `reads the interface version`() {
        assertEquals("9.9.9", parse("").interfaceVersion)
    }

    @Test
    fun `reads a struct with documentation`() {
        val header = parse(
            """
            // A string.
            typedef struct lore_string_t {
              // Pointer to the data.
              const char *string;
              uintptr_t length;
            } lore_string_t;
            """.trimIndent()
        )

        val struct = header.struct("lore_string_t")
        assertEquals(listOf("A string."), struct.doc)
        assertEquals(listOf("string", "length"), struct.fields.map { it.name })
        assertEquals(listOf("Pointer to the data."), struct.fields[0].doc)
        assertEquals(CType.Pointer(CType.Primitive("char")), struct.fields[0].type)
    }

    @Test
    fun `reads enums with implicit and explicit values`() {
        val header = parse(
            """
            typedef enum lore_kind_t {
              LORE_KIND_A = 0,
              LORE_KIND_B,
              LORE_KIND_C = 7,
            } lore_kind_t;
            """.trimIndent()
        )

        assertEquals(listOf(0L, 1L, 7L), header.enums.single().constants.map { it.value })
    }

    @Test
    fun `reads a named enum declared without a typedef`() {
        val header = parse(
            """
            enum lore_event_id_t {
              LORE_EVENT_PROGRESS,
              LORE_EVENT_ERROR,
            };
            """.trimIndent()
        )

        assertTrue(header.isEnum("lore_event_id_t"))
        assertEquals(2, header.enums.single().constants.size)
    }

    @Test
    fun `reads array fields, anonymous unions and function pointers`() {
        val header = parse(
            """
            typedef struct lore_hash_t {
              uint8_t data[32];
            } lore_hash_t;

            typedef struct lore_event_t {
              uint32_t tag;
              union {
                struct lore_hash_t hash;
                uint64_t id;
              };
            } lore_event_t;

            typedef struct lore_callback_t {
              void (*func)(const struct lore_event_t *event, uint64_t context);
            } lore_callback_t;
            """.trimIndent()
        )

        assertEquals(CType.Array(CType.Primitive("uint8_t"), 32), header.struct("lore_hash_t").fields.single().type)

        val union = header.struct("lore_event_t").fields[1]
        assertEquals("", union.name)
        assertTrue(union.type is CType.InlineUnion)

        assertEquals(CType.Pointer(CType.Void), header.struct("lore_callback_t").fields.single().type)
    }

    @Test
    fun `reads functions including multi-line signatures`() {
        val header = parse(
            """
            // Does a thing.
            int32_t lore_do(const struct lore_global_args_t *globals,
                            struct lore_callback_t callback);

            const char *lore_version(void);
            """.trimIndent()
        )

        val doIt = header.functions.first { it.name == "lore_do" }
        assertEquals(listOf("Does a thing."), doIt.doc)
        assertEquals(listOf("globals", "callback"), doIt.params.map { it.name })
        assertEquals(CType.Primitive("int32_t"), doIt.returnType)

        val version = header.functions.first { it.name == "lore_version" }
        assertTrue(version.params.isEmpty())
    }

    @Test
    fun `distinguishes an opaque typedef from an alias to another struct`() {
        val header = parse(
            """
            typedef struct lore_context_t { uint8_t data[16]; } lore_context_t;
            typedef struct lore_context_t lore_branch_id_t;
            typedef uint32_t lore_node_id_t;
            """.trimIndent()
        )

        assertEquals(CType.StructRef("lore_context_t"), header.alias("lore_branch_id_t")?.type)
        assertEquals(CType.Primitive("uint32_t"), header.alias("lore_node_id_t")?.type)
    }

    @Test
    fun `an unrecognised construct fails the build`() {
        assertFailsWith<HeaderParseException> {
            parse(
                """
                typedef struct lore_broken_t {
                  this is not a field
                } lore_broken_t;
                """.trimIndent()
            )
        }
    }

    @Test
    fun `an unrecognised type fails the build`() {
        assertFailsWith<HeaderParseException> {
            parse(
                """
                typedef struct lore_broken_t {
                  int (weird) thing;
                } lore_broken_t;
                """.trimIndent()
            )
        }
    }
}
