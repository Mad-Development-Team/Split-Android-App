package com.madteam.split.ui.screens.groupexpenses.state

import com.madteam.split.domain.model.ExpenseFilter
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.domain.model.Member

data class GroupExpensesUIState(
    val groupsModalIsVisible: Boolean = false,
    val availableFilters: List<ExpenseFilter> = listOf(),
    val categoryFilterDialogIsVisible: Boolean = false,
    val payerFilterDialogIsVisible: Boolean = false,
    val amountFilterDialogIsVisible: Boolean = false,
    val selectedAmountFilter: Pair<Double, Double> = Pair(0.0, 0.0),
    val selectedCategoriesFilter: List<ExpenseType> = listOf(),
    val selectedPayersFilter: List<Member> = listOf(),
    val isAnyFilterActive: Boolean = false,
)
