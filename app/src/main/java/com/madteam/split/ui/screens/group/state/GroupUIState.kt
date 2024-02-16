package com.madteam.split.ui.screens.group.state

import com.madteam.split.domain.model.Balance
import com.madteam.split.domain.model.Expense
import com.madteam.split.domain.model.Group

data class GroupUIState(
    val currentGroupId: Int? = null,
    val userGroups: List<Group> = listOf(),
    val currentGroup: Group = Group(),
    val isLoading: Boolean = false,
    val groupExpenses: List<Expense> = listOf(),
    val groupBalances: List<Balance> = listOf(),
    val errorRetrievingExpenses: Boolean = false,
    val isSuccess: Boolean = false,
    val currentExpense: Int? = null,
)
