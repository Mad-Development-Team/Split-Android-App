package com.madteam.split.data.database.balance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.madteam.split.data.database.balance.dao.BalanceDao
import com.madteam.split.data.database.balance.entities.BalanceEntity

@Database(entities = [BalanceEntity::class], version = 1)
abstract class BalanceDatabase : RoomDatabase() {
    abstract fun getBalanceDao(): BalanceDao
}