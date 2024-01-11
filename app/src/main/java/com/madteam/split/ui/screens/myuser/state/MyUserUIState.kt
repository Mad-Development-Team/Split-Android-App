package com.madteam.split.ui.screens.myuser.state

import com.madteam.split.domain.model.User

data class MyUserUIState(
    val userInfo: User = User(
        id = 0,
        name = "",
        email = "",
        profileImage = "",
        createdDate = ""
    ),
    val showLogOutDialog: Boolean = false,
    val showSharedInfoMessage: Boolean = true,
    val showProfileImageModal: Boolean = false,
    val showSettingsModal: Boolean = false
)
