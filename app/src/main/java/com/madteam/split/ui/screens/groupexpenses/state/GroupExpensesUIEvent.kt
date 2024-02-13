package com.madteam.split.ui.screens.groupexpenses.state

import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.domain.model.Member

sealed class GroupExpensesUIEvent {
    data class ShowGroupsModal(val show: Boolean) : GroupExpensesUIEvent()
    data class ShowCategoryFilterDialog(val show: Boolean) : GroupExpensesUIEvent()
    data class ShowPayerFilterDialog(val show: Boolean) : GroupExpensesUIEvent()
    data class SelectedCategoriesFilter(val selectedCategories: List<ExpenseType>) :
        GroupExpensesUIEvent()

    data class SelectedPayersFilter(val selectedPayers: List<Member>) : GroupExpensesUIEvent()
    data object OnClearFilters : GroupExpensesUIEvent()
}
