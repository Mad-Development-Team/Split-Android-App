package com.madteam.split.data.mapper.member

import com.madteam.split.data.model.response.MemberDTO
import com.madteam.split.domain.model.Member

fun Member.toDto(): MemberDTO {
    return MemberDTO(
        id = id,
        name = name,
        profileImage = profileImage,
        user = user,
        color = color,
        joinedDate = joinedDate,
        groupId = groupId,
    )
}

fun List<Member>.toDtoList(): List<MemberDTO> {
    return this.map { member ->
        MemberDTO(
            id = member.id,
            name = member.name,
            profileImage = member.profileImage,
            user = member.user,
            color = member.color,
            joinedDate = member.joinedDate,
            groupId = member.groupId,
        )
    }
}

fun List<MemberDTO>.toDomainModel(): List<Member> {
    return this.map { member ->
        Member(
            id = member.id ?: 0,
            name = member.name,
            profileImage = member.profileImage,
            user = member.user,
            color = member.color,
            joinedDate = member.joinedDate,
            groupId = member.groupId,
        )
    }
}