package com.dzmitryj.lorelens.ffi

import com.dzmitryj.lorelens.ffi.generated.lore_string_t
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

/**
 * Everything an event carries is borrowed for the duration of the callback, so
 * every read here copies. Holding a MemorySegment past the upcall is a
 * use-after-free that takes the IDE down with it.
 */
object LoreCopy {

    private const val PTR_OFFSET = 0L
    private const val COUNT_OFFSET = 8L

    fun string(struct: MemorySegment): String {
        val pointer = lore_string_t.string(struct)
        val length = lore_string_t.length(struct)
        if (pointer.address() == 0L || length <= 0L) return ""
        return String(pointer.reinterpret(length).toArray(ValueLayout.JAVA_BYTE), Charsets.UTF_8)
    }

    fun bytes(struct: MemorySegment, length: Long): ByteArray {
        val pointer = struct.get(ValueLayout.ADDRESS, PTR_OFFSET)
        if (pointer.address() == 0L || length <= 0L) return ByteArray(0)
        return pointer.reinterpret(length).toArray(ValueLayout.JAVA_BYTE)
    }

    fun fixedBytes(slice: MemorySegment): ByteArray = slice.toArray(ValueLayout.JAVA_BYTE)

    /**
     * A fixed-length C array declared inline in a struct, such as
     * `lore_hash_t parent[2]`. The accessor hands over the whole sequence, so
     * unlike [array] there is no pointer to follow and no count to read.
     */
    fun <T> inlineArray(sequence: MemorySegment, count: Int, elementSize: Long, read: (MemorySegment) -> T): List<T> =
        (0 until count).map { index -> read(sequence.asSlice(index * elementSize, elementSize)) }

    fun <T> array(struct: MemorySegment, elementSize: Long, read: (MemorySegment) -> T): List<T> {
        val pointer = struct.get(ValueLayout.ADDRESS, PTR_OFFSET)
        val count = struct.get(ValueLayout.JAVA_LONG, COUNT_OFFSET)
        if (pointer.address() == 0L || count <= 0L) return emptyList()

        val base = pointer.reinterpret(count * elementSize)
        return (0 until count).map { index -> read(base.asSlice(index * elementSize, elementSize)) }
    }
}
