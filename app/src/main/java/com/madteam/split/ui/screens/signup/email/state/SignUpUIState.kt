package com.madteam.split.ui.screens.signup.email.state

data class SignUpUIState(
    val name: String = "",
    val isNameValid: Boolean = false,
    val email: String = "",
    val isEmailValid: Boolean = false,
    val password: String = "",
    val isPasswordValid: Boolean = false,
    val confirmPassword: String = "",
    val isConfirmPasswordValid: Boolean = false,
    val isTermsAndConditionsChecked: Boolean = false,
    val isTermsAndConditionsError: Boolean = false,
    val isFormValid: Boolean = false,
)
