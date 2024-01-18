package com.madteam.split.data.repository.creategroup

import com.madteam.split.domain.model.Group
import com.madteam.split.domain.model.Member

interface CreateGroupRepository {
    fun getNewGroup(): Group
    fun setNameAndDescription(name: String, description: String)
    fun setMembers(members: List<Member>)
}