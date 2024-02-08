package com.madteam.split.data.database.group.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.madteam.split.data.database.expense.entity.ExpenseEntity
import com.madteam.split.data.database.group.entities.GROUP_TABLE_NAME
import com.madteam.split.data.database.group.entities.GroupEntity

@Dao
interface GroupDAO {

    @Query("SELECT * FROM $GROUP_TABLE_NAME")
    suspend fun getAllUserGroups(): List<GroupEntity>

    @Query("SELECT * FROM $GROUP_TABLE_NAME WHERE id = :groupId")
    suspend fun getUserGroupById(groupId: Int): GroupEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserGroups(groups: List<GroupEntity>) {
        groups.forEach { insertUserGroup(it) }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserGroup(group: GroupEntity)

    @Query("DELETE FROM $GROUP_TABLE_NAME")
    suspend fun deleteAllUserGroups()

    @Query("DELETE FROM $GROUP_TABLE_NAME WHERE id = :groupId")
    suspend fun deleteUserGroupById(groupId: Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserGroup(group: GroupEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroupExpense(expense: ExpenseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroupExpenses(expenses: List<ExpenseEntity>) {
        expenses.forEach { insertGroupExpense(it) }
    }

    @Query("SELECT * FROM $GROUP_TABLE_NAME WHERE id = :groupId")
    suspend fun getGroupExpenses(groupId: Int): List<ExpenseEntity>

    @Query("DELETE FROM $GROUP_TABLE_NAME WHERE id = :groupId")
    suspend fun deleteGroupExpenses(groupId: Int)

    @Query("DELETE FROM $GROUP_TABLE_NAME")
    suspend fun deleteAllGroupExpenses()
}