package com.currencyconverter.domain.repository

import com.currencyconverter.domain.model.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<Settings>

    suspend fun putSettings(settings: Settings)
}