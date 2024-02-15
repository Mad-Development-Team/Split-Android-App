package com.madteam.split.data.model.request

import com.google.gson.annotations.SerializedName
import com.madteam.split.data.database.expense.entity.ExpenseEntity
import com.madteam.split.data.model.response.CurrencyDTO
import com.madteam.split.data.model.response.ExpenseTypeDTO
import com.madteam.split.data.model.response.toDomainModel
import com.madteam.split.data.model.response.toModel
import com.madteam.split.domain.model.Expense

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

fun ExpenseDTO.toEntity() = ExpenseEntity(
    id = id,
    title = title,
    description = description,
    totalAmount = totalAmount,
    type = type.toDomainModel(),
    date = date,
    groupId = group,
    currency = currency.toModel(),
    paymentMethod = paymentMethod,
    images = images,
    paidBy = paidBy.toModel(),
    forWhom = forWhom.toModel()
)

fun List<ExpenseDTO>.toEntity() = map { it.toEntity() }

fun ExpenseDTO.toDomainModel() = Expense(
    id = id,
    title = title,
    description = description,
    totalAmount = totalAmount,
    type = type.toDomainModel(),
    date = date,
    group = group,
    currency = currency.toModel(),
    paymentMethod = paymentMethod,
    images = images,
    paidBy = paidBy.toModel(),
    forWhom = forWhom.toModel()
)

fun List<ExpenseDTO>.toDomainModel() = map { it.toDomainModel() }
