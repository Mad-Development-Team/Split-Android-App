package com.madteam.split.data.repository

import com.madteam.split.data.model.AuthResult

interface AuthenticationRepository {
    suspend fun signUp(
        email: String,
        password: String,
        name: String
    ): AuthResult<Unit>
    suspend fun signIn(
        email: String,
        password: String
    ): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
    suspend fun signOut()
}