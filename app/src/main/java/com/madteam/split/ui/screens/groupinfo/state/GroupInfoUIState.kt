package com.madteam.split.ui.screens.groupinfo.state

import com.madteam.split.domain.model.Group

data class GroupInfoUIState(
    val groupsModalIsVisible: Boolean = false,
    val groupsList: List<Group> = listOf(
        Group(
            id = 5,
            name = "Amsterdam",
            description = "",
            inviteCode = "",
            image = "",
            bannerImage = "",
            createdDate = "",
            members = listOf()
        ),
        Group(
            id = 6,
            name = "pisito",
            description = "",
            inviteCode = "",
            image = "",
            bannerImage = "",
            createdDate = "",
            members = listOf()
        )
    ),
    //TODO: Remove and apply real data
    val currentGroupId: Int = 5,
)
