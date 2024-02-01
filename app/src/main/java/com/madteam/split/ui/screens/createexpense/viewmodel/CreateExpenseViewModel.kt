package com.madteam.split.ui.screens.createexpense.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madteam.split.data.repository.group.GroupRepository
import com.madteam.split.domain.model.PaidByExpense
import com.madteam.split.ui.screens.createexpense.state.CreateExpenseUIEvent
import com.madteam.split.ui.screens.createexpense.state.CreateExpenseUIState
import com.madteam.split.ui.utils.getCurrentDate
import com.madteam.split.ui.utils.validateExpenseDescription
import com.madteam.split.ui.utils.validateExpenseTitle
import com.madteam.split.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateExpenseViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<CreateExpenseUIState> =
        MutableStateFlow(CreateExpenseUIState())
    val state: StateFlow<CreateExpenseUIState> = _state

    init {
        getGroupInfo()
        getCurrentDateIntoExpense()
    }

    fun onEvent(event: CreateExpenseUIEvent) {
        when (event) {
            is CreateExpenseUIEvent.OnTitleChanged -> {
                onExpenseTitleChanged(event.title)
            }

            is CreateExpenseUIEvent.OnDescriptionChanged -> {
                onExpenseDescriptionChanged(event.description)
            }

            is CreateExpenseUIEvent.OnAmountChanged -> {
                onExpenseAmountChanged(event.amount)
            }

            is CreateExpenseUIEvent.OnDatePickerDialogShowChanged -> {
                onDatePickerDialogShowChanged(event.show)
            }

            is CreateExpenseUIEvent.OnDatePickerDateSelected -> {
                onDatePickerDateSelected(event.date)
            }

            is CreateExpenseUIEvent.OnPaidByMemberSelected -> {
                onPaidByMemberSelected(event.memberId)
            }
        }
    }

    private fun onPaidByMemberSelected(memberId: Int) {
        _state.value = _state.value.copy(
            newExpense = _state.value.newExpense.copy(
                paidBy = listOf(
                    PaidByExpense(
                        memberId = memberId,
                        paidAmount = _state.value.newExpense.totalAmount
                    )
                )
            )
        )
    }

    private fun getGroupInfo() {
        val currentGroup = groupRepository.getCurrentGroup()
        viewModelScope.launch {
            val response = groupRepository.getUserGroups()
            if (response is Resource.Success) {
                _state.value = _state.value.copy(
                    groupInfo = response.data.first { it.id == currentGroup }
                )
            }
        }
    }

    private fun onDatePickerDateSelected(date: String) {
        _state.value = _state.value.copy(
            newExpense = _state.value.newExpense.copy(
                date = date
            )
        )
    }

    private fun onDatePickerDialogShowChanged(show: Boolean) {
        _state.value = _state.value.copy(
            showDatePickerDialog = show
        )
    }

    private fun getCurrentDateIntoExpense() {
        _state.value = _state.value.copy(
            newExpense = _state.value.newExpense.copy(
                date = getCurrentDate()
            )
        )
    }

    private fun onExpenseAmountChanged(amount: Double) {
        _state.value = _state.value.copy(
            newExpense = _state.value.newExpense.copy(
                totalAmount = amount
            ),
            isAmountError = amount == 0.0
        )
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