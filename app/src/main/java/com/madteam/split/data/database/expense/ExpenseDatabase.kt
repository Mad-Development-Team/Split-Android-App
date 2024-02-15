package com.madteam.split.data.database.expense

import androidx.room.Database
import androidx.room.RoomDatabase
import com.madteam.split.data.database.expense.dao.ExpenseDAO
import com.madteam.split.data.database.expense.entity.ExpenseEntity

@Database(entities = [ExpenseEntity::class], version = 1)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun getExpenseDao(): ExpenseDAO
}