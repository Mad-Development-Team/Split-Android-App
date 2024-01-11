package com.madteam.split.data.repository.user

import android.util.Log
import com.madteam.split.data.datasource.user.UserDataSourceContract
import com.madteam.split.data.mapper.user.toEntity
import com.madteam.split.data.mapper.user.toUser
import com.madteam.split.domain.model.User
import com.madteam.split.utils.network.Resource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserDataSourceContract.Remote,
    private val userLocalDataSource: UserDataSourceContract.Local
) : UserRepository {

    override suspend fun getUserInfo(
        update: Boolean
    ): Resource<User> {
        if (!update) {
            try {
                return Resource.Success(
                    data = userLocalDataSource.getUserInfoEntity().toUser()
                )
            } catch (e: Exception) {
                Log.d("UserRepositoryImpl::getUserInfo", "Error: ${e.message}")
            }
        }
        try {
            val user = userRemoteDataSource.getUserInfo().toUser()
            userLocalDataSource.insertUserInfo(user.toEntity())
            return Resource.Success(user)
        } catch (e: Exception) {
            Log.d("UserRepositoryImpl::getUserInfo", "Error: ${e.message}")
        }
        return Resource.Error(
            exception = Exception("Error"),
            errorMessage = "Error trying to retrieve user info"
        )
    }

    override suspend fun insertUserLocalInfo(user: User) {
        userLocalDataSource.insertUserInfo(user.toEntity())
    }

    override suspend fun updateUserLocalInfo(user: User) {
        userLocalDataSource.updateUserInfo(user.toEntity())
    }

    override suspend fun deleteAllUserLocalInfo() {
        userLocalDataSource.deleteAllUserInfo()
    }
}