package com.madteam.split.ui.screens.groupexpenses.state

import com.madteam.split.domain.model.ExpenseFilter

data class GroupExpensesUIState(
    val groupsModalIsVisible: Boolean = false,
    val availableFilters: List<ExpenseFilter> = listOf(),
    val categoryFilterDialogIsVisible: Boolean = false,
)
