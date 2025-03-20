package com.example.importsbywam.network

import com.example.importsbywam.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("api/users")
    suspend fun getUsers(): Response<List<User>>
}
