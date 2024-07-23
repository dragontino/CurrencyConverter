package com.currencyconverter.app.util

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.currencyconverter.app.R
import com.currencyconverter.domain.model.ColorScheme
import com.currencyconverter.domain.model.Currency

val Color.reversed: Color
    get() = copy(red = 1 - red, green = 1 - green, blue = 1 - blue)


fun Resources.isDarkTheme(): Boolean =
    (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES


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


val ColorScheme.title: String
@Composable get() = when (this) {
    ColorScheme.Light -> stringResource(R.string.light_scheme)
    ColorScheme.Dark -> stringResource(R.string.dark_scheme)
    ColorScheme.System -> stringResource(R.string.system_scheme)
}


@Suppress("DEPRECATION")
inline fun <reified T : Any> Bundle.extractParcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(key, T::class.java)
    else -> getParcelable(key)
}