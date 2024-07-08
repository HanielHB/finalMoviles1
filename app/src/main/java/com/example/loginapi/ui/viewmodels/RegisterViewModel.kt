package com.example.loginapi.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapi.models.dto.RegisterResponseDTO
import com.example.loginapi.repositories.UserRepository

class RegisterViewModel : ViewModel() {
    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _registerResponse: MutableLiveData<RegisterResponseDTO?> by lazy {
        MutableLiveData<RegisterResponseDTO?>()
    }
    val registerResponse: LiveData<RegisterResponseDTO?> get() = _registerResponse

    fun register(email: String, password: String, name: String, phone: String, context: Context) {
        UserRepository.register(
            email,
            password,
            name,
            phone,
            success = {
                _registerResponse.value = it
                if (it?.error != null) {
                    _errorMessage.value = it.error
                } else {
                    _errorMessage.value = "Registro exitoso"
                }
            },
            failure = {
                it.printStackTrace()
                _errorMessage.value = "Error en la conexión"
            }
        )
    }
}
