package com.madteam.split.ui.screens.signup.email.state

import com.madteam.split.data.model.utils.AuthResult

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
    val isLoading: Boolean = false,
    val authResult: AuthResult<Unit>? = null,
    val isErrorDialog: Boolean = false,
)
