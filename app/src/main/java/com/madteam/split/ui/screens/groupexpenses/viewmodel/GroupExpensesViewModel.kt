package com.madteam.split.ui.screens.groupexpenses.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madteam.split.data.repository.datastore.DatastoreManager
import com.madteam.split.data.repository.expense.ExpenseRepository
import com.madteam.split.ui.screens.groupexpenses.state.GroupExpensesUIEvent
import com.madteam.split.ui.screens.groupexpenses.state.GroupExpensesUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupExpensesViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val datastoreManager: DatastoreManager,
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

            is GroupExpensesUIEvent.GetGroupExpenses -> {
                onGetGroupExpenses(event.groupId)
            }
        }
    }

    private fun onShowGroupsModal(show: Boolean) {
        _state.value = _state.value.copy(
            groupsModalIsVisible = show
        )
    }

    private fun onGetGroupExpenses(groupId: Int) {
        viewModelScope.launch {

            val timeInMillis = System.currentTimeMillis()
            datastoreManager.saveString(groupId.toString(), timeInMillis.toString())
        }
    }
}