package com.madteam.split.di.expense

import com.madteam.split.data.database.expense.ExpenseDatabase
import com.madteam.split.data.database.expense.dao.ExpenseDAO
import com.madteam.split.data.datasource.expense.ExpenseDataSource
import com.madteam.split.data.datasource.expense.ExpenseDataSourceContract
import com.madteam.split.data.repository.expense.ExpenseRepository
import com.madteam.split.data.repository.expense.ExpenseRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExpenseModule {

    @Provides
    @Singleton
    fun provideExpenseDao(
        @Named("ExpenseDatabase") database: ExpenseDatabase,
    ): ExpenseDAO {
        return database.getExpenseDao()
    }

    @Provides
    @Singleton
    fun providesExpenseRemoteDataSource(
        expenseRemoteDataSource: ExpenseDataSource,
    ): ExpenseDataSourceContract.Remote {
        return expenseRemoteDataSource
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(
        expenseDataSource: ExpenseDataSource,
    ): ExpenseRepository {
        return ExpenseRepositoryImpl(
            remoteDataSource = expenseDataSource
        )
    }
}