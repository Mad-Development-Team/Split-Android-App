package com.madteam.split.ui.screens.creategroup.members.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.ui.screens.creategroup.members.state.CreateGroupMembersUIEvent
import com.madteam.split.ui.screens.creategroup.members.state.CreateGroupMembersUIState
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
            else -> {
                // do nothing
            }
        }
    }
}