package com.currencyconverter.domain.usecase

import com.currencyconverter.domain.model.Settings
import com.currencyconverter.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SettingsUseCase(
    private val repository: SettingsRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun getSettings(): Settings = withContext(dispatcher) {
        repository.getSettings()
    }

    suspend fun putSettings(settings: Settings) = withContext(dispatcher) {
        repository.putSettings(settings)
    }
}