package com.madteam.split.ui.screens.creategroup.members.state

import com.madteam.split.domain.model.Member

data class CreateGroupMembersUIState(
    val membersList: List<Member> = listOf(),
    val showAddMemberDialog: Boolean = false,
    val newMemberName: String = "",
    val isNewMemberNameValid: Boolean = false,
    val nameErrorText: Int? = null,
    val memberSelected: Member? = null,
    val showDialogError: Boolean = false,
    val errorMessage: Int? = null,
    val showLoading: Boolean = false,
    val createGroupSuccess: Boolean = false,
)
