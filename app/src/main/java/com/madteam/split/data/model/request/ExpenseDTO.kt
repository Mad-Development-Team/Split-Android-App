package com.madteam.split.data.model.request

import com.google.gson.annotations.SerializedName
import com.madteam.split.data.model.response.CurrencyDTO
import com.madteam.split.data.model.response.ExpenseTypeDTO

data class ExpenseDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("expenseTitle")
    val title: String,
    @SerializedName("expenseDescription")
    val description: String?,
    @SerializedName("totalAmount")
    val totalAmount: Double,
    @SerializedName("expenseType")
    val type: ExpenseTypeDTO,
    @SerializedName("createdDate")
    val date: String,
    @SerializedName("groupId")
    val group: Int,
    @SerializedName("currency")
    val currency: CurrencyDTO,
    @SerializedName("paymentMethod")
    val paymentMethod: String?,
    @SerializedName("images")
    val images: List<String>?,
    @SerializedName("paidBy")
    val paidBy: List<PaidByDTO>,
    @SerializedName("forWhom")
    val forWhom: List<MemberExpensesDTO>,
)
