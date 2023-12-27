package com.madteam.split.ui.navigation

sealed class Screens(
    val route: String
){
    data object WelcomeScreen : Screens("welcome_screen")
}
