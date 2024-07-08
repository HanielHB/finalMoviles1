package com.example.loginapi.models.dto

data class UpdateRestaurantRequestDTO(
    val name: String,
    val address: String,
    val city: String,
    val description: String,
    val logo: String
)