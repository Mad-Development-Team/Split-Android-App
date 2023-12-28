package com.madteam.split.ui.navigation

sealed class Screens(
    val route: String
){
    data object WelcomeScreen : Screens("welcome_screen")
    data object SignInEmailScreen: Screens("sign_in_email_screen")
    data object ForgotPasswordScreen: Screens("forgot_password_screen")
}
