package com.madteam.split.ui.screens.createexpense.state

import com.madteam.split.domain.model.Currency
import com.madteam.split.domain.model.Expense
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.domain.model.Group

data class CreateExpenseUIState(
    val newExpense: Expense = Expense(),
    val groupInfo: Group = Group(),
    val myMemberId: Int? = null,
    val paidBySelectedMembers: MutableList<Int> = mutableListOf(),
    val needsToPaySelectedMember: MutableList<Int> = mutableListOf(),
    val isTitleError: Boolean = true,
    val isDescriptionError: Boolean = false,
    val isAmountError: Boolean = true,
    val showDatePickerDialog: Boolean = false,
    val showCurrencyDialog: Boolean = false,
    val currencies: List<Currency> = emptyList(),
    val currencySelected: Currency = Currency("EUR", "Euro", "€"),
    val showExpenseTypeDialog: Boolean = false,
    val groupExpenseTypes: List<ExpenseType> = emptyList(),
    val isLoading: Boolean = false,
    val showErrorDialog: Boolean = false,
    val expenseCreatedSuccessfully: Boolean = false,
)
