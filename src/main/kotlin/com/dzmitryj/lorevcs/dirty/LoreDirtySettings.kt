package com.dzmitryj.lorevcs.dirty

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service

@Service(Service.Level.APP)
@State(name = "LoreDirtySettings", storages = [Storage("lore.xml")])
class LoreDirtySettings : PersistentStateComponent<LoreDirtySettings.State> {

    data class State(
        var markEditsDirty: Boolean = true,
        var debounceMillis: Int = 300,
        var maxBatchSize: Int = 2_000,
    )

    private var state = State()

    override fun getState(): State = state

    override fun loadState(state: State) {
        this.state = state
    }

    var markEditsDirty: Boolean
        get() = state.markEditsDirty
        set(value) {
            state.markEditsDirty = value
        }

    val debounceMillis: Int get() = state.debounceMillis

    val maxBatchSize: Int get() = state.maxBatchSize

    companion object {
        fun getInstance(): LoreDirtySettings = service()
    }
}
