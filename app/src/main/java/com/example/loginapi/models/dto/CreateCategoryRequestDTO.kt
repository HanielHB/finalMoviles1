package com.example.loginapi.models.dto

data class CreateCategoryRequestDTO(
    val name: String,
    val restaurant_id: Int
)