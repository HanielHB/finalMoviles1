package com.example.loginapi.models.dto

import java.io.Serializable

data class UserDTO(
    val id: Int,
    val name: String,
    val email: String
): Serializable