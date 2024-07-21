package com.currencyconverter.app.di

import com.currencyconverter.app.viewmodel.MainViewModel
import com.currencyconverter.app.viewmodel.SettingsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class])
interface ConverterComponent {
    fun mainViewModel(): MainViewModel
    fun settingsViewModel(): SettingsViewModel
}