package com.madteam.split.data.datasource.user

import com.madteam.split.data.database.user.entities.UserEntity
import com.madteam.split.data.model.response.UserDTO

interface UserDataSourceContract {

    fun interface Remote {
        suspend fun getUserInfo(): UserDTO
    }

    interface Local {
        suspend fun getUserInfoEntity(): UserEntity
        suspend fun updateUserInfo(userEntity: UserEntity)
        suspend fun insertUserInfo(userEntity: UserEntity)
        suspend fun deleteAllUserInfo()
    }
}