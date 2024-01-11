package com.madteam.split.data.repository.user

import com.madteam.split.data.datasource.user.UserDataSourceContract
import com.madteam.split.data.mapper.user.toUser
import com.madteam.split.domain.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserDataSourceContract.Remote,
    private val userLocalDataSource: UserDataSourceContract.Local
) : UserRepository {
    override suspend fun getUserInfo(): User {
        return try {
            userRemoteDataSource.getUserInfo().toUser()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}