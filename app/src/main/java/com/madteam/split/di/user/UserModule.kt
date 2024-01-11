package com.madteam.split.di.user

import android.content.SharedPreferences
import com.madteam.split.data.api.UserApi
import com.madteam.split.data.config.LinksConstants.BASE_API_URL
import com.madteam.split.data.database.user.UserDatabase
import com.madteam.split.data.database.user.dao.UserDAO
import com.madteam.split.data.datasource.user.UserDataSource
import com.madteam.split.data.datasource.user.UserDataSourceContract
import com.madteam.split.data.interceptor.AuthInterceptor
import com.madteam.split.data.repository.user.UserRepository
import com.madteam.split.data.repository.user.UserRepositoryImpl
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
object UserModule {

    @Provides
    @Singleton
    fun provideUserApi(
        prefs: SharedPreferences
    ): UserApi {
        return Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(AuthInterceptor(prefs)).build())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesUserRemoteDataSource(
        userRemoteDataSource: UserDataSource
    ): UserDataSourceContract.Remote {
        return userRemoteDataSource
    }

    @Provides
    @Singleton
    fun providesUserLocalDataSource(
        userRemoteDataSource: UserDataSource
    ): UserDataSourceContract.Local {
        return userRemoteDataSource
    }

    @Provides
    @Singleton
    fun providesUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository {
        return userRepository
    }

    @Provides
    @Singleton
    fun providesUserDao(
        @Named("UserDatabase") database: UserDatabase
    ): UserDAO {
        return database.getUserDao()
    }

}