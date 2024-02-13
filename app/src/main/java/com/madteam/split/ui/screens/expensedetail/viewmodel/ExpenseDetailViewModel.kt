package com.madteam.split.ui.screens.expensedetail.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.ui.screens.expensedetail.state.ExpenseDetailUIEvent
import com.madteam.split.ui.screens.expensedetail.state.ExpenseDetailUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(

) : ViewModel() {

    private val _state: MutableStateFlow<ExpenseDetailUIState> =
        MutableStateFlow(ExpenseDetailUIState())
    val state: StateFlow<ExpenseDetailUIState> = _state

    fun onEvent(event: ExpenseDetailUIEvent) {
        when (event) {
            else -> {
                // no-op
            }
        }
    }
}