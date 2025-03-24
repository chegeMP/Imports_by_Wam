package com.example.importsbywam.model

data class RegisterRequest(
    val name: String,
    val phone: String,
    val email: String,
    val password_hash: String,
    val user_type: String = "user"
)
