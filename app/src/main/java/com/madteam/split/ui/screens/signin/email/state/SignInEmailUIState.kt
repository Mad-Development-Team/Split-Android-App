package com.madteam.split.ui.screens.signin.email.state

import com.madteam.split.data.model.AuthResult

data class SignInEmailUIState(
    val emailValue: String = "",
    val isEmailValid: Boolean = false,
    val passwordValue: String = "",
    val isLoading: Boolean = false,
    val authResult: AuthResult<Unit>? = null
)
