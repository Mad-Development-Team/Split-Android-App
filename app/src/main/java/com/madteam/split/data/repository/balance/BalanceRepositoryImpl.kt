package com.madteam.split.data.repository.balance

import com.madteam.split.data.datasource.balance.BalanceDataSourceContract
import com.madteam.split.domain.model.Balance
import com.madteam.split.domain.model.toEntity
import com.madteam.split.utils.network.Resource
import javax.inject.Inject

class BalanceRepositoryImpl @Inject constructor(
    private val remoteDataSource: BalanceDataSourceContract.Remote,
    private val localDataSource: BalanceDataSourceContract.Local,
) : BalanceRepository {

    override suspend fun getGroupBalances(
        groupId: Int,
        update: Boolean,
    ): Resource<List<Balance>> {
        return if (update) {
            try {
                val response = remoteDataSource.getGroupBalancesFromRemote(groupId)
                if (response is Resource.Success) {
                    localDataSource.deleteGroupBalances(groupId)
                    localDataSource.insertBalances(response.data.toEntity())
                }
                response
            } catch (e: Exception) {
                Resource.Error(
                    exception = Exception("Error"),
                    errorMessage = "Error trying to get group balances: ${e.message}"
                )
            }
        } else {
            return localDataSource.getGroupBalancesFromLocal(groupId)
        }
    }

    override suspend fun deleteGroupBalances(groupId: Int) {
        localDataSource.deleteGroupBalances(groupId)
    }

    override suspend fun insertBalances(balances: List<Balance>) {
        localDataSource.insertBalances(balances.toEntity())
    }

    override suspend fun deleteAllBalances() {
        localDataSource.deleteAllBalances()
    }
}