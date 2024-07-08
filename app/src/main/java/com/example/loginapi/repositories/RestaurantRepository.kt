package com.example.loginapi.repositories

import android.util.Log
import com.example.loginapi.R
import com.example.loginapi.api.APIProyecto
import com.example.loginapi.models.Category
import com.example.loginapi.models.Menu
import com.example.loginapi.models.Restaurant
import com.example.loginapi.models.Restaurants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RestaurantRepository {
    fun getRestaurantById(
        id: Int,
        success: (Restaurant?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        service.getRestaurantById(id).enqueue(object : Callback<Restaurant> {
            override fun onResponse(res: Call<Restaurant>, response: Response<Restaurant>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    failure(Throwable("Error: ${response.code()} ${response.message()}"))
                }
            }

            override fun onFailure(res: Call<Restaurant>, t: Throwable) {
                failure(t)
            }
        })
    }
        fun getRestaurantMenu(
            id: Int,
            success: (Menu?) -> Unit,
            failure: (Throwable) -> Unit
        ) {
            val retrofit = RetrofitRepository.getRetrofitInstance()
            val service: APIProyecto = retrofit.create(APIProyecto::class.java)
            service.getRestaurantMenu(id).enqueue(object : Callback<List<Category>> {
                override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                    if (response.isSuccessful) {
                        val categories = response.body()
                        success(categories?.let { Menu(it) })
                    } else {
                        failure(Throwable("Error: ${response.code()} ${response.message()}"))
                    }
                }

                override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                    failure(t)
                }
            })
        }

    fun getLibroList(
        selectedDate: String? = null,
        startTime: String? = null,
        endTime: String? = null,
        city: String? = null,
        success: (Restaurants?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        service.postRestaurants(selectedDate, startTime, endTime, city).enqueue(object : Callback<Restaurants> {
            override fun onResponse(res: Call<Restaurants>, response: Response<Restaurants>) {
                if (response.isSuccessful) {
                    val postList = response.body()
                    success(postList)
                } else {
                    failure(Throwable("Error: ${response.code()} ${response.message()}"))
                }
            }

            override fun onFailure(res: Call<Restaurants>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun insertRestaurant(
        libro: Restaurant,
        success: (Restaurant) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIProyecto =
            retrofit.create(APIProyecto::class.java)
        service.insertRestaurant(libro).enqueue(object : Callback<Restaurant> {
            override fun onResponse(res: Call<Restaurant>, response: Response<Restaurant>) {
                val objLibro = response.body()
                success(objLibro!!)
            }

            override fun onFailure(res: Call<Restaurant>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getRestaurant(id: Int, success: (Restaurant?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIProyecto =
            retrofit.create(APIProyecto::class.java)
        service.getRestaurantById(id).enqueue(object : Callback<Restaurant?> {
            override fun onResponse(res: Call<Restaurant?>, response: Response<Restaurant?>) {
                success(response.body())
            }

            override fun onFailure(res: Call<Restaurant?>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deleteRestaurant(
        id: Int,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIProyecto =
            retrofit.create(APIProyecto::class.java)
        service.deleteRestaurant(id).enqueue(object : Callback<Void> {
            override fun onResponse(res: Call<Void>, response: Response<Void>) {
                success()
            }

            override fun onFailure(res: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }
}