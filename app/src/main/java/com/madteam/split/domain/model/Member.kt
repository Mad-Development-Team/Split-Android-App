package com.madteam.split.domain.model

data class Member(
    val id: Int,
    val name: String,
    val profileImage: String?,
    val user: Int?,
    val color: String?,
    val joinedDate: String,
    val groupId: Int,
)
