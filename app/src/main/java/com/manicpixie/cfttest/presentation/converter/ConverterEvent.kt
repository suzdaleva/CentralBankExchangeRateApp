package com.manicpixie.cfttest.presentation.converter

import androidx.compose.ui.focus.FocusState

sealed class ConverterEvent{
    data class EnteredText(val value: String) : ConverterEvent()
    data class ChangeTextFocus(val focusState: FocusState) : ConverterEvent()
    data class Filter(val currency: String): ConverterEvent()
    object Calculate: ConverterEvent()

}
