package com.dzmitryj.lorevcs.ffi

import com.dzmitryj.lorevcs.ffi.generated.lore_string_t
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

    fun <T> array(struct: MemorySegment, elementSize: Long, read: (MemorySegment) -> T): List<T> {
        val pointer = struct.get(ValueLayout.ADDRESS, PTR_OFFSET)
        val count = struct.get(ValueLayout.JAVA_LONG, COUNT_OFFSET)
        if (pointer.address() == 0L || count <= 0L) return emptyList()

        val base = pointer.reinterpret(count * elementSize)
        return (0 until count).map { index -> read(base.asSlice(index * elementSize, elementSize)) }
    }
}
