package com.madteam.split.data.repository.storage

import com.google.firebase.storage.FirebaseStorage
import com.madteam.split.data.config.StoragePaths.AVATAR_IMAGES_PATH
import com.madteam.split.utils.network.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
) : StorageRepository {

    override suspend fun getAvatarImagesUrls(): Resource<List<String>> {
        return try {
            val reference = storage.reference.child(AVATAR_IMAGES_PATH)
            val result = reference.listAll().await()
            val urls = mutableListOf<String>()

            for (item in result.items) {
                val url = item.downloadUrl.await().toString()
                urls.add(url)
            }

            Resource.Success(urls)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}