package com.manicpixie.cfttest.domain.util

sealed class CurrenciesOrder {
    object Initial: CurrenciesOrder()
    object ByValue: CurrenciesOrder()
}