package com.madteam.split.data.model.response

import com.google.gson.annotations.SerializedName

data class TokenResponseDTO(
    @SerializedName("token")
    val token: String
)
