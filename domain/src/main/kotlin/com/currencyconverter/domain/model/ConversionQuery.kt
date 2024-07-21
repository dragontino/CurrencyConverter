package com.currencyconverter.domain.model

data class ConversionQuery(
    val from: Currency,
    val to: Currency,
    val amount: Double
)
