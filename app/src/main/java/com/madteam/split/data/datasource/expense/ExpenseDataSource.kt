package com.madteam.split.data.datasource.expense

import com.madteam.split.data.api.GroupApi
import com.madteam.split.data.database.balance.dao.BalanceDao
import com.madteam.split.data.database.expense.dao.ExpenseDAO
import com.madteam.split.data.database.expense.entity.toDomainModel
import com.madteam.split.data.model.request.toDomainModel
import com.madteam.split.data.model.request.toEntity
import com.madteam.split.data.model.response.toDomain
import com.madteam.split.data.model.response.toEntity
import com.madteam.split.domain.model.Balance
import com.madteam.split.domain.model.Expense
import com.madteam.split.domain.model.toDto
import com.madteam.split.utils.network.Resource
import retrofit2.HttpException
import javax.inject.Inject

class ExpenseDataSource @Inject constructor(
    private val api: GroupApi,
    private val dao: ExpenseDAO,
    private val balanceDao: BalanceDao,
) : ExpenseDataSourceContract.Remote, ExpenseDataSourceContract.Local {

    override suspend fun createGroupExpense(newExpense: Expense): Resource<List<Balance>> {
        try {
            val response = api.createGroupExpense(newExpense.toDto())
            balanceDao.insertBalances(response.toEntity())
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

    override suspend fun getGroupExpensesFromRemote(groupId: Int): Resource<List<Expense>> {
        try {
            val response = api.getGroupExpenses(groupId)
            if (response.isNotEmpty()) {
                dao.deleteAllGroupExpenses()
                dao.insertGroupExpenses(response.toEntity())
                return Resource.Success(response.toDomainModel())
            }
            return Resource.Success(response.toDomainModel())
        } catch (e: HttpException) {
            return Resource.Error(
                exception = Exception("Error"),
                errorMessage = "Error trying to get group expenses: ${e.message}"
            )
        }
    }

    override suspend fun deleteGroupExpense(groupId: Int, expenseId: Int): Resource<List<Balance>> {
        return try {
            val response = api.deleteGroupExpense(groupId, expenseId)
            Resource.Success(response.toDomain())
        } catch (e: HttpException) {
            Resource.Error(
                exception = Exception("Error"),
                errorMessage = "Error trying to delete group expense: ${e.message}"
            )
        }
    }

    override suspend fun editGroupExpense(expense: Expense): Resource<List<Balance>> {
        return try {
            val response = api.editGroupExpense(expense.toDto())
            Resource.Success(response.toDomain())
        } catch (e: HttpException) {
            Resource.Error(
                exception = Exception("Error"),
                errorMessage = "Error trying to edit group expense: ${e.message}"
            )
        }
    }

    override suspend fun deleteAllExpenses() {
        dao.deleteAllGroupExpenses()
    }

    override suspend fun getGroupExpensesFromLocal(groupId: Int): Resource<List<Expense>> {
        val response = dao.getGroupExpenses(groupId)
        return if (response.isNotEmpty()) {
            Resource.Success(response.toDomainModel())
        } else {
            Resource.Error(
                exception = Exception("Error"),
                errorMessage = "Error trying to get group expenses"
            )
        }
    }
}