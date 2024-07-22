package com.currencyconverter.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencyconverter.domain.model.ColorScheme
import com.currencyconverter.domain.model.Settings
import com.currencyconverter.domain.usecase.SettingsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val useCase: SettingsUseCase
) : ViewModel() {
    var settings: Settings by mutableStateOf(Settings())
        private set

    init {
        useCase
            .getSettings()
            .onEach { settings = it }
            .launchIn(viewModelScope)
    }

    fun updateScheme(newScheme: ColorScheme) {
        viewModelScope.launch {
            val newSettings = settings.copy(scheme = newScheme)
            useCase.putSettings(newSettings)
        }
    }

    fun updateDynamicColor(dynamicColor: Boolean) {
        viewModelScope.launch {
            val newSettings = settings.copy(dynamicColorEnabled = dynamicColor)
            useCase.putSettings(newSettings)
            settings = newSettings
        }
    }
}