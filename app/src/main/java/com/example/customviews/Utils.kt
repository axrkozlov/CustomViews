package com.example.customviews

fun String.pretty(currency: String = "KZT", truncZeros: Boolean = false): String {
    val rawValue = this.raw()
    if (rawValue.isEmpty()) return "0 ${getCurrencySymbol(currency)}"
    val parts = rawValue.split(".")
    var finalString = ""
    var firstPart = parts[0]
    while (firstPart.isNotEmpty() && firstPart[0] == '0') {
        firstPart = firstPart.substring(1, firstPart.length)
    }

    if (firstPart.isEmpty()) {
        firstPart = "0"
    }

    var right = firstPart.length
    while (right > 0) {
        val left = Math.max(right - 3, 0)
        finalString = if (finalString.isNotEmpty()) {
            firstPart.substring(left, right) + " " + finalString
        } else {
            firstPart.substring(left, right)
        }

        right -= 3
    }

    if (truncZeros) {
        if (parts.size > 1 && !(parts[1] == "0" || parts[1] == "00")) {
            finalString += "," + parts[1].substring(0, Math.min(2, parts[1].length))
        }
    } else {
        if (parts.size > 1) {
            finalString += "," + parts[1].substring(0, Math.min(2, parts[1].length))
        }
    }
    finalString += " ${getCurrencySymbol(currency)}"
    return finalString
}


fun String?.raw(): String {
    if (this != null) {
        var result = ""
        var hasDot = false
        for (c in this) {
            if (c.isDigit()) {
                result += c
            }
            if (!hasDot && (c.equals(',') || c.equals('.'))) {
                hasDot = true
                result += "."
            }
        }
        if (result.isNotEmpty())
            return result
        return ""
    } else {
        return ""
    }
}


fun getCurrencySymbol(currency: String?): String = when (currency) {
    "KZT" -> "₸"
    "USD" -> "$"
    "EUR" -> "€"
    "RUR" -> "\u20BD"
    else -> ""
}
