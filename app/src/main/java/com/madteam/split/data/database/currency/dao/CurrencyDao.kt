package com.madteam.split.data.database.currency.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.madteam.split.data.database.currency.entities.CURRENCY_TABLE_NAME
import com.madteam.split.data.database.currency.entities.CurrencyEntity

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM $CURRENCY_TABLE_NAME")
    suspend fun getAllCurrencies(): List<CurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<CurrencyEntity>) {
        currencies.forEach { insertCurrency(it) }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: CurrencyEntity)

    @Query("DELETE FROM $CURRENCY_TABLE_NAME")
    suspend fun deleteAllCurrencies()
}