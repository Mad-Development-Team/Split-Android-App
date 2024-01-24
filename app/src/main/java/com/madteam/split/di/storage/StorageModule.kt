package com.madteam.split.di.storage

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
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
}