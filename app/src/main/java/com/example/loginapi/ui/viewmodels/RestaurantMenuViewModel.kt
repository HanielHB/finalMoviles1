package com.example.loginapi.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapi.models.Menu
import com.example.loginapi.repositories.RestaurantRepository
import com.example.loginapi.repositories.RestaurantRepository.getRestaurantMenu

class RestaurantMenuViewModel : ViewModel() {
    private val _menu = MutableLiveData<Menu>()
    val menu: LiveData<Menu> get() = _menu

    fun fetchRestaurantMenu(id: Int) {
        RestaurantRepository.getRestaurantMenu(id,
            success = { menu ->
                menu?.let {
                    val filteredCategories = it.categories.filter { category ->
                        category.restaurant_id == id
                    }
                    _menu.postValue(Menu(filteredCategories))
                }
            },
            failure = {
                it.printStackTrace()
            }
        )
    }
}

