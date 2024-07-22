package com.currencyconverter.data.repository

import com.currencyconverter.data.storage.SharedPreferencesStorage
import com.currencyconverter.domain.model.Settings
import com.currencyconverter.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(private val storage: SharedPreferencesStorage) : SettingsRepository {
    override fun getSettings(): Flow<Settings> {
        return storage.getSettings()
    }

    override suspend fun putSettings(settings: Settings) {
        storage.saveSettings(settings)
    }
}