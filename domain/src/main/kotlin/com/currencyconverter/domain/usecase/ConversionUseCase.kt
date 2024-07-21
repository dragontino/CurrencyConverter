package com.currencyconverter.domain.usecase

import com.currencyconverter.domain.model.ConversionQuery
import com.currencyconverter.domain.repository.ConversionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ConversionUseCase(
    private val repository: ConversionRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun convertCurrency(query: ConversionQuery) = withContext(dispatcher) {
        repository.convertCurrency(query)
    }
}