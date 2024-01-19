package com.madteam.split.data.api

import com.madteam.split.data.config.EndpointsConstants.CREATE_GROUP
import com.madteam.split.data.model.request.CreateGroupDTO
import com.madteam.split.data.model.response.GroupDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface GroupApi {

    @POST(CREATE_GROUP)
    suspend fun createGroup(
        @Body request: CreateGroupDTO,
    ): GroupDTO
}