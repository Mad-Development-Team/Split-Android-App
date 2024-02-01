package com.madteam.split.domain.model

data class ExpenseType(
    val id: Int,
    val title: String,
    val description: String?,
    val icon: String,
    val group: Int,
) {
    constructor() : this(
        id = 0,
        title = "",
        description = "",
        icon = "",
        group = 0,
    )
}
