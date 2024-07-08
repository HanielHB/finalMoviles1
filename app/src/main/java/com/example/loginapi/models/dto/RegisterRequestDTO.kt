package com.example.loginapi.models.dto

data class RegisterRequestDTO(
    var email: String,
    var password: String,
    var name: String,
    var phone: String
)