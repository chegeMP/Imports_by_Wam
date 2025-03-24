package com.example.importsbywam.network

import com.example.importsbywam.model.User
import com.example.importsbywam.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("/api/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<User>


    // ✅ Add this for fetching users
    @GET("/api/users")
    suspend fun getUsers(): Response<List<User>>
}
