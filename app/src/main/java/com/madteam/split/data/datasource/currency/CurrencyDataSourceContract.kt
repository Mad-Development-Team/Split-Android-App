package com.madteam.split.data.datasource.currency

import com.madteam.split.domain.model.Currency
import com.madteam.split.utils.network.Resource

interface CurrencyDataSourceContract {

    interface Local {
        suspend fun insertCurrencies(currencies: List<Currency>)
        suspend fun deleteAllCurrencies()
        suspend fun getCurrencies(update: Boolean): Resource<List<Currency>>
    }
}