package com.currencyconverter.data.repository

import com.currencyconverter.data.BuildConfig
import com.currencyconverter.data.retrofit.ConverterAPI
import com.currencyconverter.domain.model.ConversionQuery
import com.currencyconverter.domain.repository.ConversionRepository

class ConversationRepositoryImpl(private val converter: ConverterAPI) : ConversionRepository {
    override suspend fun convertCurrency(query: ConversionQuery): Result<Double> {
        val apiKey = BuildConfig.EXCHANGE_RATES_API_KEY
        val response = converter.convert(
            apiKey = apiKey,
            fromCurrencyCode = query.from.code,
            toCurrencyCode = query.to.code,
            amount = query.amount
        )

        return response.body()
            ?.let { Result.success(it.result) }
            ?: Result.failure(Exception("Error ${response.code()}: ${response.errorBody()}"))

    }
}