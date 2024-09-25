package io.tayviscon.theme.jetbrains.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(name = "TayvisconSetting", storages = [Storage("tayviscon-theme.xml")])
class TayvisconSettings : PersistentStateComponent<TayvisconState> {
    companion object {
        val instance: TayvisconSettings
            get() = ApplicationManager.getApplication().getService(TayvisconSettings::class.java)
    }

    private var myState = TayvisconState()

    var version: String
        get() = myState.version
        set(value) {
            myState.version = value
        }

    override fun getState(): TayvisconState {
        return myState
    }

    override fun loadState(state: TayvisconState) {
        myState = state
    }
}