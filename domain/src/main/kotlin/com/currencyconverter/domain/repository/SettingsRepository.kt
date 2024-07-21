package com.currencyconverter.domain.repository

import com.currencyconverter.domain.model.Settings

interface SettingsRepository {
    suspend fun getSettings(): Settings

    suspend fun putSettings(settings: Settings)
}