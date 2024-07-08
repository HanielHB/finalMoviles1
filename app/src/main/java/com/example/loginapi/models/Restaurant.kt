package com.example.loginapi.models

typealias Restaurants = ArrayList<Restaurant>

data class Restaurant(
    var name: String
) {
    var id: Int? = null
    var address: String? = null
    var city: String? = null
    var description: String? = null
    var user_id: String? = null
    var logo: String? = null
}