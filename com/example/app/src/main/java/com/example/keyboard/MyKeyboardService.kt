package com.example.keyboard

import android.inputmethodservice.InputMethodService
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

class MyKeyboardService : InputMethodService(), 
    LifecycleOwner, ViewModelStoreOwner, SavedStateRegistryOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)
    override val lifecycle: LifecycleRegistry get() = lifecycleRegistry

    private val store = ViewModelStore()
    override val viewModelStore: ViewModelStore get() = store

    private val savedStateRegistryController = SavedStateRegistryController.create(this)
    override val savedStateRegistry: SavedStateRegistry 
        get() = savedStateRegistryController.savedStateRegistry

    var isUppercase by mutableStateOf(false)

    override fun onCreate() {
        super.onCreate()
        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onCreateInputView(): View {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)

        return ComposeView(this).apply {
            setViewTreeLifecycleOwner(this@MyKeyboardService)
            setViewTreeViewModelStoreOwner(this@MyKeyboardService)
            setViewTreeSavedStateRegistryOwner(this@MyKeyboardService)

            setContent {
                KeyboardRootView(
                    isUppercase = isUppercase,
                    onAction = { action ->
                        handleKeyboardAction(action)
                    }
                )
            }
        }
    }

    private fun handleKeyboardAction(action: KeyboardAction) {
        val inputConnection = currentInputConnection
        when (action) {
            is KeyboardAction.Character -> {
                val textToCommit = if (isUppercase) action.char.uppercase() else action.char
                inputConnection?.commitText(textToCommit, 1)
                if (isUppercase) isUppercase = false
            }
            is KeyboardAction.Delete -> {
                inputConnection?.deleteSurroundingText(1, 0)
            }
            is KeyboardAction.Shift -> {
                isUppercase = !isUppercase
            }
            is KeyboardAction.Space -> {
                inputConnection?.commitText(" ", 1)
            }
            is KeyboardAction.Done -> {
                inputConnection?.performEditorAction(android.view.inputmethod.EditorInfo.IME_ACTION_DONE)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}
