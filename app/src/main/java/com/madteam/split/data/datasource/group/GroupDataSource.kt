package com.madteam.split.data.datasource.group

import com.madteam.split.data.api.GroupApi
import com.madteam.split.data.database.group.dao.ExpenseTypeDAO
import com.madteam.split.data.database.group.dao.GroupDAO
import com.madteam.split.data.database.group.entities.toDomainModel
import com.madteam.split.data.mapper.group.toDomainModel
import com.madteam.split.data.mapper.group.toEntity
import com.madteam.split.data.mapper.member.toDtoList
import com.madteam.split.data.model.request.CreateGroupDTO
import com.madteam.split.data.model.response.toDomain
import com.madteam.split.data.model.response.toDomainModel
import com.madteam.split.data.model.response.toEntity
import com.madteam.split.domain.model.Balance
import com.madteam.split.domain.model.Expense
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.domain.model.Group
import com.madteam.split.domain.model.Member
import com.madteam.split.domain.model.toDto
import com.madteam.split.domain.model.toEntity
import com.madteam.split.utils.network.Resource
import retrofit2.HttpException
import javax.inject.Inject

class GroupDataSource @Inject constructor(
    private val api: GroupApi,
    private val dao: GroupDAO,
    private val expenseTypeDao: ExpenseTypeDAO,
) : GroupDataSourceContract.Remote, GroupDataSourceContract.Local {

    override suspend fun createGroup(
        name: String,
        description: String,
        members: List<Member>,
        currency: String,
    ): Resource<Group> {
        try {
            val response = api.createGroup(
                CreateGroupDTO(
                    groupName = name,
                    groupDescription = description,
                    membersList = members.toDtoList(),
                    currency = currency
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

    override suspend fun createGroupExpense(newExpense: Expense): Resource<List<Balance>> {
        try {
            val response = api.createGroupExpense(newExpense.toDto())
            dao.insertGroupExpense(newExpense.toEntity())
            return Resource.Success(response.toDomain())
        } catch (e: HttpException) {
            Resource.Error(
                exception = Exception("Error"),
                errorMessage = "Error trying to create new expense: ${e.message}"
            )
        }
        return Resource.Error(
            exception = Exception("Error"),
            errorMessage = "Error trying to create new expense"
        )
    }

    override suspend fun insertUserGroups(groups: List<Group>) {
        dao.insertUserGroups(
            groups.map {
                it.toEntity()
            }
        )
    }

    override suspend fun deleteAllUserGroups() {
        dao.deleteAllUserGroups()
    }

    override suspend fun getUserGroups(refresh: Boolean): Resource<List<Group>> {
        return if (refresh) {
            fetchGroupsFromApi()
        } else {
            val localGroups = fetchGroupsFromLocal()
            if (localGroups is Resource.Success && localGroups.data.isNotEmpty()) {
                localGroups
            } else {
                fetchGroupsFromApi()
            }
        }
    }

    private suspend fun fetchGroupsFromApi(): Resource<List<Group>> {
        return try {
            val response = api.getUserGroups()
            val groups = response.map { it.toDomainModel() }
            dao.insertUserGroups(groups.map { it.toEntity() })
            Resource.Success(groups)
        } catch (e: HttpException) {
            Resource.Error(
                exception = Exception("Error"),
                errorMessage = "Error trying to fetch groups from API: ${e.message}"
            )
        }
    }

    private suspend fun fetchGroupsFromLocal(): Resource<List<Group>> {
        return try {
            val groups = dao.getAllUserGroups().map { it.toDomainModel() }
            if (groups.isEmpty()) {
                Resource.Error(
                    exception = Exception("Error"),
                    errorMessage = "No groups found in local database"
                )
            } else {
                Resource.Success(groups)
            }
        } catch (e: Exception) {
            Resource.Error(
                exception = Exception("Error"),
                errorMessage = "Error trying to fetch groups from local database: ${e.message}"
            )
        }
    }

    override suspend fun getUserGroupById(groupId: Int): Resource<Group> {
        return try {
            val response = dao.getUserGroupById(groupId)
            return Resource.Success(response.toDomainModel())
        } catch (e: HttpException) {
            Resource.Error(
                exception = Exception("Error"),
                errorMessage = "Error trying to get user group by id: ${e.message}"
            )
        }
    }

    override suspend fun deleteUserGroupById(groupId: Int) {
        dao.deleteUserGroupById(groupId)
    }

    override suspend fun updateUserGroup(group: Group) {
        dao.updateUserGroup(group.toEntity())
    }

    override suspend fun insertExpenseTypes(expenseTypes: List<ExpenseType>) {
        expenseTypeDao.insertExpenseTypes(expenseTypes.map { it.toEntity() })
    }

    private suspend fun fetchExpenseTypesFromApi(groupId: Int): Resource<List<ExpenseType>> {
        return try {
            val response = api.getGroupExpenseTypes(groupId)
            val expenseTypes = response.map { it.toDomainModel() }
            expenseTypeDao.insertExpenseTypes(expenseTypes.map { it.toEntity() })
            Resource.Success(expenseTypes)
        } catch (e: HttpException) {
            Resource.Error(
                exception = Exception("Error"),
                errorMessage = "Error trying to fetch expense types from API: ${e.message}"
            )
        }
    }

    private suspend fun fetchExpenseTypesFromLocal(): Resource<List<ExpenseType>> {
        return try {
            val expenseTypes = expenseTypeDao.getAllExpenseTypes()
            if (expenseTypes.isEmpty()) {
                Resource.Error(
                    exception = Exception("Error"),
                    errorMessage = "No expense types found in local database"
                )
            } else {
                Resource.Success(expenseTypes.map { it.toDomainModel() })
            }
        } catch (e: Exception) {
            Resource.Error(
                exception = Exception("Error"),
                errorMessage = "Error trying to fetch expense types from local database: ${e.message}"
            )
        }
    }

    override suspend fun getGroupExpenseTypes(
        groupId: Int,
        update: Boolean,
    ): Resource<List<ExpenseType>> {
        if (update) {
            fetchExpenseTypesFromApi(groupId)
        }
        val localExpenseTypes = fetchExpenseTypesFromLocal()
        return if (localExpenseTypes is Resource.Success && localExpenseTypes.data.isNotEmpty()) {
            localExpenseTypes
        } else {
            fetchExpenseTypesFromApi(groupId)
        }
    }

    override suspend fun deleteExpenseTypes() {
        expenseTypeDao.deleteAllExpenseTypes()
    }
}