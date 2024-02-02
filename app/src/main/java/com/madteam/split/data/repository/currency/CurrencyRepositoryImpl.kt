package com.madteam.split.data.repository.currency

import com.madteam.split.data.datasource.currency.CurrencyDataSourceContract
import com.madteam.split.domain.model.Currency
import com.madteam.split.utils.network.Resource
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val dataSource: CurrencyDataSourceContract.Local,
) : CurrencyRepository {

    override suspend fun getCurrencies(update: Boolean): Resource<List<Currency>> {
        return try {
            dataSource.getCurrencies(update)
        } catch (e: Exception) {
            Resource.Error(
                exception = Exception("Error"),
                errorMessage = "Error trying to get currencies"
            )
        }
    }

    override suspend fun deleteAllCurrencies() {
        dataSource.deleteAllCurrencies()
    }
}