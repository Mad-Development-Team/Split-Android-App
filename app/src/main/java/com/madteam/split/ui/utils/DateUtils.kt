package com.madteam.split.ui.utils

fun getCurrentDate(): String {
    val current = java.time.LocalDate.now()
    val formatter =
        java.time.format.DateTimeFormatter.ofLocalizedDate(java.time.format.FormatStyle.SHORT)
    return current.format(formatter)
}