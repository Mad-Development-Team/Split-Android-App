package com.madteam.split.data.datasource.user

import com.madteam.split.data.model.response.UserDTO

interface UserDataSourceContract {

    fun interface Remote {
        suspend fun getUserInfo(): UserDTO
    }

    interface Local {
    }
}