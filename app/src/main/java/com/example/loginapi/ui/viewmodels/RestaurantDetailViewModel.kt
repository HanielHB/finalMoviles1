package com.example.loginapi.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import com.example.loginapi.models.Restaurant
import com.example.loginapi.repositories.RestaurantRepository

class RestaurantDetailViewModel : ViewModel() {
    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> get() = _restaurant

    fun fetchRestaurantById(id: Int) {
        RestaurantRepository.getRestaurantById(
            id,
            success = { restaurant ->
                restaurant?.let {
                    _restaurant.postValue(it)
                }
            },
            failure = {
                it.printStackTrace()
            })
    }
}
