package com.madteam.split.utils.ui

import com.madteam.split.R
import com.madteam.split.domain.model.Currency

fun getFlagByCurrency(currency: Currency?): Int {
    return when {
        currency != null -> {
            when (currency.currency) {
                "EUR" -> R.drawable.emoji_europe_flag
                "GBP" -> R.drawable.emoji_uk_flag
                "CHF" -> R.drawable.emoji_switzerland_flag
                "SEK" -> R.drawable.emoji_sweden_flag
                "NOK" -> R.drawable.emoji_norway_flag
                "DKK" -> R.drawable.emoji_denmark_flag
                "PLN" -> R.drawable.emoji_poland_flag
                "USD" -> R.drawable.emoji_usa_flag
                "CAD" -> R.drawable.emoji_canada_flag
                "MXN" -> R.drawable.emoji_mexico_flag
                "BRL" -> R.drawable.emoji_brazil_flag
                "ARS" -> R.drawable.emoji_argentina_flag
                "COP" -> R.drawable.emoji_colombia_flag
                "PEN" -> R.drawable.emoji_peru_flag
                else -> R.drawable.emoji_europe_flag
            }
        }

        else -> {
            R.drawable.emoji_pirate_flag
        }
    }
}