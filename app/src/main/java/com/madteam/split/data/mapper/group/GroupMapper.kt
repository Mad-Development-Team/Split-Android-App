package com.madteam.split.data.mapper.group

import com.madteam.split.data.model.response.GroupDTO
import com.madteam.split.domain.model.Group

fun GroupDTO.toGroup(): Group {
    return Group(
        id = id,
        name = groupName,
        description = groupDescription ?: "",
        members = emptyList(),
        createdDate = createdDate,
        bannerImage = bannerImage ?: "",
        image = image ?: "",
        inviteCode = inviteCode,
    )
}

fun GroupDTO.toDomainModel(): Group {
    return Group(
        id = this.id,
        name = this.groupName,
        description = this.groupDescription ?: "",
        inviteCode = this.inviteCode,
        image = this.image ?: "",
        bannerImage = this.bannerImage ?: "",
        createdDate = this.createdDate,
        members = emptyList()
    )
}