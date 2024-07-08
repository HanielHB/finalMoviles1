package com.example.loginapi.models.dto

data class CreateRestaurantRequestDTO(
    val name: String,
    val address: String,
    val city: String,
    val description: String,
    val user_id: Int,
    val logo: String
)