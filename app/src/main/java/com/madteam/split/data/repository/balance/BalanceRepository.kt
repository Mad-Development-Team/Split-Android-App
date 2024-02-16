package com.madteam.split.data.repository.balance

import com.madteam.split.domain.model.Balance
import com.madteam.split.utils.network.Resource

interface BalanceRepository {

    suspend fun getGroupBalances(groupId: Int, update: Boolean): Resource<List<Balance>>
    suspend fun deleteGroupBalances(groupId: Int)
    suspend fun insertBalances(balances: List<Balance>)
    suspend fun deleteAllBalances()
}