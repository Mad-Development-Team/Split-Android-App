package com.madteam.split.ui.screens.createexpense.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madteam.split.data.repository.currency.CurrencyRepository
import com.madteam.split.data.repository.expense.ExpenseRepository
import com.madteam.split.data.repository.group.GroupRepository
import com.madteam.split.data.repository.user.UserRepository
import com.madteam.split.domain.model.Currency
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.domain.model.MemberExpense
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
    private val currencyRepository: CurrencyRepository,
    private val expenseRepository: ExpenseRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<CreateExpenseUIState> =
        MutableStateFlow(CreateExpenseUIState())
    val state: StateFlow<CreateExpenseUIState> = _state

    init {
        getGroupInfo()
        getCurrencies()
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

            is CreateExpenseUIEvent.OnNeedsToPayMemberSelected -> {
                onMemberNeedsToPaySelected(event.memberId)
            }

            is CreateExpenseUIEvent.OnAllMembersNeedsToPaySelected -> {
                onAllMembersSelected()
            }

            is CreateExpenseUIEvent.OnCurrencyDialogShowChanged -> {
                onCurrencyDialogShowChanged(event.show)
            }

            is CreateExpenseUIEvent.OnCurrencySelected -> {
                onCurrencySelected(event.currency)
            }

            is CreateExpenseUIEvent.OnExpenseTypeDialogShowChanged -> {
                onExpenseTypeDialogShowChanged(event.show)
            }

            is CreateExpenseUIEvent.OnExpenseTypeSelected -> {
                onExpenseTypeSelected(event.expenseType)
            }

            is CreateExpenseUIEvent.OnExpenseTypeCreated -> {
                onExpenseTypeCreated(event.expenseType)
            }

            is CreateExpenseUIEvent.OnCreateExpense -> {
                onCreateExpense()
            }

            is CreateExpenseUIEvent.OnErrorDialogShowChanged -> {
                showErrorDialog(event.show)
            }
        }
    }

    private fun onCreateExpense() {
        viewModelScope.launch {
            showLoading(true)
            val expenseToCreate = _state.value.newExpense.copy(
                currency = _state.value.currencySelected,
                group = _state.value.groupInfo.id
            )
            val response = expenseRepository.createGroupExpense(
                expenseToCreate
            )
            when (response) {
                is Resource.Success -> {
                    showLoading(false)
                    _state.value = _state.value.copy(
                        expenseCreatedSuccessfully = true
                    )
                }

                is Resource.Error -> {
                    showLoading(false)
                    showErrorDialog(true)
                }

                else -> {
                    showLoading(false)
                }
            }
        }
    }

    private fun showErrorDialog(state: Boolean) {
        _state.value = _state.value.copy(
            showErrorDialog = state
        )
    }

    private fun showLoading(state: Boolean) {
        _state.value = _state.value.copy(
            isLoading = state
        )
    }

    private fun onExpenseTypeCreated(expenseType: ExpenseType) {
        val newExpenseList = _state.value.groupExpenseTypes.toMutableList().apply {
            add(expenseType)
        }
        _state.value = _state.value.copy(
            groupExpenseTypes = newExpenseList
        )
    }

    private fun onExpenseTypeDialogShowChanged(show: Boolean) {
        _state.value = _state.value.copy(
            showExpenseTypeDialog = show
        )
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

    private fun onCurrencySelected(currency: Currency) {
        _state.value = _state.value.copy(
            currencySelected = currency
        )
    }

    private fun getCurrencies() {
        viewModelScope.launch {
            val response = currencyRepository.getCurrencies()
            if (response is Resource.Success) {
                _state.value = _state.value.copy(
                    currencies = response.data
                )
            }
        }
    }

    private fun onCurrencyDialogShowChanged(show: Boolean) {
        _state.value = _state.value.copy(
            showCurrencyDialog = show
        )
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

    private fun onAllMembersSelected() {
        _state.value = _state.value.copy(
            needsToPaySelectedMember = _state.value.groupInfo.members.map { it.id }.toMutableList()
        )
        recalculateAmountToPayPerMember()
    }

    private fun recalculateAmountToPayPerMember() {
        if (_state.value.needsToPaySelectedMember.isEmpty()) return
        val amountPerMember =
            _state.value.newExpense.totalAmount / _state.value.needsToPaySelectedMember.size
        val needsToPayList = _state.value.needsToPaySelectedMember.map { memberId ->
            MemberExpense(
                expenseId = 0,
                memberId = memberId,
                amount = amountPerMember
            )
        }
        _state.value = _state.value.copy(
            newExpense = _state.value.newExpense.copy(
                forWhom = needsToPayList
            )
        )
    }

    private fun recalculatePaidByAmountPerMember() {
        val amountPerMember =
            _state.value.newExpense.totalAmount / _state.value.paidBySelectedMembers.size
        val paidByList = _state.value.paidBySelectedMembers.map { memberId ->
            PaidByExpense(
                expenseId = 0,
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
            val expenseTypesResponse = groupRepository.getGroupExpenseTypes(
                update = true
            )
            if (expenseTypesResponse is Resource.Success) {
                _state.value = _state.value.copy(
                    groupExpenseTypes = expenseTypesResponse.data,
                    newExpense = _state.value.newExpense.copy(
                        type = expenseTypesResponse.data.find { it.title == "Other" }
                            ?: ExpenseType()
                    )
                )
            }
        }
    }

    private fun onExpenseTypeSelected(expenseType: ExpenseType) {
        _state.value = _state.value.copy(
            newExpense = _state.value.newExpense.copy(
                type = expenseType
            )
        )
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
        recalculatePaidByAmountPerMember()
        recalculateAmountToPayPerMember()
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