package com.madteam.split.di.storage

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.madteam.split.data.database.currency.CurrencyDatabase
import com.madteam.split.data.database.expense.ExpenseDatabase
import com.madteam.split.data.database.group.ExpenseTypeDatabase
import com.madteam.split.data.database.group.GroupDatabase
import com.madteam.split.data.database.user.UserDatabase
import com.madteam.split.data.repository.datastore.DatastoreManager
import com.madteam.split.data.repository.datastore.DatastoreManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

private const val USER_DATABASE_NAME = "user_database"

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "userSettings"
    )

    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferencesDataStore(app: Application): DataStore<Preferences> {
        return app.dataStore
    }

    @Provides
    @Singleton
    fun provideDatastoreManager(
        datastore: DataStore<Preferences>,
    ): DatastoreManager {
        return DatastoreManagerImpl(
            datastore
        )
    }

    @Provides
    @Singleton
    @Named("UserDatabase")
    fun providesUserRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context, UserDatabase::class.java, USER_DATABASE_NAME
        ).build()

    @Provides
    @Named("GroupDatabase")
    fun provideGroupDatabase(@ApplicationContext context: Context): GroupDatabase {
        return Room.databaseBuilder(
            context,
            GroupDatabase::class.java, "group_database"
        ).build()
    }

    @Provides
    @Named("CurrencyDatabase")
    fun provideCurrencyDatabase(@ApplicationContext context: Context): CurrencyDatabase {
        return Room.databaseBuilder(
            context,
            CurrencyDatabase::class.java, "currency_database"
        ).build()
    }

    @Provides
    @Singleton
    @Named("ExpenseTypeDatabase")
    fun provideExpenseTypeDatabase(@ApplicationContext context: Context): ExpenseTypeDatabase {
        return Room.databaseBuilder(
            context,
            ExpenseTypeDatabase::class.java, "expense_type_database"
        ).build()
    }

    @Provides
    @Singleton
    @Named("ExpenseDatabase")
    fun provideExpenseDatabase(@ApplicationContext context: Context): ExpenseDatabase {
        return Room.databaseBuilder(
            context,
            ExpenseDatabase::class.java, "expense_database"
        ).build()
    }
}