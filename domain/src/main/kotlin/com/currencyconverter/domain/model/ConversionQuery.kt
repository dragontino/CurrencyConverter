package com.currencyconverter.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConversionQuery(
    val from: Currency,
    val to: Currency,
    val amount: Double
) : Parcelable
