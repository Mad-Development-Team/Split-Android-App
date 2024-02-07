package com.madteam.split.data.database.group

import androidx.room.Database
import androidx.room.RoomDatabase
import com.madteam.split.data.database.group.dao.ExpenseTypeDAO
import com.madteam.split.data.database.group.entities.ExpenseTypeEntity

@Database(entities = [ExpenseTypeEntity::class], version = 1)
abstract class ExpenseTypeDatabase : RoomDatabase() {
    abstract fun getExpenseTypeDao(): ExpenseTypeDAO
}