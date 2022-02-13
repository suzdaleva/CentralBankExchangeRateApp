package com.manicpixie.cfttest.data.remote


import com.manicpixie.cfttest.data.remote.dto.DailyExchangeRatesDto
import retrofit2.http.GET

interface CFTApi {
    @GET("daily_json.js")
    suspend fun getCurrencies(): DailyExchangeRatesDto
}