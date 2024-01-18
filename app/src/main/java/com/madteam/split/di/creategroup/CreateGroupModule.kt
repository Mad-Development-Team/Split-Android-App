package com.madteam.split.di.creategroup

import com.madteam.split.data.repository.creategroup.CreateGroupRepository
import com.madteam.split.data.repository.creategroup.CreateGroupRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CreateGroupModule {

    @Provides
    @Singleton
    fun provideCreateGroupRepository(): CreateGroupRepository {
        return CreateGroupRepositoryImpl()
    }
}