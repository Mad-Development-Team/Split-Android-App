package com.madteam.split.ui.screens.groupexpenses.viewmodel

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

            is GroupExpensesUIEvent.ShowAmountFilterDialog -> {
                onShowAmountFilterDialog(event.show)
            }

            is GroupExpensesUIEvent.SelectedAmountFilter -> {
                onSelectedAmountFilter(event.minAmount, event.maxAmount)
            }
        }
    }

    private fun onSelectedAmountFilter(minAmount: Double, maxAmount: Double) {
        _state.value = _state.value.copy(
            selectedAmountFilter = Pair(minAmount, maxAmount)
        )
        checkIfAnyFilterIsActive()
    }

    private fun onShowAmountFilterDialog(show: Boolean) {
        _state.value = _state.value.copy(
            amountFilterDialogIsVisible = show
        )
    }

    private fun onSelectedPayersFilter(selectedPayers: List<Member>) {
        _state.value = _state.value.copy(
            selectedPayersFilter = selectedPayers
        )
        checkIfAnyFilterIsActive()
    }

    private fun checkIfAnyFilterIsActive() {
        if (_state.value.selectedCategoriesFilter.isNotEmpty() ||
            _state.value.selectedPayersFilter.isNotEmpty() ||
            _state.value.selectedAmountFilter.first != 0.0 ||
            _state.value.selectedAmountFilter.second != 0.0
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
                    title = R.string.category,
                    icon = "label",
                    enabled = true,
                    selected = false,
                    onClick = {
                        onShowCategoryFilterDialog(true)
                    }
                ),
                /* TODO: Implement this filter, slider is not working
                               ExpenseFilter(
                                   title = R.string.amount,
                                   icon = Icons.Outlined.Money,
                                   enabled = true,
                                   selected = false,
                                   onClick = {
                                       onShowAmountFilterDialog(true)
                                   }
                               ), */
                ExpenseFilter(
                    title = R.string.payer,
                    icon = "moneywithwings",
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
            selectedAmountFilter = Pair(0.0, 0.0),
            isAnyFilterActive = false,
        )
    }

    private fun onShowGroupsModal(show: Boolean) {
        _state.value = _state.value.copy(
            groupsModalIsVisible = show
        )
    }
}