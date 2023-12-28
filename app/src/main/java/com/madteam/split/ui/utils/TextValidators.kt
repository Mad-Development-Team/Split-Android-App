package com.madteam.split.ui.utils

private const val VALID_NAME_REGEX = "^[a-zA-Z0-9]+$"
private const val MIN_CHAR_NAME_LENGTH = 3
private const val MAX_CHAR_NAME_LENGTH = 20

fun validateName(name: String): Boolean {
    val regex = Regex(VALID_NAME_REGEX)
    val validLength = name.length in MIN_CHAR_NAME_LENGTH..MAX_CHAR_NAME_LENGTH

    return regex.matches(name) && validLength
}