package com.madteam.split.utils.ui

import com.madteam.split.R
import com.madteam.split.domain.model.Currency

fun getFlagByCurrency(currency: Currency?): Int {
    return when {
         currency != null -> {
             when (currency.currency) {
                 "EUR" -> R.drawable.emoji_flags_flageuropeanunion
                 "GBP" -> R.drawable.emoji_flags_flagunitedkingdom
                 "CHF" -> R.drawable.emoji_flags_flagswitzerland
                 "SEK" -> R.drawable.emoji_flags_flagsweden
                 "NOK" -> R.drawable.emoji_flags_flagbouvetisland
                 "DKK" -> R.drawable.emoji_flags_flagdenmark
                 "PLN" -> R.drawable.emoji_flags_flagpoland
                 "USD" -> R.drawable.emoji_flags_flagusoutlyingislands
                 "CAD" -> R.drawable.emoji_flags_flagcanada
                 "MXN" -> R.drawable.emoji_flags_flagmexico
                 "BRL" -> R.drawable.emoji_flags_flagbrazil
                 "ARS" -> R.drawable.emoji_flags_flagargentina
                 "COP" -> R.drawable.emoji_flags_flagcolombia
                 "PEN" -> R.drawable.emoji_flags_flagperu
                 else -> R.drawable.emoji_flags_flageuropeanunion
             }
         }

        else -> {
            R.drawable.emoji_flags_flageuropeanunion
        }
    }
}

fun getEmojisByType(type: String): List<Int> {
    val fieldList = R.drawable::class.java.fields
    return fieldList.filter { it.name.startsWith("emoji_${type}_") }.mapNotNull { field ->
        try {
            field.getInt(null)
        } catch (e: Exception) {
            null
        }
    }
}

fun getEmojiByName(name: String): Int {
    val fieldList = R.drawable::class.java.fields
    val field = fieldList.firstOrNull { it.name.startsWith("emoji_") && it.name.contains(name) }

    return if (field != null) {
        try {
            field.getInt(null)
        } catch (e: Exception) {
            R.drawable.emoji_symbols_questionmark
        }
    } else {
        R.drawable.emoji_symbols_questionmark
    }
}