package com.manicpixie.cfttest.domain.use_case

import com.manicpixie.cfttest.common.Constants
import com.manicpixie.cfttest.common.Resource
import com.manicpixie.cfttest.domain.model.Currency
import com.manicpixie.cfttest.domain.repository.CFTRepository
import com.manicpixie.cfttest.domain.util.CurrenciesOrder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val repository: CFTRepository
) {

    operator fun invoke(currenciesOrder: CurrenciesOrder = CurrenciesOrder.Initial): Flow<Resource<List<Currency>>> =
        flow {
            while(true) {
                try {
                    emit(Resource.Loading<List<Currency>>())
                    val currencies = when (currenciesOrder) {
                        is CurrenciesOrder.Initial -> repository.getCurrencies()
                            .map { it.toCurrency() }.onEach { it.name = Constants.currenciesNames[it.charCode]!![0] }
                        is CurrenciesOrder.ByValue -> repository.getCurrencies()
                            .map { it.toCurrency() }
                            .sortedBy { it.value/it.nominal }.onEach { it.name = Constants.currenciesNames[it.charCode]!![0] }
                    }
                    emit(Resource.Success<List<Currency>>(currencies))
                } catch (e: HttpException) {
                    emit(Resource.Error<List<Currency>>("Упс, что-то пошло не так"))
                } catch (e: IOException) {
                    emit(Resource.Error<List<Currency>>("Упс, что-то пошло не так"))
                }
                delay(900000)

            }
        }
}


