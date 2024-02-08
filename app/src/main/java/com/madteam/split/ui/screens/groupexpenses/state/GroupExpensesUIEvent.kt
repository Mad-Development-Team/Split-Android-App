package com.madteam.split.ui.screens.groupexpenses.state

sealed class GroupExpensesUIEvent {
    data class ShowGroupsModal(val show: Boolean) : GroupExpensesUIEvent()
    data class GetGroupExpenses(val groupId: Int) : GroupExpensesUIEvent()
}
