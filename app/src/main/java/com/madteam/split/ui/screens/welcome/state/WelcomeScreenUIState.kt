package com.madteam.split.ui.screens.welcome.state

data class WelcomeScreenUIState(
    val progressPhase: Int = 0,
    val progressSeconds: Int = 0,
    val totalPhases: Int = 4
)