package com.example.loginapi.models.dto

import java.io.Serializable

data class RestaurantDTO(
    val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val description: String,
    val user_id: Int,
    val logo: String
) : Serializable