package com.example.keyboard

sealed interface KeyboardAction {
    data class Character(val char: String) : KeyboardAction
    object Delete : KeyboardAction
    object Shift : KeyboardAction
    object Space : KeyboardAction
    object Done : KeyboardAction
}
