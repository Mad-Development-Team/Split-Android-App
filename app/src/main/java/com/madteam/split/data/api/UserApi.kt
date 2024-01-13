package com.madteam.split.data.api

import com.madteam.split.data.config.EndpointsConstants.REMOVE_PROFILE_IMAGE
import com.madteam.split.data.config.EndpointsConstants.UPDATE_USER_INFO
import com.madteam.split.data.config.EndpointsConstants.USER_INFO
import com.madteam.split.data.model.request.UpdateUserInfoRequestDTO
import com.madteam.split.data.model.response.UserDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @GET(USER_INFO)
    suspend fun getUserInfo(): UserDTO

    @POST(UPDATE_USER_INFO)
    suspend fun updateUserInfo(
        @Body request: UpdateUserInfoRequestDTO,
    )

    @GET(REMOVE_PROFILE_IMAGE)
    suspend fun removeProfileImage()
}