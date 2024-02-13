package com.madteam.split.ui.screens.groupexpenses.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material.icons.outlined.Person
import androidx.lifecycle.ViewModel
import com.madteam.split.R
import com.madteam.split.domain.model.ExpenseFilter
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.domain.model.Member
import com.madteam.split.ui.screens.groupexpenses.state.GroupExpensesUIEvent
import com.madteam.split.ui.screens.groupexpenses.state.GroupExpensesUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GroupExpensesViewModel @Inject constructor(
) : ViewModel() {

    private val _state: MutableStateFlow<GroupExpensesUIState> = MutableStateFlow(
        GroupExpensesUIState()
    )
    val state: StateFlow<GroupExpensesUIState> = _state

    init {
        setAvailableFilters()
    }

    fun onEvent(event: GroupExpensesUIEvent) {
        when (event) {
            is GroupExpensesUIEvent.ShowGroupsModal -> {
                onShowGroupsModal(event.show)
            }

            is GroupExpensesUIEvent.ShowCategoryFilterDialog -> {
                onShowCategoryFilterDialog(event.show)
            }

            is GroupExpensesUIEvent.SelectedCategoriesFilter -> {
                onSelectedCategoriesFilter(event.selectedCategories)
            }

            is GroupExpensesUIEvent.OnClearFilters -> {
                onClearFilters()
            }

            is GroupExpensesUIEvent.ShowPayerFilterDialog -> {
                onShowPayerFilterDialog(event.show)
            }

            is GroupExpensesUIEvent.SelectedPayersFilter -> {
                onSelectedPayersFilter(event.selectedPayers)
            }
        }
    }

    private fun onSelectedPayersFilter(selectedPayers: List<Member>) {
        _state.value = _state.value.copy(
            selectedPayersFilter = selectedPayers
        )
        checkIfAnyFilterIsActive()
    }

    private fun checkIfAnyFilterIsActive() {
        if (_state.value.selectedCategoriesFilter.isNotEmpty() ||
            _state.value.selectedPayersFilter.isNotEmpty()
        ) {
            _state.value = _state.value.copy(
                isAnyFilterActive = true
            )
        } else {
            _state.value = _state.value.copy(
                isAnyFilterActive = false
            )
        }
    }

    private fun onShowPayerFilterDialog(show: Boolean) {
        _state.value = _state.value.copy(
            payerFilterDialogIsVisible = show
        )
    }

    private fun onSelectedCategoriesFilter(selectedCategories: List<ExpenseType>) {
        _state.value = _state.value.copy(
            selectedCategoriesFilter = selectedCategories
        )
        checkIfAnyFilterIsActive()
    }

    private fun onShowCategoryFilterDialog(show: Boolean) {
        _state.value = _state.value.copy(
            categoryFilterDialogIsVisible = show
        )
    }

    private fun setAvailableFilters() {
        _state.value = _state.value.copy(
            availableFilters = listOf(
                ExpenseFilter(
                    title = R.string.date,
                    icon = Icons.Outlined.CalendarMonth,
                    enabled = true,
                    selected = false,
                    onClick = {
                    }
                ),
                ExpenseFilter(
                    title = R.string.category,
                    icon = Icons.Outlined.Category,
                    enabled = true,
                    selected = false,
                    onClick = {
                        onShowCategoryFilterDialog(true)
                    }
                ),
                ExpenseFilter(
                    title = R.string.amount,
                    icon = Icons.Outlined.Money,
                    enabled = true,
                    selected = false,
                    onClick = {}
                ),
                ExpenseFilter(
                    title = R.string.payer,
                    icon = Icons.Outlined.Person,
                    enabled = true,
                    selected = false,
                    onClick = {
                        onShowPayerFilterDialog(true)
                    }
                ),
            )
        )
    }

    private fun onClearFilters() {
        _state.value = _state.value.copy(
            selectedCategoriesFilter = listOf(),
            selectedPayersFilter = listOf(),
            isAnyFilterActive = false
        )
    }

    private fun onShowGroupsModal(show: Boolean) {
        _state.value = _state.value.copy(
            groupsModalIsVisible = show
        )
    }
}