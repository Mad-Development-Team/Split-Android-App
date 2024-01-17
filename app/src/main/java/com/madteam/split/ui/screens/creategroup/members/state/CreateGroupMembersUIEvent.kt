package com.madteam.split.ui.screens.creategroup.members.state

sealed class CreateGroupMembersUIEvent {
    data class OnShowAddMemberDialogChanged(val show: Boolean) : CreateGroupMembersUIEvent()
    data class OnNewMemberNameChanged(val name: String) : CreateGroupMembersUIEvent()
    data object OnAddMemberClicked : CreateGroupMembersUIEvent()
}
