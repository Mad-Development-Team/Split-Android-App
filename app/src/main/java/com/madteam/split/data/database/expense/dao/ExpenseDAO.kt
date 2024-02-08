package com.madteam.split.data.database.expense.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.madteam.split.data.database.expense.entity.EXPENSE_TABLE_NAME
import com.madteam.split.data.database.expense.entity.ExpenseEntity

@Dao
interface ExpenseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroupExpense(expense: ExpenseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroupExpenses(expenses: List<ExpenseEntity>) {
        expenses.forEach { insertGroupExpense(it) }
    }

    @Query("SELECT * FROM $EXPENSE_TABLE_NAME WHERE groupId = :groupId")
    suspend fun getGroupExpenses(groupId: Int): List<ExpenseEntity>

    @Query("DELETE FROM $EXPENSE_TABLE_NAME WHERE groupId = :groupId")
    suspend fun deleteGroupExpenses(groupId: Int)

    @Query("DELETE FROM $EXPENSE_TABLE_NAME")
    suspend fun deleteAllGroupExpenses()
}