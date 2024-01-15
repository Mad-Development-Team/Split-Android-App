package com.madteam.split.ui.screens.mygroups.state

import com.madteam.split.domain.model.User

data class MyGroupsUIState(
    val userInfo: User = User(0, "", "", "", ""),
)
