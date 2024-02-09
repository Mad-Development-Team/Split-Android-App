package com.madteam.split.data.database.expense.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.madteam.split.data.database.converters.Converters
import com.madteam.split.domain.model.Currency
import com.madteam.split.domain.model.Expense
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.domain.model.MemberExpense
import com.madteam.split.domain.model.PaidByExpense

const val EXPENSE_TABLE_NAME = "expense_table"

@TypeConverters(Converters::class)
@Entity(tableName = "expense_table")
data class ExpenseEntity(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "totalAmount") var totalAmount: Double,
    @ColumnInfo(name = "type") var type: ExpenseType,
    @ColumnInfo(name = "paidBy") var paidBy: List<PaidByExpense>,
    @ColumnInfo(name = "forWhom") var forWhom: List<MemberExpense>,
    @ColumnInfo(name = "images") var images: List<String>?,
    @ColumnInfo(name = "paymentMethod") var paymentMethod: String?,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "groupId") var groupId: Int,
    @ColumnInfo(name = "currency") var currency: Currency,
)

fun ExpenseEntity.toDomainModel(): Expense {
    return Expense(
        id = this.id,
        title = this.title,
        description = this.description,
        totalAmount = this.totalAmount,
        type = this.type,
        paidBy = this.paidBy,
        forWhom = this.forWhom,
        images = this.images,
        paymentMethod = this.paymentMethod,
        date = this.date,
        group = this.groupId,
        currency = this.currency
    )
}

fun List<ExpenseEntity>.toDomainModel(): List<Expense> {
    return this.map { it.toDomainModel() }
}
