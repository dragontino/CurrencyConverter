package com.currencyconverter.app.ui.screens

import androidx.annotation.StringRes
import com.currencyconverter.app.R

sealed interface AppScreens {
    val baseRoute: String
    @get:StringRes
    val titleRes: Int? get() = null

    data object Main : AppScreens {
        override val baseRoute = "main"
    }

    data object Settings : AppScreens {
        override val baseRoute = "settings"
        override val titleRes = R.string.settings_title
    }
}