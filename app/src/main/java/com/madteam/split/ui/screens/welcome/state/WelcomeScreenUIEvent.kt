package com.madteam.split.ui.screens.welcome.state

sealed class WelcomeScreenUIEvent {
    data object OnStart : WelcomeScreenUIEvent()
}