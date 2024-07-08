package com.example.loginapi.models

data class Plate(
    val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val menu_category_id: Int
)