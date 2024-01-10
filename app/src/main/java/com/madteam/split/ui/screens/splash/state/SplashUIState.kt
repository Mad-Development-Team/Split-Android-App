package com.madteam.split.ui.screens.splash.state

import com.madteam.split.data.model.utils.AuthResult

data class SplashUIState(
    val isReadyToGo: Boolean = false,
    val isAuthenticated: AuthResult<Unit>? = null
)
