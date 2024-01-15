package com.madteam.split.data.repository.storage

import com.madteam.split.utils.network.Resource

interface StorageRepository {

    suspend fun getAvatarImagesUrls(): Resource<List<String>>
    suspend fun uploadProfileImage(
        uri: String,
        userId: Int,
    ): Resource<String>

    suspend fun deleteAllProfileUserImages(
        userId: Int,
    ): Resource<Boolean>
}