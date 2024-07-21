package com.currencyconverter.app.app

import android.app.Application
import com.currencyconverter.app.di.ConverterComponent
import com.currencyconverter.app.di.DaggerConverterComponent
import com.currencyconverter.app.di.DataModule

class App : Application() {
    val converterComponent: ConverterComponent by lazy {
        DaggerConverterComponent.builder()
            .dataModule(DataModule(this))
            .build()
    }
}