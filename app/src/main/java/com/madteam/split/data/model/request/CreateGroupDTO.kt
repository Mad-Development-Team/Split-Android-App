package com.madteam.split.data.model.request

import com.google.gson.annotations.SerializedName
import com.madteam.split.data.model.response.MemberDTO

data class CreateGroupDTO(
    @SerializedName("groupName")
    val groupName: String,
    @SerializedName("groupDescription")
    val groupDescription: String?,
    @SerializedName("membersList")
    val membersList: List<MemberDTO>,
    @SerializedName("currency")
    val currency: String,
)
