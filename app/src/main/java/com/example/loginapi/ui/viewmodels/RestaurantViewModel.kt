package com.example.loginapi.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapi.models.Restaurant
import com.example.loginapi.models.Restaurants
import com.example.loginapi.models.dto.RestaurantResponseDTO
import com.example.loginapi.repositories.RestaurantRepository
import com.example.loginapi.repositories.UserRepository

class RestaurantViewModel : ViewModel() {
    private val _libroList = MutableLiveData<Restaurants>()
    val libroList: LiveData<Restaurants> get() = _libroList

    fun fetchListaLibros(
        selectedDate: String? = null,
        startTime: String? = null,
        endTime: String? = null,
        city: String? = null
    ) {
        RestaurantRepository.getLibroList(
            selectedDate = selectedDate,
            startTime = startTime,
            endTime = endTime,
            city = city,
            success = { libros ->
                libros?.let {
                    _libroList.postValue(it)
                }
            },
            failure = {
                it.printStackTrace()
            })
    }
}

