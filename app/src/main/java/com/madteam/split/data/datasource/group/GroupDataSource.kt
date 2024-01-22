package com.madteam.split.data.datasource.group

import com.madteam.split.data.api.GroupApi
import com.madteam.split.data.mapper.group.toDomainModel
import com.madteam.split.data.mapper.member.toDtoList
import com.madteam.split.data.model.request.CreateGroupDTO
import com.madteam.split.domain.model.Group
import com.madteam.split.domain.model.Member
import com.madteam.split.utils.network.Resource
import retrofit2.HttpException
import javax.inject.Inject

class GroupDataSource @Inject constructor(
    private val api: GroupApi,
) : GroupDataSourceContract.Remote {

    override suspend fun createGroup(
        name: String,
        description: String,
        members: List<Member>,
    ): Resource<Group> {
        try {
            val response = api.createGroup(
                CreateGroupDTO(
                    groupName = name,
                    groupDescription = description,
                    membersList = members.toDtoList()
                )
            )
            return Resource.Success(response.toDomainModel())
        } catch (e: HttpException) {
            Resource.Error(
                exception = Exception("Error"),
                errorMessage = "Error trying to create new group: ${e.message}"
            )
        }
        return Resource.Error(
            exception = Exception("Error"),
            errorMessage = "Error trying to create new group"
        )
    }

    override suspend fun getUserGroups(): Resource<List<Group>> {
        try {
            val response = api.getUserGroups()
            return Resource.Success(response.map { it.toDomainModel() })
        } catch (e: HttpException) {
            Resource.Error(
                exception = Exception("Error"),
                errorMessage = "Error trying to get user groups: ${e.message}"
            )
        }
        return Resource.Error(
            exception = Exception("Error"),
            errorMessage = "Error trying to get user groups"
        )
    }
}