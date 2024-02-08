package com.madteam.split.data.database.expense

import androidx.room.Database
import androidx.room.RoomDatabase
import com.madteam.split.data.database.expense.entity.ExpenseEntity
import com.madteam.split.data.database.group.GroupDatabase

@Database(entities = [ExpenseEntity::class], version = 1)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun getExpenseDao(): GroupDatabase
}