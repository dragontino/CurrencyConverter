package com.currencyconverter.domain.repository

import com.currencyconverter.domain.model.ConversionQuery

interface ConversionRepository {
    suspend fun convertCurrency(query: ConversionQuery): Result<Double>
}