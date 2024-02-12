package com.madteam.split.ui.screens.groupexpenses.state

import com.madteam.split.domain.model.ExpenseFilter
import com.madteam.split.domain.model.ExpenseType

data class GroupExpensesUIState(
    val groupsModalIsVisible: Boolean = false,
    val availableFilters: List<ExpenseFilter> = listOf(),
    val categoryFilterDialogIsVisible: Boolean = false,
    val selectedCategoriesFilter: List<ExpenseType> = listOf(),
    val isAnyFilterActive: Boolean = false,
)
