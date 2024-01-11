package com.madteam.split.data.repository.user

import com.madteam.split.domain.model.User
import com.madteam.split.utils.network.Resource

interface UserRepository {
    suspend fun getUserInfo(): Resource<User>
    suspend fun updateUserLocalInfo(user: User)
    suspend fun insertUserLocalInfo(user: User)
    suspend fun deleteAllUserLocalInfo()
}