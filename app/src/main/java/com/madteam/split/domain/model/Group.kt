package com.madteam.split.domain.model

data class Group(
    val id: Int,
    val name: String,
    val description: String,
    val inviteCode: String,
    val image: String,
    val bannerImage: String,
    val createdDate: String,
    val members: List<Member>,
    val currency: String,
) {
    constructor() : this(
        id = 0,
        name = "",
        description = "",
        inviteCode = "",
        image = "",
        bannerImage = "",
        createdDate = "",
        members = emptyList(),
        currency = ""
    )
}


