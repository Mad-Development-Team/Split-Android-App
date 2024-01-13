package com.madteam.split.data.repository.user

import com.madteam.split.domain.model.User
import com.madteam.split.utils.network.Resource

interface UserRepository {
    suspend fun getUserInfo(
        update: Boolean = false,
    ): Resource<User>

    suspend fun updateUserInfo(
        user: User,
    ): Resource<Boolean>

    suspend fun insertUserLocalInfo(user: User)
    suspend fun deleteAllUserLocalInfo()
    suspend fun removeProfileImage(userId: Int)
}