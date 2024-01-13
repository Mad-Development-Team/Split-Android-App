package com.madteam.split.ui.screens.mygroups.state

import com.madteam.split.domain.model.User

data class MyGroupsUIState(
    private val userInfo: User = User(0, "", "", "", ""),
)
