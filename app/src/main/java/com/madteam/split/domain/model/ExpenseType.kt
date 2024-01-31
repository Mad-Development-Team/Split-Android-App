package com.madteam.split.domain.model

data class ExpenseType(
    val id: Int,
    val title: String,
    val description: String?,
    val icon: String,
    val group: Int,
)
