package com.madteam.split.ui.screens.welcome.state

import com.madteam.split.data.model.utils.AuthResult

data class WelcomeScreenUIState(
    val progressPhase: Int = 0,
    val progressSeconds: Int = 0,
    val totalPhases: Int = 4,
    val isAuthenticated: AuthResult<Unit>? = null
)