package com.madteam.split.domain.model

import com.madteam.split.data.database.expense.entity.ExpenseEntity
import com.madteam.split.data.model.request.ExpenseDTO

data class Expense(
    val id: Int,
    val title: String,
    val description: String?,
    val totalAmount: Double,
    val type: ExpenseType,
    val paidBy: List<PaidByExpense>,
    val forWhom: List<MemberExpense>,
    val images: List<String>?,
    val paymentMethod: String?,
    val date: String,
    val group: Int,
    val currency: Currency,
) {
    constructor() : this(
        id = 0,
        title = "",
        description = "",
        totalAmount = 0.0,
        type = ExpenseType(
            id = 1,
            title = "Others",
            icon = "emoji_symbols_questionmark",
            group = 0
        ),
        paidBy = emptyList(),
        forWhom = emptyList(),
        images = emptyList(),
        paymentMethod = "",
        date = "",
        group = 0,
        currency = Currency()
    )
}

fun Expense.toDto() = ExpenseDTO(
    id = id,
    title = title,
    description = description,
    totalAmount = totalAmount,
    type = type.toDto(),
    paidBy = paidBy.toDTO(),
    forWhom = forWhom.toDTO(),
    images = images,
    paymentMethod = paymentMethod,
    date = date,
    group = group,
    currency = currency.toDto()
)

fun Expense.toEntity() = ExpenseEntity(
    id = id,
    title = title,
    description = description,
    totalAmount = totalAmount,
    type = type,
    paidBy = paidBy,
    forWhom = forWhom,
    images = images,
    paymentMethod = paymentMethod,
    date = date,
    groupId = group,
    currency = currency
)
