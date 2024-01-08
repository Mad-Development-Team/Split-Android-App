package com.madteam.split.data.model.utils

sealed class AuthResult<T>(
    val data: T? = null
) {
    class Authorized<T>(data: T? = null) : AuthResult<T>(data)
    class Unauthorized<T>(data: T? = null) : AuthResult<T>(data)
    class UnknownError<T>(data: T? = null) : AuthResult<T>(data)
}
