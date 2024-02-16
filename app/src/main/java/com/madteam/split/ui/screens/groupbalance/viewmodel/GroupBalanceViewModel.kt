package com.madteam.split.ui.screens.groupbalance.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.ui.screens.groupbalance.state.GroupBalanceUIEvent
import com.madteam.split.ui.screens.groupbalance.state.GroupBalanceUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GroupBalanceViewModel @Inject constructor(

) : ViewModel() {

    private val _state: MutableStateFlow<GroupBalanceUIState> =
        MutableStateFlow(GroupBalanceUIState())
    val state: StateFlow<GroupBalanceUIState> = _state

    fun onEvent(event: GroupBalanceUIEvent) {
        when (event) {
            is GroupBalanceUIEvent.ShowGroupsModal -> {
                showGroupsModal(event.show)
            }
        }
    }

    private fun showGroupsModal(show: Boolean) {
        _state.value = _state.value.copy(
            groupsModalIsVisible = show
        )
    }
}