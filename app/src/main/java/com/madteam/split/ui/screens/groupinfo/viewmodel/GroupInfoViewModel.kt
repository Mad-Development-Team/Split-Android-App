package com.madteam.split.ui.screens.groupinfo.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.ui.screens.groupinfo.state.GroupInfoUIEvent
import com.madteam.split.ui.screens.groupinfo.state.GroupInfoUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GroupInfoViewModel @Inject constructor(
) : ViewModel() {

    private val _state: MutableStateFlow<GroupInfoUIState> = MutableStateFlow(GroupInfoUIState())
    val state: StateFlow<GroupInfoUIState> = _state

    fun onEvent(event: GroupInfoUIEvent) {
        when (event) {
            is GroupInfoUIEvent.ShowGroupsModal -> {
                showGroupsModal(event.show)
            }
        }
    }

    private fun showGroupsModal(show: Boolean) {
        _state.value = _state.value.copy(groupsModalIsVisible = show)
    }
}