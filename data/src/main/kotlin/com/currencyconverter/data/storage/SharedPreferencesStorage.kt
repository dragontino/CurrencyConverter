package com.currencyconverter.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.currencyconverter.domain.model.Settings
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SharedPreferencesStorage(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREF_NAME)

    fun getSettings(): Flow<Settings> {
        return context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(SETTINGS_NAME)]
                ?.let { Gson().fromJson(it, Settings::class.java) }
                ?: Settings()
        }
    }

    suspend fun saveSettings(settings: Settings) {
        context.dataStore.edit { preferences ->
            val settingsJson = Gson().toJson(settings)
            preferences[stringPreferencesKey(SETTINGS_NAME)] = settingsJson
        }
    }

    private companion object Names {
        const val PREF_NAME = "converter_settings"
        const val SETTINGS_NAME = "settings"
    }
}