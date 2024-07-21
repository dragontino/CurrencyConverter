import java.util.Properties

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kspPlugin) apply false
}

ext {
    properties["EXCHANGE_RATES_API_KEY"] = getExchangeApiKey().also { println(it) }
}

fun getExchangeApiKey(): String? {
    return Properties()
        .apply { project.file("local.properties").inputStream().let(::load) }
        .getProperty("EXCHANGE_RATES_API_KEY", "")
}