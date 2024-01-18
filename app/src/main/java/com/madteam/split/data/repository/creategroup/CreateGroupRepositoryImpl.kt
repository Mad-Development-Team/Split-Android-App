package com.madteam.split.data.repository.creategroup

import com.madteam.split.domain.model.Group
import com.madteam.split.domain.model.Member
import javax.inject.Inject

class CreateGroupRepositoryImpl @Inject constructor(
) : CreateGroupRepository {

    private var newGroup: Group

    init {
        newGroup = Group(
            id = 0,
            name = "",
            description = "",
            inviteCode = "",
            image = "",
            bannerImage = "",
            createDate = "",
            members = listOf()
        )
    }

    override fun getNewGroup(): Group = newGroup
    override fun setNameAndDescription(name: String, description: String) {
        newGroup = newGroup.copy(
            name = name,
            description = description
        )
    }

    override fun setMembers(members: List<Member>) {
        newGroup = newGroup.copy(
            members = members
        )
    }
}