package com.example.loginapi.models.dto

data class RegisterResponseDTO(
    val message: String,
    val userId: Int? = null,
    val error: String? = null
)