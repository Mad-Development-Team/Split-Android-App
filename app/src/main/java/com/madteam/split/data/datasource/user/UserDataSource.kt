package com.madteam.split.data.datasource.user

import com.madteam.split.data.api.UserApi
import com.madteam.split.data.database.user.dao.UserDAO
import com.madteam.split.data.database.user.entities.UserEntity
import com.madteam.split.data.model.response.UserDTO
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val api: UserApi,
    private val dao: UserDAO
) : UserDataSourceContract.Remote, UserDataSourceContract.Local {

    override suspend fun getUserInfo(): UserDTO {
        return api.getUserInfo()
    }

    override suspend fun updateUserInfo(userEntity: UserEntity) {
        dao.updateUserInfo(userEntity)
    }

    override suspend fun insertUserInfo(userEntity: UserEntity) {
        dao.insertUserInfo(userEntity)
    }

    override suspend fun deleteAllUserInfo() {
        dao.deleteAllUserInfo()
    }

    override suspend fun getUserInfoEntity(): UserEntity {
        return dao.getUserInfo()
    }

}