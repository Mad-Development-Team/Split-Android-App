package com.madteam.split.data.database.balance.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.madteam.split.domain.model.Balance

const val BALANCE_TABLE_NAME = "balance_table"

@Entity(tableName = BALANCE_TABLE_NAME)
data class BalanceEntity(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "groupId") var groupId: Int,
    @ColumnInfo(name = "payMemberId") var payMemberId: Int,
    @ColumnInfo(name = "receiverMemberId") var receiverMemberId: Int,
    @ColumnInfo(name = "amount") var amount: Double,
)

fun BalanceEntity.toModel() = Balance(
    id = id,
    groupId = groupId,
    payMemberId = payMemberId,
    receiverMemberId = receiverMemberId,
    amount = amount
)

fun List<BalanceEntity>.toModel() = map { it.toModel() }
