package com.madteam.split.data.database.balance.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.madteam.split.data.database.balance.entities.BALANCE_TABLE_NAME
import com.madteam.split.data.database.balance.entities.BalanceEntity

@Dao
interface BalanceDao {

    @Query("DELETE FROM $BALANCE_TABLE_NAME")
    suspend fun deleteAllBalances()

    @Query("SELECT * FROM $BALANCE_TABLE_NAME WHERE groupId = :groupId")
    suspend fun getGroupBalances(groupId: Int): List<BalanceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalances(balances: List<BalanceEntity>) {
        balances.forEach { insertBalance(it) }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalance(balance: BalanceEntity)
}