package com.madteam.split.data.datasource.user

import com.madteam.split.data.database.user.entities.UserEntity
import com.madteam.split.data.model.response.UserDTO
import com.madteam.split.domain.model.User
import com.madteam.split.utils.network.Resource

interface UserDataSourceContract {

    interface Remote {
        suspend fun getUserInfo(): UserDTO
    }

    interface Local {
        suspend fun getUserInfoEntity(): UserEntity
        suspend fun insertUserInfo(userEntity: UserEntity)
        suspend fun deleteAllUserInfo()
    }

    suspend fun removeProfileImage(userId: Int)
    suspend fun updateUserInfo(user: User): Resource<Boolean>
}