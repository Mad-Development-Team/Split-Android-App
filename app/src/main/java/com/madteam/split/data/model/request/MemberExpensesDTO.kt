package com.madteam.split.data.model.request

import com.google.gson.annotations.SerializedName

data class MemberExpensesDTO(
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("expenseId")
    val expenseId: Int,
    @SerializedName("amount")
    val amount: Double,
)
