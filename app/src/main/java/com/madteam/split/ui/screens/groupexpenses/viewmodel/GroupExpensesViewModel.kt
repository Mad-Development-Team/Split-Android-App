package com.madteam.split.ui.screens.groupexpenses.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.ui.screens.groupexpenses.state.GroupExpensesUIEvent
import com.madteam.split.ui.screens.groupexpenses.state.GroupExpensesUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GroupExpensesViewModel @Inject constructor(
) : ViewModel() {

    private val _state: MutableStateFlow<GroupExpensesUIState> = MutableStateFlow(
        GroupExpensesUIState()
    )
    val state: StateFlow<GroupExpensesUIState> = _state

    fun onEvent(event: GroupExpensesUIEvent) {
        when (event) {
            is GroupExpensesUIEvent.ShowGroupsModal -> {
                onShowGroupsModal(event.show)
            }
        }
    }

    private fun onShowGroupsModal(show: Boolean) {
        _state.value = _state.value.copy(
            groupsModalIsVisible = show
        )
    }
}