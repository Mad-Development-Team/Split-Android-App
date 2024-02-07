package com.madteam.split.data.model.response

import com.madteam.split.data.database.group.entities.ExpenseTypeEntity
import com.madteam.split.domain.model.ExpenseType

data class ExpenseTypeDTO(
    val id: Int,
    val title: String,
    val icon: String,
    val group: Int?,
)

fun ExpenseTypeDTO.toDomainModel() = ExpenseType(
    id = id,
    title = title,
    icon = icon,
    group = group,
)

fun List<ExpenseTypeDTO>.toDomainModel() = map { it.toDomainModel() }

fun ExpenseType.toEntity() = ExpenseTypeEntity(
    id = id,
    title = title,
    icon = icon,
    group = group,
)
