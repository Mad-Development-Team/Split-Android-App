package com.madteam.split.data.repository.expense

import com.madteam.split.domain.model.Balance
import com.madteam.split.domain.model.Expense
import com.madteam.split.utils.network.Resource

interface ExpenseRepository {

    suspend fun createGroupExpense(
        newExpense: Expense,
    ): Resource<List<Balance>>

    suspend fun getGroupExpenses(
        groupId: Int,
    ): Resource<List<Expense>>

    suspend fun deleteAllExpenses()
}