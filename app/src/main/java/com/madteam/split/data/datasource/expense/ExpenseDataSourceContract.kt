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
    }

    interface Local {
        suspend fun deleteAllExpenses()
        suspend fun getGroupExpensesFromLocal(groupId: Int): Resource<List<Expense>>
    }
}