package com.madteam.split.ui.screens.group.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madteam.split.data.repository.group.GroupRepository
import com.madteam.split.ui.screens.group.state.GroupUIState
import com.madteam.split.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<GroupUIState> = MutableStateFlow(GroupUIState())
    val state: StateFlow<GroupUIState> = _state

    init {
        getUserGroups()
        getCurrentGroup()
    }

    private fun getCurrentGroup() {
        _state.value = _state.value.copy(
            currentGroupId = groupRepository.getCurrentGroup()
        )
    }

    private fun getUserGroups() {
        viewModelScope.launch {
            val response = groupRepository.getUserGroups()
            if (response is Resource.Success) {
                _state.value = _state.value.copy(
                    userGroups = response.data
                )
            }
        }
    }
}