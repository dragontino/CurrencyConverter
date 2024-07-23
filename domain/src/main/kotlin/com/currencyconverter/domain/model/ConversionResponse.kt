package com.currencyconverter.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class ConversionResponse(
    val query: ConversionQuery,
    val rate: Double,
    val date: Date?,
    val resultAmount: Double
) : Parcelable
