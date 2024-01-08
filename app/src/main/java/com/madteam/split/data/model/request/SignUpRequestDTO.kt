package com.madteam.split.data.model.request

import com.google.gson.annotations.SerializedName

data class SignUpRequestDTO(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("name")
    val name: String
)
