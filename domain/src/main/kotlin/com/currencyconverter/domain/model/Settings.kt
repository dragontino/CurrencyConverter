package com.currencyconverter.domain.model

data class Settings(
    val scheme: ColorScheme = ColorScheme.System,
    val dynamicColorEnabled: Boolean = false
)
