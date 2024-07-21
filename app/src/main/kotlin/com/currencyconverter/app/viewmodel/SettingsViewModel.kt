package com.currencyconverter.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencyconverter.domain.model.ColorScheme
import com.currencyconverter.domain.model.Settings
import com.currencyconverter.domain.usecase.SettingsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val useCase: SettingsUseCase
) : ViewModel() {
    var settings by mutableStateOf(Settings())
        private set

    init {
        viewModelScope.launch {
            settings = useCase.getSettings()
        }
    }

    fun updateScheme(newScheme: ColorScheme) {
        viewModelScope.launch {
            val newSettings = settings.copy(scheme = newScheme)
            useCase.putSettings(newSettings)
            settings = newSettings
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