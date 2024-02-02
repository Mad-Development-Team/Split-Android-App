package com.madteam.split.domain.model

data class MemberExpense(
    val memberId: Int,
    val amount: Double,
) {
    constructor() : this(0, 0.0)
}
