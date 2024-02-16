package com.madteam.split.domain.model

data class Balance(
    val id: Int,
    val groupId: Int,
    val payMemberId: Int,
    val receiverMemberId: Int,
    val amount: Double,
)
