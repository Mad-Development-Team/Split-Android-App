package com.madteam.split.data.database.currency

import androidx.room.Database
import androidx.room.RoomDatabase
import com.madteam.split.data.database.currency.dao.CurrencyDao
import com.madteam.split.data.database.currency.entities.CurrencyEntity

@Database(entities = [CurrencyEntity::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun getCurrencyDao(): CurrencyDao
}