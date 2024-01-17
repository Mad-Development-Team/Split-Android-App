package com.madteam.split.ui.screens.creategroup.members.state

import com.madteam.split.domain.model.Member

data class CreateGroupMembersUIState(
    val membersList: List<Member> = listOf(),
    val showAddMemberDialog: Boolean = false,
    val newMemberName: String = "",
    val isNewMemberNameValid: Boolean = false,
    val nameErrorText: Int? = null,
)
