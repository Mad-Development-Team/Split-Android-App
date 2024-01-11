package com.madteam.split.data.datasource.user

import com.madteam.split.data.api.UserApi
import com.madteam.split.data.model.response.UserDTO
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val api: UserApi
) : UserDataSourceContract.Remote, UserDataSourceContract.Local {

    override suspend fun getUserInfo(): UserDTO {
        return api.getUserInfo()
    }

}