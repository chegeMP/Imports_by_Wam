package com.example.importsbywam.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.importsbywam.model.RegisterRequest
import com.example.importsbywam.model.User
import com.example.importsbywam.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess: StateFlow<Boolean> = _registerSuccess

    fun fetchUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.getUsers()
                if (response.isSuccessful) {
                    _users.value = response.body() ?: emptyList()
                    Log.d("UserViewModel", "Fetched users: ${response.body()}")
                } else {
                    _error.value = "Error: ${response.code()} - ${response.message()}"
                    Log.e("UserViewModel", _error.value ?: "")
                }
            } catch (e: Exception) {
                _error.value = "Exception: ${e.message}"
                Log.e("UserViewModel", _error.value ?: "", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun registerUser(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            _registerSuccess.value = false
            try {
                val response = RetrofitClient.instance.registerUser(registerRequest)
                if (response.isSuccessful) {
                    Log.d("UserViewModel", "User registered: ${response.body()}")
                    _registerSuccess.value = true
                    fetchUsers()
                } else {
                    _error.value = "Registration Error: ${response.code()} - ${response.message()}"
                    Log.e("UserViewModel", _error.value ?: "")
                }
            } catch (e: Exception) {
                _error.value = "Registration Exception: ${e.message}"
                Log.e("UserViewModel", _error.value ?: "", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ✅ Reset success after navigation
    fun resetRegisterSuccess() {
        _registerSuccess.value = false
    }
}
