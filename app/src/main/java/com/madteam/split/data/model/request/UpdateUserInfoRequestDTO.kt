package com.madteam.split.data.model.request

import com.google.gson.annotations.SerializedName

data class UpdateUserInfoRequestDTO(
  @SerializedName("name")
  val name: String,
  @SerializedName("profileImage")
  val profileImage: String,
)
