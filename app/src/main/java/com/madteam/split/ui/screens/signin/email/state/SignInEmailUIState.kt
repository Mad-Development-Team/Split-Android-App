package com.madteam.split.ui.screens.signin.email.state

import com.madteam.split.data.model.utils.AuthResult

data class SignInEmailUIState(
    val emailValue: String = "",
    val isEmailValid: Boolean = false,
    val passwordValue: String = "",
    val isLoading: Boolean = false,
    val isErrorDialog: Boolean = false,
    val isFieldsOnErrorState: Boolean = false,
    val authResult: AuthResult<Unit>? = null
)
