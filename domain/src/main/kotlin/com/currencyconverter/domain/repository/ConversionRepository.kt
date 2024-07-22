package com.currencyconverter.domain.repository

import com.currencyconverter.domain.model.ConversionQuery
import com.currencyconverter.domain.model.ConversionResponse

interface ConversionRepository {
    suspend fun convertCurrency(query: ConversionQuery): Result<ConversionResponse>
}