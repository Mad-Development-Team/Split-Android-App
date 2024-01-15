package com.madteam.split.ui.screens.creategroup.info.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.ui.screens.creategroup.info.state.CreateGroupInfoUIEvent
import com.madteam.split.ui.screens.creategroup.info.state.CreateGroupInfoUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CreateGroupInfoViewModel @Inject constructor(

) : ViewModel() {

    private val _state: MutableStateFlow<CreateGroupInfoUIState> =
        MutableStateFlow(CreateGroupInfoUIState())
    val state: StateFlow<CreateGroupInfoUIState> = _state

    fun onEvent(event: CreateGroupInfoUIEvent) {
        when (event) {
            is CreateGroupInfoUIEvent.OnGroupNameChange -> {
                changeGroupName(event.groupName)
            }

            is CreateGroupInfoUIEvent.OnGroupDescriptionChange -> {
                changeGroupDescription(event.groupDescription)
            }
        }
    }

    private fun changeGroupDescription(groupDescription: String) {
        _state.value = _state.value.copy(groupDescription = groupDescription)
    }

    private fun changeGroupName(groupName: String) {
        _state.value = _state.value.copy(groupName = groupName)
    }
}