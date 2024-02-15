package com.madteam.split.data.datasource.expense

import com.madteam.split.domain.model.Balance
import com.madteam.split.domain.model.Expense
import com.madteam.split.utils.network.Resource

interface ExpenseDataSourceContract {

    interface Remote {

        suspend fun createGroupExpense(
            newExpense: Expense,
        ): Resource<List<Balance>>

        suspend fun getGroupExpensesFromRemote(groupId: Int): Resource<List<Expense>>
        suspend fun deleteGroupExpense(groupId: Int, expenseId: Int): Resource<List<Balance>>
        suspend fun editGroupExpense(expense: Expense): Resource<List<Balance>>
    }

    interface Local {
        suspend fun deleteAllExpenses()
        suspend fun getGroupExpensesFromLocal(groupId: Int): Resource<List<Expense>>
    }
}