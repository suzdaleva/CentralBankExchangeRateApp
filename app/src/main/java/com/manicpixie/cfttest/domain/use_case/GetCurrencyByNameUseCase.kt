package com.manicpixie.cfttest.domain.use_case



import com.manicpixie.cfttest.domain.model.Currency
import com.manicpixie.cfttest.domain.repository.CFTRepository
import javax.inject.Inject

class GetCurrencyByNameUseCase @Inject constructor(
    private val repository: CFTRepository
) {
    suspend operator fun invoke(name: String): Currency  {
        return repository.getCurrencyByName(name).toCurrency()
    }
}