package com.example.loginapi.models

data class Category(
    val id: Int,
    val name: String,
    val restaurant_id: Int,
    val plates: List<Plate>
)