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
        update: Boolean = false,
    ): Resource<List<Expense>>

    suspend fun deleteGroupExpense(
        groupId: Int,
        expenseId: Int,
    ): Resource<List<Balance>>

    suspend fun editGroupExpense(
        expense: Expense,
    ): Resource<List<Balance>>

    suspend fun deleteAllExpenses()
}