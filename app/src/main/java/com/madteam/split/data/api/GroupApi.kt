package com.madteam.split.data.api

import com.madteam.split.data.config.EndpointsConstants.CREATE_GROUP
import com.madteam.split.data.config.EndpointsConstants.CREATE_GROUP_EXPENSE
import com.madteam.split.data.config.EndpointsConstants.DELETE_GROUP_EXPENSE
import com.madteam.split.data.config.EndpointsConstants.EDIT_GROUP_EXPENSE
import com.madteam.split.data.config.EndpointsConstants.GET_GROUP_BALANCES
import com.madteam.split.data.config.EndpointsConstants.GET_GROUP_EXPENSES
import com.madteam.split.data.config.EndpointsConstants.GET_GROUP_EXPENSE_TYPES
import com.madteam.split.data.config.EndpointsConstants.GET_USER_GROUPS
import com.madteam.split.data.model.request.CreateGroupDTO
import com.madteam.split.data.model.request.ExpenseDTO
import com.madteam.split.data.model.response.BalanceDTO
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

    @POST(CREATE_GROUP_EXPENSE)
    suspend fun createGroupExpense(
        @Body request: ExpenseDTO,
    ): List<BalanceDTO>

    @GET(GET_USER_GROUPS)
    suspend fun getUserGroups(): List<GroupDTO>

    @GET(GET_GROUP_EXPENSE_TYPES)
    suspend fun getGroupExpenseTypes(
        @Query("groupId") groupId: Int,
    ): List<ExpenseTypeDTO>

    @GET(GET_GROUP_EXPENSES)
    suspend fun getGroupExpenses(
        @Query("groupId") groupId: Int,
    ): List<ExpenseDTO>

    @GET(DELETE_GROUP_EXPENSE)
    suspend fun deleteGroupExpense(
        @Query("groupId") groupId: Int,
        @Query("expenseId") expenseId: Int,
    ): List<BalanceDTO>

    @POST(EDIT_GROUP_EXPENSE)
    suspend fun editGroupExpense(
        @Body request: ExpenseDTO,
    ): List<BalanceDTO>

    @GET(GET_GROUP_BALANCES)
    suspend fun getGroupBalances(
        @Query("groupId") groupId: Int,
    ): List<BalanceDTO>
}