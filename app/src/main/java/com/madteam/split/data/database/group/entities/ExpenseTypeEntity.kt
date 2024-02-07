package com.madteam.split.data.database.group.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.madteam.split.domain.model.ExpenseType

const val EXPENSE_TYPE_TABLE_NAME = "expense_type_table"

@Entity(tableName = EXPENSE_TYPE_TABLE_NAME)
data class ExpenseTypeEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "icon") val icon: String,
    @ColumnInfo(name = "group") val group: Int?,
)

fun ExpenseTypeEntity.toDomainModel() = ExpenseType(
    id = id,
    title = title,
    icon = icon,
    group = group,
)
