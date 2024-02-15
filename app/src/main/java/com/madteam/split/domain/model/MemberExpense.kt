package com.madteam.split.domain.model

import com.madteam.split.data.model.request.MemberExpensesDTO

data class MemberExpense(
    val expenseId: Int,
    val memberId: Int,
    val amount: Double,
) {
    constructor() : this(0, 0, 0.0)
}

fun MemberExpense.toDto() = MemberExpensesDTO(
    memberId = memberId,
    expenseId = expenseId,
    amount = amount
)

fun List<MemberExpense>.toDTO() = map { it.toDto() }
