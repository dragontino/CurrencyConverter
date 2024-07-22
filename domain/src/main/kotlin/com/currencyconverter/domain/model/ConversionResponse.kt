package com.currencyconverter.domain.model

import java.util.Date

data class ConversionResponse(
    val query: ConversionQuery,
    val rate: Double,
    val date: Date?,
    val resultAmount: Double
)
