package com.currencyconverter.app.di

import android.content.Context
import com.currencyconverter.data.repository.ConversationRepositoryImpl
import com.currencyconverter.data.repository.SettingsRepositoryImpl
import com.currencyconverter.data.retrofit.ConverterAPI
import com.currencyconverter.data.storage.SharedPreferencesStorage
import com.currencyconverter.domain.repository.ConversionRepository
import com.currencyconverter.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule(private val context: Context) {
    @Singleton
    @Provides
    fun provideConversionRepository(converter: ConverterAPI): ConversionRepository {
        return ConversationRepositoryImpl(converter)
    }

    @Singleton
    @Provides
    fun provideSettingsRepository(storage: SharedPreferencesStorage): SettingsRepository {
        return SettingsRepositoryImpl(storage)
    }

    @Singleton
    @Provides
    fun provideSharedPreferencesStorage() = SharedPreferencesStorage(context)

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit): ConverterAPI {
        return retrofit.create(ConverterAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.exchangeratesapi.io/v1/convert/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}