package com.manicpixie.cfttest.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manicpixie.cfttest.domain.model.Currency


@Entity
data class CurrencyEntity(
    val charCode: String,
    val name: String,
    val nominal: Int,
    val previous: Double,
    val value: Double,
    @PrimaryKey val id: Int? = null
) {
    fun toCurrency(): Currency{
        return Currency(
            charCode = charCode,
            name = name,
            nominal = nominal,
            previous = previous,
            value = value
        )
    }
}
