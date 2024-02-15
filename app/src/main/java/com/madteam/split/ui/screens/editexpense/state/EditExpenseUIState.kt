package com.madteam.split.ui.screens.editexpense.state

import com.madteam.split.domain.model.Expense
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.domain.model.Group

data class EditExpenseUIState(
    val expense: Expense = Expense(),
    val groupInfo: Group = Group(),
    val groupExpenseTypes: List<ExpenseType> = emptyList(),
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isTitleError: Boolean = false,
    val isDescriptionError: Boolean = false,
    val isAmountError: Boolean = false,
    val showExpenseTypeDialog: Boolean = false,
    val showCurrenciesDialog: Boolean = false,
    val showDatePickerDialog: Boolean = false,
    val paidBySelectedMembers: List<Int> = listOf(),
    val needsToPaySelectedMember: List<Int> = listOf(),
    val deleteDialog: Boolean = false,
    val isSuccess: Boolean = false,
)
