package com.madteam.split.ui.screens.creategroup.info.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.data.repository.group.GroupRepository
import com.madteam.split.ui.screens.creategroup.info.state.CreateGroupInfoUIEvent
import com.madteam.split.ui.screens.creategroup.info.state.CreateGroupInfoUIState
import com.madteam.split.ui.utils.validateGroupName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

private const val MAX_CHAR_GROUP_DESCRIPTION_LENGTH = 250

@HiltViewModel
class CreateGroupInfoViewModel @Inject constructor(
    private val createGroupRepository: GroupRepository,
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

            is CreateGroupInfoUIEvent.OnNextClick -> {
                saveGroupNameAndDescription()
            }
        }
    }

    private fun changeGroupDescription(groupDescription: String) {
        _state.value = _state.value.copy(
            groupDescription = groupDescription,
            isGroupDescriptionValid = groupDescription.length <= MAX_CHAR_GROUP_DESCRIPTION_LENGTH
        )
    }

    private fun changeGroupName(groupName: String) {
        _state.value = _state.value.copy(
            groupName = groupName,
            isGroupNameValid = validateGroupName(groupName)
        )
    }

    private fun saveGroupNameAndDescription() {
        createGroupRepository.setNameAndDescription(
            state.value.groupName,
            state.value.groupDescription
        )
    }
}