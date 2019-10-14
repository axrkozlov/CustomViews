package com.example.customviews


fun getCurrencySymbol(currency: String): String = when (currency) {
    "KZT" -> "₸"
    "USD" -> "$"
    "EUR" -> "€"
    "RUR" -> "\u20BD"
    else -> ""
}


