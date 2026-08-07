package com.dzmitryj.lorevcs.ffi

import com.dzmitryj.lorevcs.ffi.generated.CompleteEvent
import com.dzmitryj.lorevcs.ffi.generated.LoreEvent
import com.dzmitryj.lorevcs.ffi.generated.LoreEventReader
import com.dzmitryj.lorevcs.ffi.generated.LoreStatus
import com.dzmitryj.lorevcs.ffi.generated.lore_event_callback_config_t
import com.dzmitryj.lorevcs.ffi.generated.lore_event_t
import com.intellij.openapi.diagnostic.logger
import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

data class LoreResult(val returnCode: Int, val status: Int?, val events: List<LoreEvent>) {

    val succeeded: Boolean get() = returnCode == 0 && (status ?: 0) == 0

    fun statusOrNull(): LoreStatus? = status?.let { LoreStatus.of(it) }

    inline fun <reified T : LoreEvent> filter(): List<T> = events.filterIsInstance<T>()
}

class LoreCallException(val result: LoreResult, message: String) : RuntimeException(message)

/**
 * Owns the single upcall stub every operation shares. Lore invokes it from its
 * own worker threads, so dispatch must stay non-blocking and must never let a
 * Throwable cross back into Rust.
 */
object EventPump {

    private val log = logger<EventPump>()
    private val arena: Arena = Arena.ofShared()
    private val calls = ConcurrentHashMap<Long, Sink>()
    private val nextCallId = AtomicLong(1)

    private class Sink(val observer: ((LoreEvent) -> Unit)?) {
        val events = mutableListOf<LoreEvent>()
    }

    private val stub: MemorySegment by lazy {
        val target = MethodHandles.lookup().findStatic(
            EventPump::class.java,
            "dispatch",
            MethodType.methodType(Void.TYPE, MemorySegment::class.java, Long::class.javaPrimitiveType),
        )
        LoreLinker.upcall(
            target,
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_LONG),
            arena,
        )
    }

    @JvmStatic
    fun dispatch(event: MemorySegment, context: Long) {
        try {
            val sink = calls[context] ?: return
            val decoded = LoreEventReader.read(event.reinterpret(lore_event_t.SIZE))
            synchronized(sink) { sink.events += decoded }
            sink.observer?.invoke(decoded)
        } catch (t: Throwable) {
            log.error("Lore event callback failed for call $context", t)
        }
    }

    /**
     * Runs one synchronous operation. [invoke] receives the callback config to
     * pass through and returns the operation's own return code.
     *
     * [observer] sees events as they arrive, on Lore's worker thread, so it must
     * not block and must not take IDE locks.
     */
    fun call(
        arena: Arena,
        observer: ((LoreEvent) -> Unit)? = null,
        invoke: (MemorySegment) -> Int,
    ): LoreResult {
        val id = nextCallId.getAndIncrement()
        val sink = Sink(observer)
        calls[id] = sink

        val returnCode = try {
            val config = arena.allocate(lore_event_callback_config_t.LAYOUT)
            lore_event_callback_config_t.user_context(config, id)
            lore_event_callback_config_t.func(config, stub)
            invoke(config)
        } finally {
            calls.remove(id)
        }

        val events = synchronized(sink) { sink.events.toList() }
        val status = events.filterIsInstance<CompleteEvent>().lastOrNull()?.status
        return LoreResult(returnCode, status, events)
    }
}
