package com.madteam.split.data.model.response

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("profileImage")
    val profileImage: String,
    @SerializedName("createdDate")
    val createdDate: String
)
