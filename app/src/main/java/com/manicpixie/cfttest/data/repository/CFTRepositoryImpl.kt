package com.manicpixie.cfttest.data.repository


import com.manicpixie.cfttest.common.Constants
import com.manicpixie.cfttest.data.local.CurrencyDao
import com.manicpixie.cfttest.data.local.entity.CurrencyEntity
import com.manicpixie.cfttest.data.remote.CFTApi
import com.manicpixie.cfttest.data.remote.dto.CurrencyDto
import com.manicpixie.cfttest.domain.repository.CFTRepository
import javax.inject.Inject

class CFTRepositoryImpl @Inject constructor(
    private val api: CFTApi,
    private val dao: CurrencyDao
) : CFTRepository {
    override suspend fun getCurrencies(): List<CurrencyDto> {
        val currencies = api.getCurrencies().valute.values.toList().onEach { it.name = Constants.currenciesNames[it.charCode]!![0] }
        dao.deleteAll()
        dao.insertCurrencies(currencies.map{it.toCurrencyEntity()})
        return currencies
    }

    override suspend fun getCurrencyByName(name: String): CurrencyEntity {
        return dao.getCurrencyByName(name)
    }
}