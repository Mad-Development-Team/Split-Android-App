package com.madteam.split.ui.utils

import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun getCurrentDate(): String {
    val current = java.time.LocalDate.now()
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    return current.format(formatter)
}