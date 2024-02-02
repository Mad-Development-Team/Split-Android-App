package com.madteam.split.data.repository.currency

import com.madteam.split.domain.model.Currency
import com.madteam.split.utils.network.Resource

interface CurrencyRepository {
    suspend fun getCurrencies(
        update: Boolean = false,
    ): Resource<List<Currency>>

    suspend fun deleteAllCurrencies()
}