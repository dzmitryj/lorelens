package com.dzmitryj.lorevcs.api

import com.dzmitryj.lorevcs.ffi.EventPump
import com.dzmitryj.lorevcs.ffi.LoreArgs
import com.dzmitryj.lorevcs.ffi.generated.LoreEvent
import com.dzmitryj.lorevcs.ffi.generated.LoreFunctions
import com.dzmitryj.lorevcs.ffi.generated.Metadata
import com.dzmitryj.lorevcs.ffi.generated.MetadataEvent
import com.dzmitryj.lorevcs.ffi.generated.RevisionHistoryEntryEvent
import com.dzmitryj.lorevcs.ffi.generated.lore_revision_history_args_t
import com.dzmitryj.lorevcs.model.LoreRevisionId
import java.lang.foreign.Arena
import java.nio.file.Path

data class LoreHistoryEntry(
    val revision: LoreRevisionId,
    val number: Long,
    val metadata: Map<String, String>,
) {
    val message: String? get() = metadata[MESSAGE_KEY]

    private companion object {
        const val MESSAGE_KEY = "message"
    }
}

object LoreHistoryApi {

    /**
     * History streams one entry event per revision, with that revision's
     * metadata arriving as separate events in between. Entries are therefore
     * assembled by attributing each metadata event to the entry it follows.
     */
    fun history(root: Path, limit: Int = 50): List<LoreHistoryEntry> = Arena.ofConfined().use { arena ->
        val args = LoreArgs(arena)
        val globals = args.globals(root)
        val options = arena.allocate(lore_revision_history_args_t.LAYOUT)
        lore_revision_history_args_t.length(options, limit)

        val result = LoreClient.require(
            EventPump.call(arena) { callback ->
                LoreFunctions.lore_revision_history.invokeExact(globals, options, callback) as Int
            },
            "history",
        )

        assemble(result.events)
    }

    private fun assemble(events: List<LoreEvent>): List<LoreHistoryEntry> {
        val entries = mutableListOf<LoreHistoryEntry>()
        var revision: LoreRevisionId? = null
        var number = 0L
        var metadata = mutableMapOf<String, String>()

        fun flush() {
            revision?.let { entries += LoreHistoryEntry(it, number, metadata.toMap()) }
            revision = null
            metadata = mutableMapOf()
        }

        events.forEach { event ->
            when (event) {
                is RevisionHistoryEntryEvent -> {
                    flush()
                    revision = LoreRevisionId(event.revision)
                    number = event.revision_number
                }

                is MetadataEvent -> (event.value as? Metadata.StringValue)?.let {
                    metadata[event.key] = it.value
                }

                else -> Unit
            }
        }
        flush()
        return entries
    }
}
