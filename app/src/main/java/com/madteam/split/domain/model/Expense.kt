package com.madteam.split.domain.model

data class Expense(
    val id: Int,
    val title: String,
    val description: String?,
    val totalAmount: Double,
    val type: ExpenseType,
    val paidBy: List<Member>,
    val images: List<String>?,
    val paymentMethod: String?,
    val date: String?,
    val group: Int,
) {
    constructor() : this(
        id = 0,
        title = "",
        description = "",
        totalAmount = 0.0,
        type = ExpenseType(),
        paidBy = emptyList(),
        images = emptyList(),
        paymentMethod = "",
        date = "",
        group = 0,
    )
}
