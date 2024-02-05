package com.madteam.split.data.mapper.group

import com.madteam.split.data.database.group.entities.GroupEntity
import com.madteam.split.data.mapper.member.toDomainModel
import com.madteam.split.data.model.response.GroupDTO
import com.madteam.split.data.model.response.toModel
import com.madteam.split.domain.model.Group

fun GroupDTO.toDomainModel(): Group {
    return Group(
        id = this.id,
        name = this.groupName,
        description = this.groupDescription ?: "",
        inviteCode = this.inviteCode,
        image = this.image ?: "",
        bannerImage = this.bannerImage ?: "",
        createdDate = this.createdDate,
        members = this.members.toDomainModel(),
        currency = this.currency.toModel()
    )
}

fun GroupEntity.toDomainModel(): Group {
    return Group(
        id = this.id,
        name = this.name,
        description = this.description ?: "",
        inviteCode = this.inviteCode,
        image = this.image ?: "",
        bannerImage = this.bannerImage ?: "",
        createdDate = this.createdDate,
        members = this.members,
        currency = this.currency
    )
}

fun GroupDTO.toEntity(): GroupEntity {
    return GroupEntity(
        id = this.id,
        name = this.groupName,
        description = this.groupDescription ?: "",
        inviteCode = this.inviteCode,
        image = this.image ?: "",
        bannerImage = this.bannerImage ?: "",
        createdDate = this.createdDate,
        members = this.members.toDomainModel(),
        currency = this.currency.toModel()
    )
}

fun Group.toEntity(): GroupEntity {
    return GroupEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        inviteCode = this.inviteCode,
        image = this.image,
        bannerImage = this.bannerImage,
        createdDate = this.createdDate,
        members = this.members,
        currency = this.currency
    )
}