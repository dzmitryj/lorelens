package com.dzmitryj.lorelens.ffi

import com.dzmitryj.lorelens.ffi.generated.lore_global_args_t
import com.dzmitryj.lorelens.ffi.generated.lore_string_array_t
import com.dzmitryj.lorelens.ffi.generated.lore_string_t
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import java.nio.file.Path

/**
 * Builds argument structs. The library copies argument data before the call
 * starts, so an arena scoped to one call is enough.
 */
class LoreArgs(private val arena: Arena) {

    fun globals(repositoryRoot: Path?): MemorySegment {
        val globals = arena.allocate(lore_global_args_t.LAYOUT)
        repositoryRoot?.let {
            writeString(lore_global_args_t.repository_path(globals), it.toString())
            writeString(lore_global_args_t.working_directory(globals), it.toString())
        }
        return globals
    }

    fun writeStrings(target: MemorySegment, values: List<String>) {
        if (values.isEmpty()) {
            lore_string_array_t.ptr(target, MemorySegment.NULL)
            lore_string_array_t.count(target, 0L)
            return
        }

        val elements = arena.allocate(lore_string_t.LAYOUT, values.size.toLong())
        values.forEachIndexed { index, value ->
            writeString(elements.asSlice(index * lore_string_t.SIZE, lore_string_t.SIZE), value)
        }
        lore_string_array_t.ptr(target, elements)
        lore_string_array_t.count(target, values.size.toLong())
    }

    fun writeString(target: MemorySegment, value: String) {
        val bytes = value.toByteArray(Charsets.UTF_8)
        val buffer = arena.allocate(bytes.size + 1L)
        MemorySegment.copy(bytes, 0, buffer, ValueLayout.JAVA_BYTE, 0L, bytes.size)
        lore_string_t.string(target, buffer)
        lore_string_t.length(target, bytes.size.toLong())
    }
}
