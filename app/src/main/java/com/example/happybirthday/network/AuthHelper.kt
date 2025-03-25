package com.example.importsbywam.network

import com.example.importsbywam.model.LoginRequest
import com.example.importsbywam.model.User
import retrofit2.Response

object AuthHelper {
    suspend fun loginUser(email: String, password: String): Response<User> {
        val loginRequest = LoginRequest(email, password)
        return RetrofitClient.instance.loginUser(loginRequest)
    }
}
