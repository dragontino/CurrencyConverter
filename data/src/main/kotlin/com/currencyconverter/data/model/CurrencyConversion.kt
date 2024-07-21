package com.currencyconverter.data.model

data class CurrencyConversion(
    val success: Boolean,
    val query: Query,
    val info: Info,
    val historical: String,
    val date: String,
    val result: Double
) {
    data class Query(
        val from: String,
        val to: String,
        val amount: Double
    )

    data class Info(
        val timestamp: String,
        val rate: Double
    )
}
