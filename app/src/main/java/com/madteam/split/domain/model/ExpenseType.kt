package com.madteam.split.domain.model

import com.madteam.split.data.model.response.ExpenseTypeDTO

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

fun ExpenseType.toDto() = ExpenseTypeDTO(
    id = id,
    title = title,
    icon = icon,
    group = group,
)
