package com.madteam.split.ui.screens.signin.email.state

sealed class SignInEmailUIEvent {
    data class OnEmailChanged(val email: String) : SignInEmailUIEvent()
    data class OnPasswordChanged(val password: String) : SignInEmailUIEvent()

}