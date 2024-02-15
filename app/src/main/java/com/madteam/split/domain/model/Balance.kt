package com.madteam.split.domain.model

data class Balance(
    val groupId: Int,
    val payMemberId: Int,
    val receiverMemberId: Int,
    val amount: Double,
)
