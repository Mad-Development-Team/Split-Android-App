package com.madteam.split.data.repository.storage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.madteam.split.data.config.StoragePaths.AVATAR_IMAGES_PATH
import com.madteam.split.data.config.StoragePaths.USER_PROFILES_IMAGES_PATH
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

    override suspend fun uploadProfileImage(uri: String, userId: Int): Resource<String> {
        return try {
            val reference = storage
                .reference
                .child(USER_PROFILES_IMAGES_PATH)
                .child("$userId/")
                .child("global_profile_image")
            val uriObject = Uri.parse(uri)

            if (uriObject != null) {
                val result = reference.putFile(uriObject).await()
                val url = result.storage.downloadUrl.await().toString()
                Resource.Success(url)
            } else {
                Resource.Error(Exception("Uri is null"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun deleteAllProfileUserImages(
        userId: Int,
    ): Resource<Boolean> {
        return try {
            val reference = storage
                .reference
                .child(USER_PROFILES_IMAGES_PATH)
                .child("$userId/")

            val result = reference.listAll().await()

            for (item in result.items) {
                item.delete().await()
            }

            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}