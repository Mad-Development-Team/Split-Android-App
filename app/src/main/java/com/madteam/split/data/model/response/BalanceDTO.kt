package com.madteam.split.data.model.response

import com.google.gson.annotations.SerializedName
import com.madteam.split.data.database.balance.entities.BalanceEntity
import com.madteam.split.domain.model.Balance

data class BalanceDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("groupId")
    val groupId: Int,
    @SerializedName("payMemberId")
    val payMemberId: Int,
    @SerializedName("receiverMemberId")
    val receiverMemberId: Int,
    @SerializedName("amount")
    val amount: Double,
)

fun BalanceDTO.toDomain() = Balance(
    id = id,
    groupId = groupId,
    payMemberId = payMemberId,
    receiverMemberId = receiverMemberId,
    amount = amount,
)

fun List<BalanceDTO>.toDomain() = map { it.toDomain() }

fun BalanceDTO.toEntity() = BalanceEntity(
    id = id,
    groupId = groupId,
    payMemberId = payMemberId,
    receiverMemberId = receiverMemberId,
    amount = amount,
)

fun List<BalanceDTO>.toEntity() = map { it.toEntity() }
