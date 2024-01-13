package com.madteam.split.data.repository.authentication

import android.content.SharedPreferences
import com.madteam.split.data.api.AuthenticationApi
import com.madteam.split.data.model.request.SignInRequestDTO
import com.madteam.split.data.model.request.SignUpRequestDTO
import com.madteam.split.data.model.utils.AuthResult
import com.madteam.split.utils.network.HttpStatusCodes
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
            when (e.code()){
                HttpStatusCodes.BAD_REQUEST -> {
                    AuthResult.UnknownError()
                }
                else -> {
                    AuthResult.Unauthorized()
                }
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
            when (e.code()){
                HttpStatusCodes.BAD_REQUEST -> {
                    AuthResult.UnknownError()
                }
                else -> {
                    AuthResult.Unauthorized()
                }
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            api.authenticate()
            AuthResult.Authorized()
        } catch (e: HttpException) {
            when (e.code()){
                HttpStatusCodes.BAD_REQUEST -> {
                    AuthResult.UnknownError()
                }
                else -> {
                    AuthResult.Unauthorized()
                }
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun signOut() {
        return try {
            prefs.edit()
                .remove("jwt")
                .apply()
        } catch (e: Exception) {
            println(e.message)
        }
    }

}