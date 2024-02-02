package com.madteam.split.data.datasource.currency

import com.madteam.split.data.api.CurrencyApi
import com.madteam.split.data.database.currency.dao.CurrencyDao
import com.madteam.split.data.database.currency.entities.toEntity
import com.madteam.split.data.database.currency.entities.toModel
import com.madteam.split.data.model.response.toModel
import com.madteam.split.domain.model.Currency
import com.madteam.split.domain.model.toEntity
import com.madteam.split.utils.network.Resource
import javax.inject.Inject

class CurrencyDataSource @Inject constructor(
    private val api: CurrencyApi,
    private val dao: CurrencyDao,
) : CurrencyDataSourceContract.Local {
    override suspend fun insertCurrencies(currencies: List<Currency>) {
        dao.insertCurrencies(currencies.toEntity())
    }

    override suspend fun deleteAllCurrencies() {
        dao.deleteAllCurrencies()
    }

    override suspend fun getCurrencies(
        update: Boolean,
    ): Resource<List<Currency>> {
        val localCurrencies = dao.getAllCurrencies()
        return if (localCurrencies.isNotEmpty() && !update) {
            Resource.Success(localCurrencies.toModel())
        } else {
            val remoteCurrencies = api.getCurrencies()
            if (remoteCurrencies.isNotEmpty()) {
                dao.insertCurrencies(remoteCurrencies.toEntity())
                Resource.Success(remoteCurrencies.toModel())
            } else {
                Resource.Error(
                    exception = Exception("Error"),
                    errorMessage = "Error trying to get currencies"
                )
            }
        }
    }
}