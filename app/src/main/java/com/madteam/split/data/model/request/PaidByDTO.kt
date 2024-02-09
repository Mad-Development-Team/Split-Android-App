package com.madteam.split.data.model.request

import com.google.gson.annotations.SerializedName
import com.madteam.split.domain.model.PaidByExpense

data class PaidByDTO(
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("expenseId")
    val expenseId: Int,
    @SerializedName("amount")
    val amount: Double,
)

fun PaidByDTO.toModel() = PaidByExpense(
    memberId = memberId,
    expenseId = expenseId,
    paidAmount = amount
)

fun List<PaidByDTO>.toModel() = map { it.toModel() }
