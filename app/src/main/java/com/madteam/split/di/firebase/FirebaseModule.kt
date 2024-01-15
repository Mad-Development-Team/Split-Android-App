package com.madteam.split.di.firebase

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.madteam.split.data.repository.storage.StorageRepository
import com.madteam.split.data.repository.storage.StorageRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = Firebase.storage

    @Provides
    @Singleton
    fun provideStorageRepository(
        storage: FirebaseStorage,
    ): StorageRepository {
        return StorageRepositoryImpl(
            storage = storage
        )
    }
}