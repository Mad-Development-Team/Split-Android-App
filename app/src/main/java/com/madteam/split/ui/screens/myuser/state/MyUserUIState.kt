package com.madteam.split.ui.screens.myuser.state

import com.madteam.split.domain.model.User

data class MyUserUIState(
    val userInfo: User = User("", "", "", "")
)
