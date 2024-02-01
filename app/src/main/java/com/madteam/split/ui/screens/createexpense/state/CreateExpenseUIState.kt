package com.madteam.split.ui.screens.createexpense.state

import com.madteam.split.domain.model.Expense
import com.madteam.split.domain.model.Group

data class CreateExpenseUIState(
    val newExpense: Expense = Expense(),
    val groupInfo: Group = Group(),
    val selectedMembers: MutableList<Int> = mutableListOf(),
    val isTitleError: Boolean = true,
    val isDescriptionError: Boolean = false,
    val isAmountError: Boolean = true,
    val showDatePickerDialog: Boolean = false,
)
