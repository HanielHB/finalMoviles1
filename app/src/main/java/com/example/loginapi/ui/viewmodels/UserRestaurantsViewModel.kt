package com.example.loginapi.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapi.models.dto.RestaurantDTO
import com.example.loginapi.repositories.UserRepository

class UserRestaurantsViewModel : ViewModel() {
    private val _restaurantList = MutableLiveData<List<RestaurantDTO>>()
    val restaurantList: LiveData<List<RestaurantDTO>> get() = _restaurantList

    fun fetchUserRestaurants(context: Context) {
        UserRepository.getUserRestaurants(
            context = context,
            userId = 1,
            success = { restaurants ->
                _restaurantList.postValue(restaurants)
            },
            failure = {
                it.printStackTrace()
            }
        )
    }
}

