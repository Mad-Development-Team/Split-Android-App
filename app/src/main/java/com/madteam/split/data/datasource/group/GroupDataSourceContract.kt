package com.madteam.split.data.datasource.group

import com.madteam.split.domain.model.Balance
import com.madteam.split.domain.model.Expense
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.domain.model.Group
import com.madteam.split.domain.model.Member
import com.madteam.split.utils.network.Resource

interface GroupDataSourceContract {

    interface Remote {
        suspend fun createGroup(
            name: String,
            description: String,
            members: List<Member>,
            currency: String,
        ): Resource<Group>

        suspend fun createGroupExpense(
            newExpense: Expense,
        ): Resource<List<Balance>>
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
        suspend fun getGroupExpenseTypes(
            groupId: Int,
            update: Boolean,
        ): Resource<List<ExpenseType>>

        suspend fun insertExpenseTypes(expenseTypes: List<ExpenseType>)
        suspend fun deleteExpenseTypes()
    }
}