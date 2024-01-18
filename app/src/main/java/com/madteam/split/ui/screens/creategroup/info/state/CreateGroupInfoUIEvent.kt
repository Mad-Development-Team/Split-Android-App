package com.madteam.split.ui.screens.creategroup.info.state

sealed class CreateGroupInfoUIEvent {
    data class OnGroupNameChange(val groupName: String) : CreateGroupInfoUIEvent()
    data class OnGroupDescriptionChange(val groupDescription: String) : CreateGroupInfoUIEvent()
}
