package com.madteam.split.di.group

import android.content.SharedPreferences
import com.madteam.split.data.api.GroupApi
import com.madteam.split.data.config.LinksConstants
import com.madteam.split.data.database.group.ExpenseTypeDatabase
import com.madteam.split.data.database.group.GroupDatabase
import com.madteam.split.data.database.group.dao.ExpenseTypeDAO
import com.madteam.split.data.database.group.dao.GroupDAO
import com.madteam.split.data.datasource.group.GroupDataSource
import com.madteam.split.data.datasource.group.GroupDataSourceContract
import com.madteam.split.data.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GroupModule {

    @Provides
    @Singleton
    fun provideGroupApi(
        prefs: SharedPreferences,
    ): GroupApi {
        return Retrofit.Builder()
            .baseUrl(LinksConstants.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(AuthInterceptor(prefs)).build())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesGroupRemoteDataSource(
        groupRemoteDataSource: GroupDataSource,
    ): GroupDataSourceContract.Remote {
        return groupRemoteDataSource
    }

    @Provides
    @Singleton
    fun providesGroupLocalDataSource(
        groupRemoteDataSource: GroupDataSource,
    ): GroupDataSourceContract.Local {
        return groupRemoteDataSource
    }

    @Provides
    @Singleton
    fun providesGroupDao(
        @Named("GroupDatabase") database: GroupDatabase,
    ): GroupDAO {
        return database.getGroupDao()
    }

    @Provides
    @Singleton
    fun providesExpenseTypeDao(
        @Named("ExpenseTypeDatabase") database: ExpenseTypeDatabase,
    ): ExpenseTypeDAO {
        return database.getExpenseTypeDao()
    }
}