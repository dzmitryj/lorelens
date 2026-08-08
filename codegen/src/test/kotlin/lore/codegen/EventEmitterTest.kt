package lore.codegen

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private val PREAMBLE = """
    #define LORE_INTERFACE_VERSION "9.9.9"
""".trimIndent()

/**
 * The emitter needs a whole event union to run, so these build the smallest
 * header that is still a valid one.
 */
private fun emit(payloads: String, arms: String, tags: String): Pair<String, Set<String>> {
    val header = HeaderParser.parse(
        """
        $PREAMBLE

        typedef struct lore_hash_t {
          uint8_t data[32];
        } lore_hash_t;

        $payloads

        typedef enum lore_event_id_t {
          $tags
        } lore_event_id_t;

        typedef struct lore_event_t {
          enum lore_event_id_t id;
          union {
            $arms
          };
        } lore_event_t;
        """.trimIndent(),
    )
    val emitter = EventEmitter(header, TypeMapper(header))
    return emitter.emit() to emitter.droppedFields
}

class EventEmitterTest {

    /**
     * A fixed C array declared inline, like `lore_hash_t parent[2]`. This was
     * dropped without a word, which is how revision parents went missing and
     * the log drew a straight line through merges.
     */
    @Test
    fun `a fixed array of structs becomes a list`() {
        val (code, dropped) = emit(
            payloads = """
                typedef struct lore_thing_event_data_t {
                  struct lore_hash_t revision;
                  struct lore_hash_t parent[2];
                } lore_thing_event_data_t;
            """.trimIndent(),
            arms = "struct lore_thing_event_data_t thing;",
            tags = "LORE_EVENT_THING = 0,",
        )

        assertTrue(code.contains("val parent: List<ByteArray>"), "expected a List property, got:\n$code")
        assertTrue(code.contains("LoreCopy.inlineArray("), "expected an inline array read, got:\n$code")
        assertEquals(emptySet(), dropped)
    }

    /** A field the emitter cannot represent has to be recorded, not swallowed. */
    @Test
    fun `an unrepresentable field is reported as dropped`() {
        val (_, dropped) = emit(
            payloads = """
                typedef struct lore_thing_event_data_t {
                  struct lore_hash_t revision;
                  void *opaque;
                } lore_thing_event_data_t;
            """.trimIndent(),
            arms = "struct lore_thing_event_data_t thing;",
            tags = "LORE_EVENT_THING = 0,",
        )

        assertEquals(setOf("lore_thing_event_data_t.opaque: Void*"), dropped)
    }
}
