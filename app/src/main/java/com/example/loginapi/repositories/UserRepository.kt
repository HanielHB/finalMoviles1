package com.example.loginapi.repositories

import android.content.Context
import com.example.loginapi.api.APIProyecto
import com.example.loginapi.models.Restaurant
import com.example.loginapi.models.dto.CategoryResponseDTO
import com.example.loginapi.models.dto.CreateCategoryRequestDTO
import com.example.loginapi.models.dto.CreateRestaurantRequestDTO
import com.example.loginapi.models.dto.LoginRequestDTO
import com.example.loginapi.models.dto.LoginResponseDTO
import com.example.loginapi.models.dto.RegisterRequestDTO
import com.example.loginapi.models.dto.RegisterResponseDTO
import com.example.loginapi.models.dto.ReservationDTO
import com.example.loginapi.models.dto.ReservationResponseDTO
import com.example.loginapi.models.dto.RestaurantDTO
import com.example.loginapi.models.dto.RestaurantResponseDTO
import com.example.loginapi.models.dto.UpdateRestaurantRequestDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {
    private var authToken: String? = null

    fun doLogin(
        email: String,
        password: String,
        success: (LoginResponseDTO?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        service.login(LoginRequestDTO(email, password))
            .enqueue(object : Callback<LoginResponseDTO> {
                override fun onResponse(
                    call: Call<LoginResponseDTO>,
                    response: Response<LoginResponseDTO>
                ) {
                    if (response.code() == 401) {
                        success(null)
                        return
                    }
                    val loginResponse = response.body()
                    authToken = loginResponse?.access_token // Almacenar el token de autorización
                    success(loginResponse)
                }

                override fun onFailure(call: Call<LoginResponseDTO>, t: Throwable) {
                    failure(t)
                }
            })
    }
    fun createRestaurant(
        name: String,
        address: String,
        city: String,
        description: String,
        userId: Int,
        logo: String,
        success: (RestaurantResponseDTO?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        val token = authToken ?: run {
            failure(Throwable("Error: No authorization token found"))
            return
        }

        val request = CreateRestaurantRequestDTO(name, address, city, description, userId, logo)
        service.createRestaurant("Bearer $token", request)
            .enqueue(object : Callback<RestaurantResponseDTO> {
                override fun onResponse(
                    call: Call<RestaurantResponseDTO>,
                    response: Response<RestaurantResponseDTO>
                ) {
                    if (response.isSuccessful) {
                        val restaurantResponse = response.body()
                        success(restaurantResponse)
                    } else {
                        failure(Throwable("Error: ${response.code()} ${response.message()}"))
                    }
                }

                override fun onFailure(call: Call<RestaurantResponseDTO>, t: Throwable) {
                    failure(t)
                }
            })
    }

    fun register(
        email: String,
        password: String,
        name: String,
        phone: String,
        success: (RegisterResponseDTO?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        service.register(RegisterRequestDTO(email, password, name, phone))
            .enqueue(object : Callback<RegisterResponseDTO> {
                override fun onResponse(
                    call: Call<RegisterResponseDTO>,
                    response: Response<RegisterResponseDTO>
                ) {
                    if (response.isSuccessful) {
                        val registerResponse = response.body()
                        success(registerResponse)
                    } else {
                        failure(Throwable("Error: ${response.code()} ${response.message()}"))
                    }
                }

                override fun onFailure(call: Call<RegisterResponseDTO>, t: Throwable) {
                    failure(t)
                }
            })
    }
    fun createCategory(
        name: String,
        restaurantId: Int,
        success: (CategoryResponseDTO?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        val token = authToken ?: run {
            failure(Throwable("Error: No authorization token found"))
            return
        }

        service.createCategory("Bearer $token", CreateCategoryRequestDTO(name, restaurantId))
            .enqueue(object : Callback<CategoryResponseDTO> {
                override fun onResponse(
                    call: Call<CategoryResponseDTO>,
                    response: Response<CategoryResponseDTO>
                ) {
                    if (response.isSuccessful) {
                        val categoryResponse = response.body()
                        success(categoryResponse)
                    } else {
                        failure(Throwable("Error: ${response.code()} ${response.message()}"))
                    }
                }

                override fun onFailure(call: Call<CategoryResponseDTO>, t: Throwable) {
                    failure(t)
                }
            })
    }

    fun getUserRestaurants(
        context: Context,
        userId: Int,
        success: (List<RestaurantDTO>) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        val token = PreferencesRepository.getToken(context) ?: run {
            failure(Throwable("Error: No authorization token found"))
            return
        }

        service.getUserRestaurants("Bearer $token", userId)
            .enqueue(object : Callback<List<RestaurantDTO>> {
                override fun onResponse(
                    call: Call<List<RestaurantDTO>>,
                    response: Response<List<RestaurantDTO>>
                ) {
                    if (response.isSuccessful) {
                        val restaurants = response.body()
                        if (restaurants != null) {
                            success(restaurants)
                        } else {
                            failure(Throwable("Error: Response body is null"))
                        }
                    } else {
                        failure(Throwable("Error: ${response.code()} ${response.message()}"))
                    }
                }

                override fun onFailure(call: Call<List<RestaurantDTO>>, t: Throwable) {
                    failure(t)
                }
            })
    }

    fun deleteRestaurant(
        context: Context,
        restaurantId: Int,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        val token = PreferencesRepository.getToken(context) ?: run {
            failure(Throwable("Error: No authorization token found"))
            return
        }

        service.deleteRestaurant("Bearer $token", restaurantId)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        success()
                    } else {
                        failure(Throwable("Error: ${response.code()} ${response.message()}"))
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    failure(t)
                }
            })
    }
    fun updateRestaurant(
        context: Context,
        restaurantId: Int,
        updateRestaurantRequest: UpdateRestaurantRequestDTO,
        success: (RestaurantDTO) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        val token = PreferencesRepository.getToken(context) ?: run {
            failure(Throwable("Error: No authorization token found"))
            return
        }

        service.updateRestaurant("Bearer $token", restaurantId, updateRestaurantRequest)
            .enqueue(object : Callback<RestaurantDTO> {
                override fun onResponse(call: Call<RestaurantDTO>, response: Response<RestaurantDTO>) {
                    if (response.isSuccessful) {
                        val restaurant = response.body()
                        if (restaurant != null) {
                            success(restaurant)
                        } else {
                            failure(Throwable("Error: Response body is null"))
                        }
                    } else {
                        failure(Throwable("Error: ${response.code()} ${response.message()}"))
                    }
                }

                override fun onFailure(call: Call<RestaurantDTO>, t: Throwable) {
                    failure(t)
                }
            })
    }

    fun getUserReservations(
        context: Context,
        success: (List<ReservationDTO>) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        val token = PreferencesRepository.getToken(context) ?: run {
            failure(Throwable("Error: No authorization token found"))
            return
        }

        service.getUserReservations("Bearer $token")
            .enqueue(object : Callback<List<ReservationDTO>> {
                override fun onResponse(
                    call: Call<List<ReservationDTO>>,
                    response: Response<List<ReservationDTO>>
                ) {
                    if (response.isSuccessful) {
                        val reservations = response.body()
                        if (reservations != null) {
                            success(reservations)
                        } else {
                            failure(Throwable("Error: Response body is null"))
                        }
                    } else {
                        failure(Throwable("Error: ${response.code()} ${response.message()}"))
                    }
                }

                override fun onFailure(call: Call<List<ReservationDTO>>, t: Throwable) {
                    failure(t)
                }
            })
    }

    fun cancelReservation(
        context: Context,
        reservationId: Int,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        val token = PreferencesRepository.getToken(context) ?: run {
            failure(Throwable("Error: No authorization token found"))
            return
        }

        service.cancelReservation("Bearer $token", reservationId)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        success()
                    } else {
                        failure(Throwable("Error: ${response.code()} ${response.message()}"))
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    failure(t)
                }
            })
    }

    fun getRestaurantReservations(
        context: Context,
        restaurantId: Int,
        success: (List<ReservationDTO>) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        val token = PreferencesRepository.getToken(context) ?: run {
            failure(Throwable("Error: No authorization token found"))
            return
        }

        service.getRestaurantReservations("Bearer $token", restaurantId)
            .enqueue(object : Callback<List<ReservationDTO>> {
                override fun onResponse(
                    call: Call<List<ReservationDTO>>,
                    response: Response<List<ReservationDTO>>
                ) {
                    if (response.isSuccessful) {
                        val reservations = response.body()
                        if (reservations != null) {
                            success(reservations)
                        } else {
                            failure(Throwable("Error: Response body is null"))
                        }
                    } else {
                        failure(Throwable("Error: ${response.code()} ${response.message()}"))
                    }
                }

                override fun onFailure(call: Call<List<ReservationDTO>>, t: Throwable) {
                    failure(t)
                }
            })
    }

}
