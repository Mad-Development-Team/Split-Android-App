package com.madteam.split.data.repository.group

import com.madteam.split.domain.model.Group
import com.madteam.split.domain.model.Member
import com.madteam.split.utils.network.Resource

interface GroupRepository {
    fun getNewGroup(): Group
    fun setNameAndDescription(name: String, description: String)
    fun setMembers(members: List<Member>)
    suspend fun createGroup(): Resource<Group>
    suspend fun getUserGroups(
        update: Boolean = false,
    ): Resource<List<Group>>

    fun setCurrentGroup(groupId: Int)
    fun getCurrentGroup(): Int?
}