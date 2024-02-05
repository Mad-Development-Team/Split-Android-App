package com.madteam.split.data.model.response

import com.google.gson.annotations.SerializedName

data class GroupDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("groupName")
    val groupName: String,
    @SerializedName("groupDescription")
    val groupDescription: String?,
    @SerializedName("createdDate")
    val createdDate: String,
    @SerializedName("inviteCode")
    val inviteCode: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("bannerImage")
    val bannerImage: String?,
    @SerializedName("members")
    val members: List<MemberDTO>,
    @SerializedName("currency")
    val currency: CurrencyDTO,
)
