package com.madteam.split.ui.screens.createexpense.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.ui.screens.createexpense.state.CreateExpenseUIEvent
import com.madteam.split.ui.screens.createexpense.state.CreateExpenseUIState
import com.madteam.split.ui.utils.validateExpenseDescription
import com.madteam.split.ui.utils.validateExpenseTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CreateExpenseViewModel @Inject constructor(

) : ViewModel() {

    private val _state: MutableStateFlow<CreateExpenseUIState> =
        MutableStateFlow(CreateExpenseUIState())
    val state: StateFlow<CreateExpenseUIState> = _state

    fun onEvent(event: CreateExpenseUIEvent) {
        when (event) {
            is CreateExpenseUIEvent.OnTitleChanged -> {
                onExpenseTitleChanged(event.title)
            }

            is CreateExpenseUIEvent.OnDescriptionChanged -> {
                onExpenseDescriptionChanged(event.description)
            }
        }
    }

    private fun onExpenseDescriptionChanged(description: String) {
        _state.value = _state.value.copy(
            newExpense = _state.value.newExpense.copy(
                description = description
            ),
            isDescriptionError = !validateExpenseDescription(description)
        )
    }

    private fun onExpenseTitleChanged(title: String) {
        _state.value = _state.value.copy(
            newExpense = _state.value.newExpense.copy(
                title = title
            ),
            isTitleError = !validateExpenseTitle(title)
        )
    }
}