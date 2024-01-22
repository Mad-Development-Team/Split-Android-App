package com.madteam.split.di.group

import android.content.SharedPreferences
import com.madteam.split.data.api.GroupApi
import com.madteam.split.data.config.LinksConstants
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
}