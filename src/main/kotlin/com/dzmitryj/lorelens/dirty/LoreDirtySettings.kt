package com.dzmitryj.lorelens.dirty

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service

@Service(Service.Level.APP)
@State(name = "LoreLensSettings", storages = [Storage("lorelens.xml")])
class LoreDirtySettings : PersistentStateComponent<LoreDirtySettings.State> {

    data class State(
        var markEditsDirty: Boolean = true,
        var debounceMillis: Int = 300,
        var maxBatchSize: Int = 2_000,
        /**
         * Lore instance ids already reconciled once. Keyed on the instance UUID
         * rather than the path so a re-clone rescans and a move does not, and
         * so an asset repository is not walked on every project open.
         */
        var scannedInstances: MutableSet<String> = mutableSetOf(),
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

    fun needsInitialScan(instanceId: String): Boolean = instanceId !in state.scannedInstances

    fun markScanned(instanceId: String) {
        state.scannedInstances += instanceId
    }

    companion object {
        fun getInstance(): LoreDirtySettings = service()
    }
}
