package com.currencyconverter.data.retrofit

import com.currencyconverter.data.model.CurrencyApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RatesAPI {
    @GET("/v3/latest")
    suspend fun latestRate(
        @Header("apikey") apiKey: String,
        @Query("base_currency") fromCurrencyCode: String,
        @Query("currencies") toCurrencyCode: String
    ): Response<CurrencyApi>
}