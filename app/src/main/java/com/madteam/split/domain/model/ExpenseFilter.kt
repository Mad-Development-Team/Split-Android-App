package com.madteam.split.domain.model

data class ExpenseFilter(
    val title: Int,
    val icon: String? = null,
    val enabled: Boolean = true,
    val selected: Boolean = false,
    val onClick: () -> Unit,
)
