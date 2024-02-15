package com.madteam.split.ui.screens.editexpense.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madteam.split.data.repository.expense.ExpenseRepository
import com.madteam.split.data.repository.group.GroupRepository
import com.madteam.split.domain.model.Expense
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.domain.model.Group
import com.madteam.split.domain.model.MemberExpense
import com.madteam.split.domain.model.PaidByExpense
import com.madteam.split.ui.screens.editexpense.state.EditExpenseUIEvent
import com.madteam.split.ui.screens.editexpense.state.EditExpenseUIState
import com.madteam.split.ui.utils.validateExpenseDescription
import com.madteam.split.ui.utils.validateExpenseTitle
import com.madteam.split.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditExpenseViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val expenseRepository: ExpenseRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<EditExpenseUIState> =
        MutableStateFlow(EditExpenseUIState())
    val state: StateFlow<EditExpenseUIState> = _state

    init {
        getExpenseTypes()
    }

    fun onEvent(event: EditExpenseUIEvent) {
        when (event) {
            is EditExpenseUIEvent.OnLoadExpense -> {
                loadExpense(event.expense, event.groupInfo)
            }

            is EditExpenseUIEvent.OnExpenseTitleChange -> {
                changeExpenseTitle(event.title)
            }

            is EditExpenseUIEvent.OnShowExpenseTypeDialog -> {
                showExpenseTypeDialog(event.state)
            }

            is EditExpenseUIEvent.OnExpenseDescriptionChanged -> {
                changeExpenseDescription(event.description)
            }

            is EditExpenseUIEvent.OnExpenseAmountChanged -> {
                changeExpenseAmount(event.amount)
            }

            is EditExpenseUIEvent.OnShowCurrenciesDialog -> {
                showCurrencyDialog(event.state)
            }

            is EditExpenseUIEvent.OnDatePickerDialogShowChanged -> {
                showDatePickerDialog(event.state)
            }

            is EditExpenseUIEvent.OnPaidByMemberSelected -> {
                onPaidByMemberSelected(event.memberId)
            }

            is EditExpenseUIEvent.OnMemberNeedsToPaySelected -> {
                onMemberNeedsToPaySelected(event.memberId)
            }

            is EditExpenseUIEvent.OnAllMembersNeedsToPaySelected -> {
                onAllMembersNeedsToPaySelected()
            }

            is EditExpenseUIEvent.OnDatePickerDateSelected -> {
                onDatePickerDateSelected(event.date)
            }

            is EditExpenseUIEvent.OnExpenseTypeSelected -> {
                onExpenseTypeSelected(event.expenseType)
            }

            is EditExpenseUIEvent.OnExpenseTypeCreated -> {
                createExpenseType(event.expenseType)
            }

            is EditExpenseUIEvent.OnErrorDialogShowChanged -> {
                showErrorDialog(event.state)
            }

            is EditExpenseUIEvent.OnDeleteDialogShowChanged -> {
                showDeleteDialog(event.state)
            }

            is EditExpenseUIEvent.OnDeleteExpense -> {
                deleteExpense()
            }

            is EditExpenseUIEvent.OnUpdateExpense -> {
                updateExpense()
            }
        }
    }

    private fun updateExpense() {
        viewModelScope.launch {
            showLoading(true)
            val response = expenseRepository.editGroupExpense(
                expense = _state.value.expense,
            )
            if (response is Resource.Success) {
                showLoading(false)
                _state.value = _state.value.copy(
                    isSuccess = true
                )
            } else {
                showLoading(false)
                showErrorDialog(true)
            }
        }
    }

    private fun deleteExpense() {
        viewModelScope.launch {
            showLoading(true)
            val response = expenseRepository.deleteGroupExpense(
                expenseId = _state.value.expense.id,
                groupId = _state.value.groupInfo.id
            )
            if (response is Resource.Success) {
                showLoading(false)
                _state.value = _state.value.copy(
                    isSuccess = true
                )
            } else {
                showLoading(false)
                showErrorDialog(true)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        _state.value = _state.value.copy(
            isLoading = state
        )
    }

    private fun showDeleteDialog(state: Boolean) {
        _state.value = _state.value.copy(
            deleteDialog = state
        )
    }

    private fun createExpenseType(expenseType: ExpenseType) {
        val newExpenseList = _state.value.groupExpenseTypes.toMutableList().apply {
            add(expenseType)
        }
        _state.value = _state.value.copy(
            groupExpenseTypes = newExpenseList
        )
    }

    private fun onExpenseTypeSelected(expenseType: ExpenseType) {
        _state.value = _state.value.copy(
            expense = _state.value.expense.copy(
                type = expenseType
            )
        )
    }

    private fun getExpenseTypes() {
        viewModelScope.launch {
            val expenseTypesResponse = groupRepository.getGroupExpenseTypes()
            if (expenseTypesResponse is Resource.Success) {
                _state.value = _state.value.copy(
                    groupExpenseTypes = expenseTypesResponse.data
                )
            } else {
                showErrorDialog(true)
            }
        }
    }

    private fun onDatePickerDateSelected(date: String) {
        _state.value = _state.value.copy(
            expense = _state.value.expense.copy(
                date = date
            )
        )
    }

    private fun showErrorDialog(state: Boolean) {
        _state.value = _state.value.copy(
            isLoading = false,
            isError = state
        )
    }

    private fun onAllMembersNeedsToPaySelected() {
        _state.value = _state.value.copy(
            needsToPaySelectedMember = _state.value.groupInfo.members.map { it.id }.toMutableList()
        )
        recalculateAmountToPayPerMember()
    }

    private fun onMemberNeedsToPaySelected(memberId: Int) {
        if (memberId == 0) return
        _state.value = _state.value.copy(
            needsToPaySelectedMember = _state.value.needsToPaySelectedMember.toMutableList().apply {
                if (contains(memberId)) {
                    remove(memberId)
                } else {
                    add(memberId)
                }
            }
        )
        recalculateAmountToPayPerMember()
    }

    private fun onPaidByMemberSelected(memberId: Int) {
        if (memberId == 0) return
        _state.value = _state.value.copy(
            paidBySelectedMembers = _state.value.paidBySelectedMembers.toMutableList().apply {
                if (contains(memberId)) {
                    remove(memberId)
                } else {
                    add(memberId)
                }
            }
        )
        recalculatePaidByAmountPerMember()
    }

    private fun recalculatePaidByAmountPerMember() {
        val amountPerMember =
            _state.value.expense.totalAmount / _state.value.paidBySelectedMembers.size
        val paidByList = _state.value.paidBySelectedMembers.map { memberId ->
            PaidByExpense(
                expenseId = 0,
                memberId = memberId,
                paidAmount = amountPerMember
            )
        }
        _state.value = _state.value.copy(
            expense = _state.value.expense.copy(
                paidBy = paidByList
            )
        )
    }

    private fun loadExpense(
        expense: Expense,
        groupInfo: Group,
    ) {
        _state.value = _state.value.copy(
            expense = expense,
            groupInfo = groupInfo,
            paidBySelectedMembers = expense.paidBy.map { it.memberId },
            needsToPaySelectedMember = expense.forWhom.map { it.memberId }
        )
    }

    private fun showDatePickerDialog(state: Boolean) {
        _state.value = _state.value.copy(
            showDatePickerDialog = state
        )
    }

    private fun changeExpenseTitle(title: String) {
        _state.value = _state.value.copy(
            expense = _state.value.expense.copy(
                title = title
            ),
            isTitleError = !validateExpenseTitle(title)
        )
    }

    private fun showExpenseTypeDialog(state: Boolean) {
        _state.value = _state.value.copy(
            showExpenseTypeDialog = state
        )
    }

    private fun changeExpenseDescription(description: String) {
        _state.value = _state.value.copy(
            expense = _state.value.expense.copy(
                description = description
            ),
            isDescriptionError = !validateExpenseDescription(description)
        )
    }

    private fun changeExpenseAmount(amount: Double) {
        _state.value = _state.value.copy(
            expense = _state.value.expense.copy(
                totalAmount = amount
            ),
            isAmountError = amount == 0.0
        )
        recalculatePaidByAmountPerMember()
        recalculateAmountToPayPerMember()
    }

    private fun recalculateAmountToPayPerMember() {
        if (_state.value.needsToPaySelectedMember.isEmpty()) return
        val amountPerMember =
            _state.value.expense.totalAmount / _state.value.needsToPaySelectedMember.size
        val needsToPayList = _state.value.needsToPaySelectedMember.map { memberId ->
            MemberExpense(
                expenseId = 0,
                memberId = memberId,
                amount = amountPerMember
            )
        }
        _state.value = _state.value.copy(
            expense = _state.value.expense.copy(
                forWhom = needsToPayList
            )
        )
    }

    private fun showCurrencyDialog(state: Boolean) {
        _state.value = _state.value.copy(
            showCurrenciesDialog = state
        )
    }
}