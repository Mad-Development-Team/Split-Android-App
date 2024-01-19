package com.madteam.split.ui.screens.creategroup.invite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madteam.split.data.repository.creategroup.CreateGroupRepository
import com.madteam.split.ui.screens.creategroup.invite.state.CreateGroupInviteUIEvent
import com.madteam.split.ui.screens.creategroup.invite.state.CreateGroupInviteUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupInviteViewModel @Inject constructor(
    private val createGroupRepository: CreateGroupRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<CreateGroupInviteUIState> =
        MutableStateFlow(CreateGroupInviteUIState())
    val state: StateFlow<CreateGroupInviteUIState> = _state

    init {
        getGroupFromRepository()
    }

    private fun getGroupFromRepository() {
        viewModelScope.launch {
            val createdGroup = createGroupRepository.getNewGroup()
            _state.value = _state.value.copy(group = createdGroup)
        }
    }

    fun onEvent(event: CreateGroupInviteUIEvent) {
        when (event) {
            else -> {
            }
        }
    }
}