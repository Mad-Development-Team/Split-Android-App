package com.madteam.split.di.creategroup

import com.madteam.split.data.datasource.group.GroupDataSource
import com.madteam.split.data.repository.group.GroupRepository
import com.madteam.split.data.repository.group.GroupRepositoryImpl
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
    fun provideCreateGroupRepository(groupDataSource: GroupDataSource): GroupRepository {
        return GroupRepositoryImpl(
            groupDataSource = groupDataSource,
            groupRemoteDataSource = groupDataSource
        )
    }
}