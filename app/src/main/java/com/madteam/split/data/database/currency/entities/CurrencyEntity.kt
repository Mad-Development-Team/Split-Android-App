package com.madteam.split.data.database.currency.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.madteam.split.data.model.response.CurrencyDTO
import com.madteam.split.domain.model.Currency

const val CURRENCY_TABLE_NAME = "currency_table"

@Entity(tableName = CURRENCY_TABLE_NAME)
data class CurrencyEntity(
    @PrimaryKey @ColumnInfo(name = "currency") var currency: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "symbol") var symbol: String,
)

fun CurrencyEntity.toModel() = Currency(
    currency = currency,
    name = name,
    symbol = symbol
)

fun List<CurrencyEntity>.toModel() = map { it.toModel() }

fun Currency.toEntity() = CurrencyEntity(
    currency = currency,
    name = name,
    symbol = symbol
)

fun CurrencyDTO.toEntity() = CurrencyEntity(
    currency = currency,
    name = name,
    symbol = symbol
)

fun List<CurrencyDTO>.toEntity() = map { it.toEntity() }
