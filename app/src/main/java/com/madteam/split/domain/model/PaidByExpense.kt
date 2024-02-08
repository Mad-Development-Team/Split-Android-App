package com.madteam.split.domain.model

import com.madteam.split.data.model.request.PaidByDTO

data class PaidByExpense(
    val expenseId: Int,
    val memberId: Int,
    val paidAmount: Double,
)

fun PaidByExpense.toDTO() = PaidByDTO(
    memberId = memberId,
    expenseId = expenseId,
    amount = paidAmount,
)

fun List<PaidByExpense>.toDTO() = map { it.toDTO() }
