package com.madteam.split.data.repository.group

import com.madteam.split.domain.model.Currency
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.domain.model.Group
import com.madteam.split.domain.model.Member
import com.madteam.split.utils.network.Resource

interface GroupRepository {
    fun getNewGroup(): Group
    fun setNameDescriptionAndCurrency(name: String, description: String, currency: Currency?)
    fun setMembers(members: List<Member>)
    suspend fun createGroup(): Resource<Group>
    suspend fun getUserGroups(
        update: Boolean = false,
    ): Resource<List<Group>>

    fun setCurrentGroup(groupId: Int)
    fun getCurrentGroup(): Int?
    suspend fun getUserExpenseTypes(): Resource<List<ExpenseType>>
}