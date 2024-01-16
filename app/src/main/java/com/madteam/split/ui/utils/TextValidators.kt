package com.madteam.split.ui.utils

private const val VALID_NAME_REGEX = "^[a-zA-Z0-9]+$"
private const val MIN_CHAR_NAME_LENGTH = 3
private const val MIN_CHAR_GROUP_NAME_LENGTH = 1
private const val MAX_CHAR_NAME_LENGTH = 20
private const val MAX_CHAR_GROUP_NAME_LENGTH = 20

private const val VALID_EMAIL_REGEX = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"

private const val VALID_PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}\$"

fun validateName(name: String): Boolean {
    val regex = Regex(VALID_NAME_REGEX)
    val validLength = name.length in MIN_CHAR_NAME_LENGTH..MAX_CHAR_NAME_LENGTH

    return regex.matches(name) && validLength
}

fun validateEmail(email: String): Boolean {
    val regex = Regex(VALID_EMAIL_REGEX)

    return regex.matches(email)
}

fun validatePassword(password: String): Boolean {
    val regex = Regex(VALID_PASSWORD_REGEX)

    return regex.matches(password)
}

fun validateGroupName(name: String): Boolean {
    return name.length in MIN_CHAR_GROUP_NAME_LENGTH..MAX_CHAR_GROUP_NAME_LENGTH
}