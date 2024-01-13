package com.madteam.split.data.repository.storage

import com.madteam.split.utils.network.Resource

interface StorageRepository {

    suspend fun getAvatarImagesUrls(): Resource<List<String>>
}