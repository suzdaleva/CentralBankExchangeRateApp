package com.manicpixie.cfttest.domain.repository



import com.manicpixie.cfttest.data.local.entity.CurrencyEntity
import com.manicpixie.cfttest.data.remote.dto.CurrencyDto


interface CFTRepository {
     suspend fun getCurrencies(): List<CurrencyDto>
     suspend fun getCurrencyByName(name: String): CurrencyEntity
}