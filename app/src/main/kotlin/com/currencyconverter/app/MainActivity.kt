package com.currencyconverter.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.currencyconverter.app.app.App
import com.currencyconverter.app.ui.screens.NavigationScreen
import com.currencyconverter.app.ui.theme.AppTheme
import com.currencyconverter.app.util.isDarkTheme
import com.currencyconverter.app.viewmodel.SettingsViewModel
import com.currencyconverter.domain.model.ColorScheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingsViewModel: SettingsViewModel by viewModels {
            viewModelFactory {
                initializer {
                    (application as App).converterComponent.settingsViewModel()
                }
            }
        }

        val style = when (settingsViewModel.settings.scheme) {
            ColorScheme.Dark -> SystemBarStyle.dark(scrim = Color.Transparent.toArgb())
            else -> SystemBarStyle.auto(
                lightScrim = Color.Transparent.toArgb(),
                darkScrim = Color.Transparent.toArgb()
            ) { resources ->
                !resources.isDarkTheme()
            }
        }

        enableEdgeToEdge(statusBarStyle = style)

        setContent {
            AppTheme(settings = settingsViewModel.settings) {
                NavigationScreen(application as App)
            }
        }
    }
}