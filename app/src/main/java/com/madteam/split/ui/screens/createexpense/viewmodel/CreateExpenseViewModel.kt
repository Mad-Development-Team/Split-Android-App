package com.madteam.split.ui.screens.createexpense.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madteam.split.data.repository.group.GroupRepository
import com.madteam.split.data.repository.user.UserRepository
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
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<CreateExpenseUIState> =
        MutableStateFlow(CreateExpenseUIState())
    val state: StateFlow<CreateExpenseUIState> = _state

    init {
        getGroupInfo()
        getCurrentDateIntoExpense()
        getMyMemberId()
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

    private fun getMyMemberId() {
        viewModelScope.launch {
            val response = userRepository.getUserInfo()
            if (response is Resource.Success) {
                _state.value = _state.value.copy(
                    myMemberId = _state.value.groupInfo.members.firstOrNull {
                        it.user == response.data.id
                    }?.id
                )
                onPaidByMemberSelected(_state.value.myMemberId ?: 0)
            }
        }
    }

    private fun onPaidByMemberSelected(memberId: Int) {
        if (memberId == 0) return
        _state.value = _state.value.copy(
            selectedMembers = _state.value.selectedMembers.toMutableList().apply {
                if (contains(memberId)) {
                    remove(memberId)
                } else {
                    add(memberId)
                }
            }
        )
        recalculateAmountPerMember()
    }

    private fun recalculateAmountPerMember() {
        val amountPerMember =
            _state.value.newExpense.totalAmount / _state.value.selectedMembers.size
        val paidByList = _state.value.selectedMembers.map { memberId ->
            PaidByExpense(
                memberId = memberId,
                paidAmount = amountPerMember
            )
        }
        _state.value = _state.value.copy(
            newExpense = _state.value.newExpense.copy(
                paidBy = paidByList
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
        recalculateAmountPerMember()
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