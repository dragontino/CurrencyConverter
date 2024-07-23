package com.currencyconverter.app.ui.screens

import android.net.Uri
import androidx.annotation.StringRes
import com.currencyconverter.app.R
import com.currencyconverter.domain.model.ConversionResponse
import com.google.gson.Gson

sealed class AppScreens(protected val baseRoute: String) {
    interface Args {
        fun toStringArray(): Array<String>
    }

    @StringRes
    open val titleRes: Int? = null
    open val args: Args? = null


    val destinationRoute: String get() = buildString {
        append(baseRoute)
        if (args != null)
        args?.let { args ->
            append("/")
            append(args.toStringArray().joinToString("/") { "{$it}" })
        }
    }


    data object Main : AppScreens("main")

    data object Result : AppScreens("result") {
        object Args : AppScreens.Args {
            const val RESPONSE = "response"

            override fun toStringArray() = arrayOf(RESPONSE)
        }

        override val args = Args

        fun createUrl(response: ConversionResponse): String {
            val serializedResponse = Uri.encode(Gson().toJson(response))
            return "$baseRoute/$serializedResponse"
        }
    }

    data object Settings : AppScreens("settings") {
        override val titleRes = R.string.settings_title
    }
}