package com.madteam.split.ui.screens.groupexpenses.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material.icons.outlined.Person
import com.madteam.split.R
import com.madteam.split.domain.model.ExpenseFilter

data class GroupExpensesUIState(
    val groupsModalIsVisible: Boolean = false,
    val availableFilters: List<ExpenseFilter> = listOf(
        ExpenseFilter(
            title = R.string.date,
            icon = Icons.Outlined.CalendarMonth,
            enabled = true,
            selected = false,
            onClick = {}
        ),
        ExpenseFilter(
            title = R.string.category,
            icon = Icons.Outlined.Category,
            enabled = true,
            selected = false,
            onClick = {}
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
            onClick = {}
        ),
    ),
)
