package com.madteam.split.ui.screens.splash.state

import com.madteam.split.data.model.utils.AuthResult
import com.madteam.split.domain.model.User

data class SplashUIState(
    val isReadyToGo: Boolean = false,
    val isAuthenticated: AuthResult<Unit>? = null,
    val userInfo: User? = null
)
