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
}