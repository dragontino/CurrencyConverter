package com.currencyconverter.data.storage

import android.content.Context
import com.currencyconverter.domain.model.Settings
import com.google.gson.Gson

class SharedPreferencesStorage(context: Context) {
    private val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var settings: Settings
        get() = preferences
            .getString(SETTINGS_NAME, null)
            ?.let { Gson().fromJson(it, Settings::class.java) }
            ?: Settings()
        set(value) {
            val settingsJson = Gson().toJson(value)
            preferences.edit()
                .putString(SETTINGS_NAME, settingsJson)
                .apply()
        }

    private companion object Names {
        const val PREF_NAME = "application_settings"
        const val SETTINGS_NAME = "settings"
    }
}