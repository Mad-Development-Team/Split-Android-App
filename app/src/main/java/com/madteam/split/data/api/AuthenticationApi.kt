package com.madteam.split.data.api

import com.madteam.split.data.config.EndpointsConstants.AUTHENTICATE
import com.madteam.split.data.config.EndpointsConstants.SIGN_IN
import com.madteam.split.data.config.EndpointsConstants.SIGN_UP
import com.madteam.split.data.model.request.SignInRequestDTO
import com.madteam.split.data.model.request.SignUpRequestDTO
import com.madteam.split.data.model.response.TokenResponseDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthenticationApi {

    @POST(SIGN_UP)
    suspend fun signUp(
        @Body request: SignUpRequestDTO
    )

    @POST(SIGN_IN)
    suspend fun signIn(
        @Body request: SignInRequestDTO
    ): TokenResponseDTO

    @GET(AUTHENTICATE)
    suspend fun authenticate()
}