package com.madteam.split.domain.model

data class Expense(
    val id: Int,
    val title: String,
    val description: String?,
    val totalAmount: Double,
    val type: Int?,
    val paidBy: List<Member>,
    val images: List<String>?,
    val paymentMethod: String?,
    val date: String?,
    val group: Int,
)
