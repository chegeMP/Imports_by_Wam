package com.example.importsbywam.model

data class User(
    val user_id: Int? = null,
    val name: String,
    val email: String,
    val phone: String,
    val password_hash: String,
    val user_type: String,
    val created_at: String
)
