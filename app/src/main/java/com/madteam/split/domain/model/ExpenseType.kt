package com.madteam.split.domain.model

data class ExpenseType(
    val id: Int,
    val title: String,
    val icon: String,
    val group: Int? = null,
) {
    constructor() : this(
        id = 0,
        title = "",
        icon = "",
        group = null,
    )
}
