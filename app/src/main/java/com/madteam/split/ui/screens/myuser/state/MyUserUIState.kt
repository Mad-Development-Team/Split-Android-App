package com.madteam.split.ui.screens.myuser.state

import com.madteam.split.domain.model.User
import com.madteam.split.ui.theme.ModalOption

data class MyUserUIState(
    val userInfo: User = User("", "", "", ""),
    val showLogOutDialog: Boolean = false,
    val showSharedInfoMessage: Boolean = true,
    val showProfileImageModal: Boolean = false,
    val showSettingsModal: Boolean = false
)
