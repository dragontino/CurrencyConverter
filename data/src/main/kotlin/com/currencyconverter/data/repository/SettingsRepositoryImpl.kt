package com.currencyconverter.data.repository

import com.currencyconverter.data.storage.SharedPreferencesStorage
import com.currencyconverter.domain.model.Settings
import com.currencyconverter.domain.repository.SettingsRepository

class SettingsRepositoryImpl(private val storage: SharedPreferencesStorage) : SettingsRepository {
    override suspend fun getSettings(): Settings {
        return storage.settings
    }

    override suspend fun putSettings(settings: Settings) {
        storage.settings = settings
    }
}