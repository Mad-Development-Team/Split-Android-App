package com.madteam.split.ui.utils

import android.content.Context
import com.madteam.split.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

fun getCurrentDate(): String {
    val current = java.time.LocalDate.now()
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    return current.format(formatter)
}

fun formatSmallDateBasedOnLocale(context: Context, dateString: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val dateInput = LocalDateTime.parse(dateString, inputFormatter)

    val today = LocalDateTime.now()
    val yesterday = today.minusDays(1)

    return when {
        dateInput.toLocalDate() == today.toLocalDate() -> context.getString(R.string.today)
        dateInput.toLocalDate() == yesterday.toLocalDate() -> context.getString(R.string.yesterday)
        else -> {
            val locale = Locale.getDefault()
            val outputFormatter = if (locale.country in listOf(
                    "US", "FM", "PW", "MH", "MM", "LR", "PH", "GU", "AS", "MP", "VI", "BZ", "PW"
                )) {
                DateTimeFormatter.ofPattern("MM/dd")
            } else {
                DateTimeFormatter.ofPattern("dd/MM")
            }
            dateInput.format(outputFormatter)
        }
    }
}