package com.currencyconverter.domain.model

enum class Currency(val code: String, val symbol: String) {
    Ruble("RUB", "₽"),
    Euro("EUR", "€"),
    Dollar("USD", "$"),
    PoundSterling("GBP", "£"),
    Tenge("KZT", "₸"),
    Dram("AMD", "֏"),
    Lira("TRY", "₺")
}