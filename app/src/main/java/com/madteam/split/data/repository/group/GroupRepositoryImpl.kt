package com.madteam.split.data.repository.group

import com.madteam.split.data.datasource.group.GroupDataSourceContract
import com.madteam.split.domain.model.Balance
import com.madteam.split.domain.model.Currency
import com.madteam.split.domain.model.Expense
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.domain.model.Group
import com.madteam.split.domain.model.Member
import com.madteam.split.utils.network.Resource
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val groupDataSource: GroupDataSourceContract.Local,
    private val groupRemoteDataSource: GroupDataSourceContract.Remote,
) : GroupRepository {

    private var newGroup: Group
    private var userGroups: List<Group> = listOf()
    private var currentGroupId: Int? = null

    init {
        newGroup = Group(
            id = 0,
            name = "",
            description = "",
            inviteCode = "",
            image = "",
            bannerImage = "",
            createdDate = "",
            members = listOf(),
            currency = Currency()
        )
    }

    override fun getNewGroup(): Group = newGroup

    override fun setNameDescriptionAndCurrency(
        name: String,
        description: String,
        currency: Currency?,
    ) {
        newGroup = newGroup.copy(
            name = name,
            description = description,
            currency = currency ?: Currency("EUR", "€", "Euro")
        )
    }

    override fun setMembers(members: List<Member>) {
        newGroup = newGroup.copy(
            members = members
        )
    }

    override suspend fun createGroup(): Resource<Group> {
        try {
            val response = groupRemoteDataSource.createGroup(
                name = newGroup.name,
                description = newGroup.description,
                members = newGroup.members,
                currency = newGroup.currency.currency
            )
            if (response is Resource.Success) {
                newGroup = response.data
                return Resource.Success(newGroup)
            }
        } catch (e: Exception) {
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

    override suspend fun getUserGroups(update: Boolean): Resource<List<Group>> {
        if (update || userGroups.isEmpty()) {
            try {
                val response = groupDataSource.getUserGroups(true)
                if (response is Resource.Success) {
                    userGroups = response.data
                    return Resource.Success(userGroups)
                }
            } catch (e: Exception) {
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
        return Resource.Success(userGroups)
    }

    override fun setCurrentGroup(groupId: Int) {
        currentGroupId = groupId
    }

    override fun getCurrentGroup(): Int? = currentGroupId

    override suspend fun getGroupExpenseTypes(
        update: Boolean,
    ): Resource<List<ExpenseType>> {
        return groupDataSource.getGroupExpenseTypes(currentGroupId ?: 0, update)
    }

    override suspend fun createGroupExpense(newExpense: Expense): Resource<List<Balance>> {
        return try {
            val response = groupRemoteDataSource.createGroupExpense(newExpense)
            return if (response is Resource.Success) {
                Resource.Success(response.data)
            } else {
                Resource.Error(
                    exception = Exception("Error"),
                    errorMessage = "Error trying to create new expense"
                )
            }
        } catch (e: Exception) {
            Resource.Error(
                exception = Exception("Error"),
                errorMessage = "Error trying to create new expense: ${e.message}"
            )
        }
    }
}