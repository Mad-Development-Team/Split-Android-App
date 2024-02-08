package com.madteam.split.data.repository.expense

import com.madteam.split.data.datasource.expense.ExpenseDataSourceContract
import com.madteam.split.domain.model.Balance
import com.madteam.split.domain.model.Expense
import com.madteam.split.utils.network.Resource
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val remoteDataSource: ExpenseDataSourceContract.Remote,
    private val localDataSource: ExpenseDataSourceContract.Local,
) : ExpenseRepository {

    override suspend fun createGroupExpense(newExpense: Expense): Resource<List<Balance>> {
        return try {
            val response = remoteDataSource.createGroupExpense(newExpense)
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

    override suspend fun getGroupExpenses(groupId: Int): Resource<List<Expense>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllExpenses() {
        localDataSource.deleteAllExpenses()
    }
}