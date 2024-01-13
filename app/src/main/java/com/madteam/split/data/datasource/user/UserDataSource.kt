package com.madteam.split.data.datasource.user

import com.madteam.split.data.api.UserApi
import com.madteam.split.data.database.user.dao.UserDAO
import com.madteam.split.data.database.user.entities.UserEntity
import com.madteam.split.data.mapper.user.toEntity
import com.madteam.split.data.model.request.UpdateUserInfoRequestDTO
import com.madteam.split.data.model.response.UserDTO
import com.madteam.split.domain.model.User
import com.madteam.split.utils.network.Resource
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val api: UserApi,
    private val dao: UserDAO,
) : UserDataSourceContract.Remote, UserDataSourceContract.Local, UserDataSourceContract {

    override suspend fun getUserInfo(): UserDTO {
        return api.getUserInfo()
    }

    override suspend fun updateUserInfo(user: User): Resource<Boolean> {
        try {
            api.updateUserInfo(
              UpdateUserInfoRequestDTO(
                name = user.name,
                profileImage = user.profileImage
              )
            )
        } catch (e: Exception) {
            return Resource.Error(
              exception = Exception("Error"),
              errorMessage = "Error trying to save user info on remote: ${e.message}"
            )
        }
        try {
            dao.updateUserInfo(user.toEntity())
        } catch (e: Exception) {
            return Resource.Error(
              exception = Exception("Error"),
              errorMessage = "Error trying to save user info on local"
            )
        }
        return Resource.Success(true)
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

    override suspend fun removeProfileImage(userId: Int) {
        dao.clearProfileImage(userId)
        api.removeProfileImage()
    }
}