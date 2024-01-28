package com.madteam.split.data.datasource.group

import com.madteam.split.domain.model.Group
import com.madteam.split.domain.model.Member
import com.madteam.split.utils.network.Resource

interface GroupDataSourceContract {

    interface Remote {
        suspend fun createGroup(
            name: String,
            description: String,
            members: List<Member>,
        ): Resource<Group>
    }

    interface Local {
        suspend fun insertUserGroups(groups: List<Group>)
        suspend fun deleteAllUserGroups()
        suspend fun getUserGroups(
            refresh: Boolean,
        ): Resource<List<Group>>

        suspend fun getUserGroupById(groupId: Int): Resource<Group>
        suspend fun deleteUserGroupById(groupId: Int)
        suspend fun updateUserGroup(group: Group)
    }
}