package com.madteam.split.data.repository.user

import com.madteam.split.domain.model.User

interface UserRepository {
    suspend fun getUserInfo(): User
}