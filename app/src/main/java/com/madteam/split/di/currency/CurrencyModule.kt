package com.madteam.split.di.currency

import android.content.SharedPreferences
import com.madteam.split.data.api.CurrencyApi
import com.madteam.split.data.config.LinksConstants
import com.madteam.split.data.database.currency.CurrencyDatabase
import com.madteam.split.data.database.currency.dao.CurrencyDao
import com.madteam.split.data.datasource.currency.CurrencyDataSource
import com.madteam.split.data.datasource.currency.CurrencyDataSourceContract
import com.madteam.split.data.interceptor.AuthInterceptor
import com.madteam.split.data.repository.currency.CurrencyRepository
import com.madteam.split.data.repository.currency.CurrencyRepositoryImpl
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
object CurrencyModule {

    @Provides
    @Singleton
    fun provideCurrencyApi(
        prefs: SharedPreferences,
    ): CurrencyApi {
        return Retrofit.Builder()
            .baseUrl(LinksConstants.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(AuthInterceptor(prefs)).build())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideCurrencyLocalDataSource(
        currencyLocalDataSource: CurrencyDataSource,
    ): CurrencyDataSourceContract.Local {
        return currencyLocalDataSource
    }

    @Provides
    @Singleton
    fun provideCurrencyDao(
        @Named("CurrencyDatabase") database: CurrencyDatabase,
    ): CurrencyDao {
        return database.getCurrencyDao()
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(
        currencyDataSource: CurrencyDataSource,
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(
            dataSource = currencyDataSource
        )
    }
}