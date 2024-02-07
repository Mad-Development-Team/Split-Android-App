package com.madteam.split.data.api

import com.madteam.split.data.config.EndpointsConstants.CREATE_GROUP
import com.madteam.split.data.config.EndpointsConstants.GET_GROUP_EXPENSE_TYPES
import com.madteam.split.data.config.EndpointsConstants.GET_USER_GROUPS
import com.madteam.split.data.model.request.CreateGroupDTO
import com.madteam.split.data.model.response.ExpenseTypeDTO
import com.madteam.split.data.model.response.GroupDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GroupApi {

    @POST(CREATE_GROUP)
    suspend fun createGroup(
        @Body request: CreateGroupDTO,
    ): GroupDTO

    @GET(GET_USER_GROUPS)
    suspend fun getUserGroups(): List<GroupDTO>

    @GET(GET_GROUP_EXPENSE_TYPES)
    suspend fun getGroupExpenseTypes(
        @Query("groupId") groupId: Int,
    ): List<ExpenseTypeDTO>
}