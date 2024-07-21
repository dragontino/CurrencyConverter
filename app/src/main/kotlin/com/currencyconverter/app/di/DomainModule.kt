package com.currencyconverter.app.di

import com.currencyconverter.domain.repository.ConversionRepository
import com.currencyconverter.domain.repository.SettingsRepository
import com.currencyconverter.domain.usecase.ConversionUseCase
import com.currencyconverter.domain.usecase.SettingsUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class DomainModule {
    @Singleton
    @Provides
    fun provideConversionUseCase(repository: ConversionRepository): ConversionUseCase =
        ConversionUseCase(repository, dispatcher = Dispatchers.IO)

    @Singleton
    @Provides
    fun provideSettingsUseCase(repository: SettingsRepository): SettingsUseCase =
        SettingsUseCase(repository, dispatcher = Dispatchers.IO)
}