package com.madteam.split.di.authentication

import android.content.SharedPreferences
import com.madteam.split.api.AuthenticationApi
import com.madteam.split.data.config.LinksConstants.BASE_API_URL
import com.madteam.split.data.repository.AuthenticationRepository
import com.madteam.split.data.repository.AuthenticationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi() : AuthenticationApi {
        return Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthenticationApi,
        prefs: SharedPreferences
    ) : AuthenticationRepository {
        return AuthenticationRepositoryImpl(
            api = api,
            prefs = prefs
        )
    }
}