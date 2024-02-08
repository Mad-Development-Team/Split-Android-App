package com.madteam.split.data.database.expense.entity

import com.madteam.split.domain.model.Currency
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.domain.model.MemberExpense
import com.madteam.split.domain.model.PaidByExpense

data class ExpenseEntity(
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
)
