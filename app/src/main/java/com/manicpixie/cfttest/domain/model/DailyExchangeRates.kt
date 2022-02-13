package com.manicpixie.cfttest.domain.model


data class DailyExchangeRates(
    val date: String,
    val previousDate: String,
    val previousURL: String,
    val timestamp: String,
    val valute: List<Currency>
)
