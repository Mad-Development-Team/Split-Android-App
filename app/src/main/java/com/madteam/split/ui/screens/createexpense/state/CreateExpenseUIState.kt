package com.madteam.split.ui.screens.createexpense.state

import com.madteam.split.domain.model.Expense

data class CreateExpenseUIState(
    val newExpense: Expense = Expense(),
)
