package com.madteam.split.ui.screens.creategroup.members.state

import com.madteam.split.domain.model.Member

sealed class CreateGroupMembersUIEvent {
    data class OnShowAddMemberDialogChanged(val show: Boolean) : CreateGroupMembersUIEvent()
    data class OnNewMemberNameChanged(val name: String) : CreateGroupMembersUIEvent()
    data class OnMemberSelected(val member: Member) : CreateGroupMembersUIEvent()
    data object OnAddMemberClicked : CreateGroupMembersUIEvent()
    data object OnDeleteSelectedMember : CreateGroupMembersUIEvent()
}
