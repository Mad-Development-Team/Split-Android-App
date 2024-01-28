package com.madteam.split.ui.screens.group.state

import com.madteam.split.domain.model.Group

data class GroupUIState(
    val currentGroupId: Int? = null,
    val userGroups: List<Group> = listOf(),
)
