package com.madteam.split.ui.screens.groupinfo.state

sealed class GroupInfoUIEvent {
    data class ShowGroupsModal(val show: Boolean) : GroupInfoUIEvent()
}
