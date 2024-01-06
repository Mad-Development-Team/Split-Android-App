package com.madteam.split.api

import com.madteam.split.data.model.request.SignInRequestDTO
import com.madteam.split.data.model.request.SignUpRequestDTO
import com.madteam.split.data.model.response.TokenResponseDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthenticationApi {

    @POST("signup")
    suspend fun signUp(
        @Body request: SignUpRequestDTO
    )

    @POST("signin")
    suspend fun signIn(
        @Body request: SignInRequestDTO
    ): TokenResponseDTO

    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    )
}