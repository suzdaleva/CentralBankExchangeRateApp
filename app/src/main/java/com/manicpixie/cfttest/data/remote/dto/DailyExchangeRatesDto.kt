package com.manicpixie.cfttest.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.manicpixie.cfttest.domain.model.DailyExchangeRates

data class DailyExchangeRatesDto(
    @SerializedName("Date")
    val date: String,
    @SerializedName("PreviousDate")
    val previousDate: String,
    @SerializedName("PreviousURL")
    val previousURL: String,
    @SerializedName("Timestamp")
    val timestamp: String,
    @SerializedName("Valute")
    val valute: Map<String, CurrencyDto>
) {
    fun toDailyExchangeRates(): DailyExchangeRates {
        return DailyExchangeRates(
            date = date,
            previousDate = previousDate,
            previousURL = previousURL,
            timestamp = timestamp,
            valute = valute.values.toList().map{it.toCurrency()}
        )
    }
}