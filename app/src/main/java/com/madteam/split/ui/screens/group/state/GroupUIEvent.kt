package com.madteam.split.ui.screens.group.state

import com.madteam.split.domain.model.Expense

sealed class GroupUIEvent {
    data object RetryGetGroupExpenses : GroupUIEvent()
    data class OnExpenseClick(val expense: Expense) : GroupUIEvent()
}
