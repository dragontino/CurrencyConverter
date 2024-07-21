package com.currencyconverter.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.currencyconverter.app.app.App
import com.currencyconverter.app.ui.screens.NavigationScreen
import com.currencyconverter.app.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel = viewModel {
                (application as App).converterComponent.settingsViewModel()
            }

            AppTheme(settings = settingsViewModel.settings) {
                NavigationScreen(application as App)
            }
        }
    }
}