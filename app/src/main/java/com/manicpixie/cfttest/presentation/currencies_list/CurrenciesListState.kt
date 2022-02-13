package com.manicpixie.cfttest.presentation.currencies_list

import com.manicpixie.cfttest.domain.model.Currency
import com.manicpixie.cfttest.domain.util.CurrenciesOrder

data class CurrenciesListState(
    val currencies: List<Currency> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val currenciesOrder: CurrenciesOrder = CurrenciesOrder.Initial
)
