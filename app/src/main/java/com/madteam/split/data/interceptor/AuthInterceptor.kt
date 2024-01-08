package com.madteam.split.data.interceptor

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = sharedPreferences.getString("jwt", null)

        if (token != null) {
            val modifiedRequest = request.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()

            return chain.proceed(modifiedRequest)
        }

        return chain.proceed(request)
    }
}