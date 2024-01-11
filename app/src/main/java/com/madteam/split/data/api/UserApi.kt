package com.madteam.split.data.api

import com.madteam.split.data.config.EndpointsConstants.USER_INFO
import com.madteam.split.data.model.response.UserDTO
import retrofit2.http.GET

interface UserApi {

    @GET(USER_INFO)
    suspend fun getUserInfo(): UserDTO
}