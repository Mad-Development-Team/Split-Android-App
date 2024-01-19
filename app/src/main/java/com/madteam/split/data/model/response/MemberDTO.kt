package com.madteam.split.data.model.response

import com.google.gson.annotations.SerializedName

data class MemberDTO(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileImage")
    val profileImage: String?,
    @SerializedName("user")
    val user: Int?,
    @SerializedName("color")
    val color: String?,
    @SerializedName("joinedDate")
    val joinedDate: String,
    @SerializedName("groupId")
    val groupId: Int,
)
