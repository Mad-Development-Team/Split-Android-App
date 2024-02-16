package com.madteam.split.data.datasource.balance

import com.madteam.split.data.database.balance.entities.BalanceEntity
import com.madteam.split.domain.model.Balance
import com.madteam.split.utils.network.Resource

interface BalanceDataSourceContract {
    interface Remote {
        suspend fun getGroupBalancesFromRemote(groupId: Int): Resource<List<Balance>>
    }

    interface Local {
        suspend fun deleteAllBalances()
        suspend fun deleteGroupBalances(groupId: Int)
        suspend fun getGroupBalancesFromLocal(groupId: Int): Resource<List<Balance>>
        suspend fun insertBalances(balances: List<BalanceEntity>)
    }
}