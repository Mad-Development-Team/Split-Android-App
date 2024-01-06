package com.madteam.split.data.repository

import android.content.SharedPreferences
import com.madteam.split.api.AuthenticationApi
import com.madteam.split.data.model.AuthResult
import com.madteam.split.data.model.request.SignInRequestDTO
import com.madteam.split.data.model.request.SignUpRequestDTO
import retrofit2.HttpException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val api: AuthenticationApi,
    private val prefs: SharedPreferences
) : AuthenticationRepository {

    override suspend fun signUp(email: String, password: String, name: String): AuthResult<Unit> {
        return try {
            api.signUp(
                request = SignUpRequestDTO(
                    email = email,
                    password = password,
                    name = name
                )
            )
            signIn(email, password)

        } catch (e: HttpException) {
            if (e.code() == 401) {
                //TODO: Implement code numbers on a different file with constants
                //TODO: Handle more server errors (Server down, existing email, etc etc)
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun signIn(email: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.signIn(
                request = SignInRequestDTO(
                    email = email,
                    password = password
                )
            )
            prefs.edit()
                .putString("jwt", response.token)
                .apply()
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                //TODO: Implement code numbers on a different file with constants
                //TODO: Handle more server errors (Server down, existing email, etc etc)
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = prefs.getString("jwt", null) ?: return AuthResult.Unauthorized()
            api.authenticate(
                token = "Bearer $token"
            )
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                //TODO: Implement code numbers on a different file with constants
                //TODO: Handle more server errors (Server down, existing email, etc etc)
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

}