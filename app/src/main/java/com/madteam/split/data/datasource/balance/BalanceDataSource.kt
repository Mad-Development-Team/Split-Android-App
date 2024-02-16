package com.madteam.split.data.datasource.balance

import com.madteam.split.data.api.GroupApi
import com.madteam.split.data.database.balance.dao.BalanceDao
import com.madteam.split.data.database.balance.entities.BalanceEntity
import com.madteam.split.data.database.balance.entities.toModel
import com.madteam.split.data.model.response.toDomain
import com.madteam.split.data.model.response.toEntity
import com.madteam.split.domain.model.Balance
import com.madteam.split.utils.network.Resource
import retrofit2.HttpException
import javax.inject.Inject

class BalanceDataSource @Inject constructor(
    private val groupApi: GroupApi,
    private val balanceDao: BalanceDao,
) : BalanceDataSourceContract.Remote, BalanceDataSourceContract.Local {

    override suspend fun deleteAllBalances() {
        balanceDao.deleteAllBalances()
    }

    override suspend fun deleteGroupBalances(groupId: Int) {
        balanceDao.deleteGroupBalances(groupId)
    }

    override suspend fun getGroupBalancesFromLocal(groupId: Int): Resource<List<Balance>> {
        val balances = balanceDao.getGroupBalances(groupId)
        return if (balances.isNotEmpty()) {
            Resource.Success(balances.toModel())
        } else {
            Resource.Error(
                exception = Exception("Error"),
                errorMessage = "Error trying to get group balances"
            )
        }
    }

    override suspend fun insertBalances(balances: List<BalanceEntity>) {
        balanceDao.insertBalances(balances)
    }

    override suspend fun getGroupBalancesFromRemote(groupId: Int): Resource<List<Balance>> {
        try {
            val response = groupApi.getGroupBalances(groupId)
            if (response.isNotEmpty()) {
                balanceDao.deleteGroupBalances(groupId)
                balanceDao.insertBalances(response.toEntity())
                return Resource.Success(response.toDomain())
            }
            return Resource.Success(response.toDomain())
        } catch (e: HttpException) {
            return Resource.Error(
                exception = Exception("Error"),
                errorMessage = "Error trying to get group balances: ${e.message}"
            )
        }
    }
}