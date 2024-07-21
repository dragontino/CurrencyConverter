package com.currencyconverter.app.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.currencyconverter.app.R
import com.currencyconverter.domain.model.Currency

val Color.reversed: Color
    get() = copy(alpha = 1 - red, red = 1 - green, green = 1 - blue)


val Currency.title: String
@Composable get() = when (this) {
    Currency.Ruble -> stringResource(R.string.ruble)
    Currency.Euro -> stringResource(R.string.euro)
    Currency.Dollar -> stringResource(R.string.dollar)
    Currency.PoundSterling -> stringResource(R.string.pound_sterling)
    Currency.Tenge -> stringResource(R.string.tenge)
    Currency.Dram -> stringResource(R.string.dram)
    Currency.Lira -> stringResource(R.string.lira)
}