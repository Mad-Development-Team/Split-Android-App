package com.madteam.split.domain.model

import com.madteam.split.data.database.balance.entities.BalanceEntity

data class Balance(
    val id: Int,
    val groupId: Int,
    val payMemberId: Int,
    val receiverMemberId: Int,
    val amount: Double,
)

fun Balance.toEntity() = BalanceEntity(
    id = this.id,
    groupId = this.groupId,
    payMemberId = this.payMemberId,
    receiverMemberId = this.receiverMemberId,
    amount = this.amount,
)

fun List<Balance>.toEntity() = this.map { it.toEntity() }
