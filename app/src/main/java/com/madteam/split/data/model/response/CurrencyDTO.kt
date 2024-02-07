package com.madteam.split.data.model.response

import com.google.gson.annotations.SerializedName
import com.madteam.split.domain.model.Currency

data class CurrencyDTO(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
)

fun CurrencyDTO.toModel() = Currency(
    currency = currency,
    name = name,
    symbol = symbol
)

fun List<CurrencyDTO>.toModel() = map { it.toModel() }
