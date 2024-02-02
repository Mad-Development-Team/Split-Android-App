package com.madteam.split.domain.model

import com.madteam.split.data.database.currency.entities.CurrencyEntity

data class Currency(
    val currency: String,
    val name: String,
    val symbol: String,
    val enabled: Boolean = currency == "EUR",
)

fun Currency.toEntity() = CurrencyEntity(
    currency = currency,
    name = name,
    symbol = symbol
)

fun List<Currency>.toEntity() = map { it.toEntity() }