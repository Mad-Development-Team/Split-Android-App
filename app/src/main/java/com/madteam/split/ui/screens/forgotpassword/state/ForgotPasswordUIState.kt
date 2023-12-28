package com.madteam.split.ui.screens.forgotpassword.state

data class ForgotPasswordUIState(
    val email: String = "",
    val isEmailValid: Boolean = false,
    val continueEnabled: Boolean = false
)