package com.madteam.split.ui.screens.creategroup.members.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madteam.split.R
import com.madteam.split.data.repository.group.GroupRepository
import com.madteam.split.data.repository.user.UserRepository
import com.madteam.split.domain.model.Member
import com.madteam.split.ui.screens.creategroup.members.state.CreateGroupMembersUIEvent
import com.madteam.split.ui.screens.creategroup.members.state.CreateGroupMembersUIState
import com.madteam.split.ui.utils.validateName
import com.madteam.split.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupMembersViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val createGroupRepository: GroupRepository,
) : ViewModel() {

    init {
        addMyUserToMembersList()
    }

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

            is CreateGroupMembersUIEvent.OnShowErrorDialogChanged -> {
                showErrorDialog(event.show)
            }

            is CreateGroupMembersUIEvent.OnShowLoadingDialogChanged -> {
                showLoadingDialog(event.show)
            }

            is CreateGroupMembersUIEvent.OnNextClick -> {
                saveMembers()
                createGroupIntent()
            }
        }
    }

    private fun createGroupIntent() {
        viewModelScope.launch {
            showLoadingDialog(true)
            val result = createGroupRepository.createGroup()
            if (result is Resource.Success) {
                showLoadingDialog(false)
                _state.value = _state.value.copy(
                    createGroupSuccess = true
                )
            } else {
                showLoadingDialog(false)
                showErrorMessage(
                    message = R.string.error_creating_group
                )
            }
        }
    }

    private fun saveMembers() {
        createGroupRepository.setMembers(
            state.value.membersList
        )
    }

    private fun addMyUserToMembersList() {
        viewModelScope.launch {
            when (val user = userRepository.getUserInfo()) {
                is Resource.Success -> {
                    val myMember = Member(
                        id = 0,
                        name = user.data.name,
                        profileImage = user.data.profileImage,
                        user = user.data.id,
                        color = null,
                        joinedDate = "",
                        groupId = 0,
                    )
                    _state.value = _state.value.copy(
                        membersList = _state.value.membersList + myMember
                    )
                }

                else -> {
                    showErrorMessage(
                        message = R.string.error_adding_yourself_member
                    )
                }
            }
        }
    }

    private fun showErrorMessage(message: Int) {
        _state.value = _state.value.copy(
            showDialogError = true,
            errorMessage = message
        )
    }

    private fun showAddMemberDialog(state: Boolean) {
        _state.value = _state.value.copy(
            showAddMemberDialog = state
        )
    }

    private fun showLoadingDialog(state: Boolean) {
        _state.value = _state.value.copy(
            showLoading = state
        )
    }

    private fun showErrorDialog(state: Boolean) {
        _state.value = _state.value.copy(
            showDialogError = state
        )
    }

    private fun onMemberSelected(member: Member) {
        if (member.user != null) {
            return
        }
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