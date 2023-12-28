package com.madteam.split.ui.screens.signup.email.state

sealed class SignUpUIEvent{
    data class OnNameChanged(val name: String) : SignUpUIEvent()
    data class OnEmailChanged(val email: String) : SignUpUIEvent()
    data class OnPasswordChanged(val password: String) : SignUpUIEvent()
    data class OnConfirmPasswordChanged(val confirmPassword: String) : SignUpUIEvent()
    data object OnTermsAndConditionsChecked : SignUpUIEvent()

}
