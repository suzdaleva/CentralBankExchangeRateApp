package com.manicpixie.cfttest.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.manicpixie.cfttest.data.local.entity.CurrencyEntity
import com.manicpixie.cfttest.domain.model.Currency
import kotlinx.serialization.Serializable


@Serializable
data class CurrencyDto(
    @SerializedName("CharCode")
    val charCode: String,
    @SerializedName("ID")
    val id: String,
    @SerializedName("Name")
    var name: String,
    @SerializedName("Nominal")
    val nominal: Int,
    @SerializedName("NumCode")
    val numCode: String,
    @SerializedName("Previous")
    val previous: Double,
    @SerializedName("Value")
    val value: Double
) {
    fun toCurrency(): Currency {
        return Currency(
            charCode = charCode,
            name = name,
            nominal = nominal,
            previous = previous,
            value = value
        )
    }

    fun toCurrencyEntity(): CurrencyEntity {
        return CurrencyEntity(
            charCode = charCode,
            name = name,
            nominal = nominal,
            previous = previous,
            value = value
        )
    }
}


