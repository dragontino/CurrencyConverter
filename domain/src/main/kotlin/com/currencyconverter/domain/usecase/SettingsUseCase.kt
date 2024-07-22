package com.currencyconverter.domain.usecase

import com.currencyconverter.domain.model.Settings
import com.currencyconverter.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class SettingsUseCase(
    private val repository: SettingsRepository,
    private val dispatcher: CoroutineDispatcher
) {
    fun getSettings(): Flow<Settings> = repository
        .getSettings()
        .flowOn(dispatcher)

    suspend fun putSettings(settings: Settings) = withContext(dispatcher) {
        repository.putSettings(settings)
    }
}