package com.currencyconverter.data.retrofit

import com.currencyconverter.data.model.CurrencyConversion
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ConverterAPI {
    @GET("/v1/convert")
    suspend fun convert(
        @Query("access_key") apiKey: String,
        @Query("from") fromCurrencyCode: String,
        @Query("to") toCurrencyCode: String,
        @Query("amount") amount: Double
    ): Response<CurrencyConversion>
}