package com.example.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val BUTTON_WAS_CLICKED = "BUTTON_WAS_CLICKED"


class CheatViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    var buttonWasClicked: Boolean
        get() = savedStateHandle.get(BUTTON_WAS_CLICKED) ?: false
        set(value) = savedStateHandle.set(BUTTON_WAS_CLICKED, value)
}