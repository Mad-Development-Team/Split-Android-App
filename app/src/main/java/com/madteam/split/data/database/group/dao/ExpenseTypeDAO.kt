package com.madteam.split.data.database.group.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.madteam.split.data.database.group.entities.EXPENSE_TYPE_TABLE_NAME
import com.madteam.split.data.database.group.entities.ExpenseTypeEntity

@Dao
interface ExpenseTypeDAO {

    @Query("SELECT * FROM $EXPENSE_TYPE_TABLE_NAME")
    suspend fun getAllExpenseTypes(): List<ExpenseTypeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpenseTypes(expenseTypes: List<ExpenseTypeEntity>) {
        expenseTypes.forEach { insertExpenseType(it) }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpenseType(expenseType: ExpenseTypeEntity)

    @Query("DELETE FROM $EXPENSE_TYPE_TABLE_NAME")
    suspend fun deleteAllExpenseTypes()
}