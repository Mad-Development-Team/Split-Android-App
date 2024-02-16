package com.madteam.split.di.balance

import com.madteam.split.data.database.balance.BalanceDatabase
import com.madteam.split.data.database.balance.dao.BalanceDao
import com.madteam.split.data.datasource.balance.BalanceDataSource
import com.madteam.split.data.datasource.balance.BalanceDataSourceContract
import com.madteam.split.data.repository.balance.BalanceRepository
import com.madteam.split.data.repository.balance.BalanceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BalanceModule {

    @Provides
    @Singleton
    fun provideBalanceDao(
        @Named("BalanceDatabase") database: BalanceDatabase,
    ): BalanceDao {
        return database.getBalanceDao()
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        balanceRemoteDataSource: BalanceDataSource,
    ): BalanceDataSourceContract.Remote {
        return balanceRemoteDataSource
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        balanceLocalDataSource: BalanceDataSource,
    ): BalanceDataSourceContract.Local {
        return balanceLocalDataSource
    }

    @Provides
    @Singleton
    fun provideBalanceRepository(
        balanceDataSource: BalanceDataSource,
    ): BalanceRepository {
        return BalanceRepositoryImpl(
            remoteDataSource = balanceDataSource,
            localDataSource = balanceDataSource
        )
    }
}