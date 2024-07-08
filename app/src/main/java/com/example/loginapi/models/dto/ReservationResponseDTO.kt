package com.example.loginapi.models.dto

data class ReservationResponseDTO(
    val id: Int,
    val user_id: Int,
    val restaurant_id: Int,
    val date: String,
    val time: String,
    val people: Int,
    val status: String,
    val created_at: String,
    val updated_at: String,
    val user: UserDTO,
    val restaurant: RestaurantDTO
)