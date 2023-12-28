package com.madteam.split.ui.screens.forgotpassword.state

sealed class ForgotPasswordUIEvent{
    data class EmailChanged(val email: String) : ForgotPasswordUIEvent()
}
