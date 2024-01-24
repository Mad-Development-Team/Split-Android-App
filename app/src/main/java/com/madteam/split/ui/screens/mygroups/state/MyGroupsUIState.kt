package com.madteam.split.ui.screens.mygroups.state

import com.madteam.split.domain.model.Group
import com.madteam.split.domain.model.User

data class MyGroupsUIState(
    val userInfo: User = User(0, "", "", "", ""),
    val userGroups: List<Group> = listOf(),
    val isGroupsListLoading: Boolean = false,
    val groupSelected: Group? = null,
)
