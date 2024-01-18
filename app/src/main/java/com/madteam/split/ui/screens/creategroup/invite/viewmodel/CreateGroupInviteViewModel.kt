package com.madteam.split.ui.screens.creategroup.invite.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.ui.screens.creategroup.invite.state.CreateGroupInviteUIEvent
import com.madteam.split.ui.screens.creategroup.invite.state.CreateGroupInviteUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CreateGroupInviteViewModel @Inject constructor(

) : ViewModel() {

    private val _state: MutableStateFlow<CreateGroupInviteUIState> =
        MutableStateFlow(CreateGroupInviteUIState())
    val state: StateFlow<CreateGroupInviteUIState> = _state

    fun onEvent(event: CreateGroupInviteUIEvent) {
        when (event) {
            else -> {
                // do nothing
            }
        }
    }
}