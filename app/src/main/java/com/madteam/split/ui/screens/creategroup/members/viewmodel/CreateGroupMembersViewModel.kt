package com.madteam.split.ui.screens.creategroup.members.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.R
import com.madteam.split.domain.model.Member
import com.madteam.split.ui.screens.creategroup.members.state.CreateGroupMembersUIEvent
import com.madteam.split.ui.screens.creategroup.members.state.CreateGroupMembersUIState
import com.madteam.split.ui.utils.validateName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CreateGroupMembersViewModel @Inject constructor(

) : ViewModel() {

    private val _state: MutableStateFlow<CreateGroupMembersUIState> =
        MutableStateFlow(CreateGroupMembersUIState())
    val state: StateFlow<CreateGroupMembersUIState> = _state

    fun onEvent(event: CreateGroupMembersUIEvent) {
        when (event) {
            is CreateGroupMembersUIEvent.OnShowAddMemberDialogChanged -> {
                showAddMemberDialog(event.show)
            }

            is CreateGroupMembersUIEvent.OnNewMemberNameChanged -> {
                newMemberNameChanged(event.name)
            }

            is CreateGroupMembersUIEvent.OnAddMemberClicked -> {
                addNewMemberToList()
            }

            is CreateGroupMembersUIEvent.OnDeleteSelectedMember -> {
                deleteSelectedMember()
            }

            is CreateGroupMembersUIEvent.OnMemberSelected -> {
                onMemberSelected(event.member)
            }
        }
    }

    private fun showAddMemberDialog(state: Boolean) {
        _state.value = _state.value.copy(
            showAddMemberDialog = state
        )
    }

    private fun onMemberSelected(member: Member) {
        if (_state.value.memberSelected?.id == member.id) {
            _state.value = _state.value.copy(
                memberSelected = null
            )
        } else {
            _state.value = _state.value.copy(
                memberSelected = member
            )
        }
    }

    private fun newMemberNameChanged(name: String) {
        _state.value = _state.value.copy(
            newMemberName = name,
            isNewMemberNameValid = validateName(name) && nameDoesNotAlreadyExist(name),
            nameErrorText = if (!nameDoesNotAlreadyExist(
                    name
                )) R.string.name_already_exists else null
        )
    }

    private fun deleteSelectedMember() {
        val selectedMember = _state.value.memberSelected
        if (selectedMember != null) {
            _state.value = _state.value.copy(
                membersList = _state.value.membersList.filter { it.name != selectedMember.name },
                memberSelected = null
            )
        }
    }

    private fun addNewMemberToList() {
        val newMember = Member(
            id = 0,
            name = _state.value.newMemberName,
            profileImage = null,
            user = null,
            color = null,
            joinedDate = "",
            groupId = 0,
        )
        _state.value = _state.value.copy(
            membersList = _state.value.membersList + newMember,
            newMemberName = "",
            isNewMemberNameValid = false,
            showAddMemberDialog = false
        )
    }

    private fun nameDoesNotAlreadyExist(name: String): Boolean {
        return _state.value.membersList.none { it.name == name }
    }
}