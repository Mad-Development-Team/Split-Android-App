package com.madteam.split.domain.model

data class Group(
    val id: Int,
    val name: String,
    val description: String,
    val inviteCode: String,
    val image: String,
    val bannerImage: String,
    val createDate: String,
    val members: List<Member>,
)


