package com.manicpixie.cfttest.presentation.currencies_list

import androidx.compose.ui.focus.FocusState
import com.manicpixie.cfttest.domain.util.CurrenciesOrder


sealed class CurrenciesListEvent{
    data class EnteredQuery(val value: String, val currenciesOrder: CurrenciesOrder) : CurrenciesListEvent()
    data class Order(val currenciesOrder: CurrenciesOrder): CurrenciesListEvent()
    data class ChangeQueryFocus(val focusState: FocusState) : CurrenciesListEvent()
}