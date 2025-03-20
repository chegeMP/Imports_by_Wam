package com.example.importsbywam.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.importsbywam.model.User
import com.example.importsbywam.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getUsers()
                if (response.isSuccessful) {
                    _users.value = response.body() ?: emptyList()
                    Log.d("UserViewModel", "Fetched users: ${response.body()}")
                } else {
                    Log.e("UserViewModel", "API Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Exception: ${e.message}", e)
            }
        }
    }
}
