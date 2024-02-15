package com.madteam.split.data.model.request

import com.google.gson.annotations.SerializedName
import com.madteam.split.domain.model.MemberExpense

data class MemberExpensesDTO(
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("expenseId")
    val expenseId: Int,
    @SerializedName("amount")
    val amount: Double,
)

fun MemberExpensesDTO.toModel() = MemberExpense(
    memberId = memberId,
    expenseId = expenseId,
    amount = amount
)

fun List<MemberExpensesDTO>.toModel() = map { it.toModel() }
