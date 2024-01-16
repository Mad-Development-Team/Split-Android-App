package com.madteam.split.ui.screens.creategroup.info.state

data class CreateGroupInfoUIState(
    val groupName: String = "",
    val groupDescription: String = "",
    val isGroupNameValid: Boolean = false,
    val isGroupDescriptionValid: Boolean = true,
)
