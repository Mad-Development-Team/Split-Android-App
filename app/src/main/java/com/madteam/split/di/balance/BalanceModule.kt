package com.madteam.split.di.balance

import com.madteam.split.data.database.balance.BalanceDatabase
import com.madteam.split.data.database.balance.dao.BalanceDao
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
}