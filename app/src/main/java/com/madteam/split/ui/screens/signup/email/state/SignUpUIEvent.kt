package com.madteam.split.ui.screens.signup.email.state

import com.madteam.split.ui.screens.signin.email.state.SignInEmailUIEvent

sealed class SignUpUIEvent{
    data class OnNameChanged(val name: String) : SignUpUIEvent()
    data class OnEmailChanged(val email: String) : SignUpUIEvent()
    data class OnPasswordChanged(val password: String) : SignUpUIEvent()
    data class OnConfirmPasswordChanged(val confirmPassword: String) : SignUpUIEvent()
    data class OnErrorDialogStateChanged(val state: Boolean) : SignUpUIEvent()
    data object OnTermsAndConditionsChecked : SignUpUIEvent()
    data object OnSignUpClicked : SignUpUIEvent()
    data object OnEmailErrorStateChanged : SignUpUIEvent()

}
