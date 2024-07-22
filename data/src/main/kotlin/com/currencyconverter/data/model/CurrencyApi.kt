package com.currencyconverter.data.model

import com.google.gson.annotations.SerializedName

data class CurrencyApi(
    val meta: Meta,
    val data: Map<String, CurrencyRate>
) {
    data class Meta(@SerializedName("last_updated_at") val lastUpdatedAt: String?)
    data class CurrencyRate(
        val code: String,
        val value: Double
    )
}
