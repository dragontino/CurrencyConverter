package com.currencyconverter.data.repository

import android.util.Log
import com.currencyconverter.data.BuildConfig
import com.currencyconverter.data.model.CurrencyApi
import com.currencyconverter.data.retrofit.RatesAPI
import com.currencyconverter.domain.model.ConversionQuery
import com.currencyconverter.domain.model.ConversionResponse
import com.currencyconverter.domain.repository.ConversionRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ConversationRepositoryImpl(private val converter: RatesAPI) : ConversionRepository {
    override suspend fun convertCurrency(query: ConversionQuery): Result<ConversionResponse> {
        val apiKey = BuildConfig.CURRENCY_API_KEY
        val response = converter.latestRate(
            apiKey = apiKey,
            fromCurrencyCode = query.from.code,
            toCurrencyCode = query.to.code
        )

        return response.body()
            ?.let {
                Log.d("MyTag", it.toString())
                Result.success(it.toConversionResponse(query))
            }
            ?: Result.failure(
                Exception("Error ${response.code()}: ${response.errorBody()?.string()}")
            )

    }


    private fun CurrencyApi.toConversionResponse(query: ConversionQuery): ConversionResponse {
        val rate = data[query.to.code]!!.value
        val resultAmount = query.amount * rate
        val date = meta.lastUpdatedAt?.let { Date(date = it, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") }
        return ConversionResponse(
            query = query,
            rate = rate,
            date = date,
            resultAmount = resultAmount
        )
    }


    private fun Date(date: String, pattern: String): Date {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.parse(date)!!
    }
}