package com.madteam.split.domain.model

data class Currency(
    val currency: String,
    val name: String,
    val symbol: String,
    val enabled: Boolean = currency == "EUR",
)