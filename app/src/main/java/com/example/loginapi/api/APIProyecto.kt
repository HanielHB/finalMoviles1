package com.example.loginapi.api

import com.example.loginapi.models.Category
import com.example.loginapi.models.Menu
import com.example.loginapi.models.dto.LoginRequestDTO
import com.example.loginapi.models.dto.LoginResponseDTO
import com.example.loginapi.models.Restaurant
import com.example.loginapi.models.Restaurants
import com.example.loginapi.models.dto.CategoryResponseDTO
import com.example.loginapi.models.dto.CreateCategoryRequestDTO
import com.example.loginapi.models.dto.CreateRestaurantRequestDTO
import com.example.loginapi.models.dto.RegisterRequestDTO
import com.example.loginapi.models.dto.RegisterResponseDTO
import com.example.loginapi.models.dto.ReservationDTO
import com.example.loginapi.models.dto.ReservationResponseDTO
import com.example.loginapi.models.dto.RestaurantDTO
import com.example.loginapi.models.dto.RestaurantResponseDTO
import com.example.loginapi.models.dto.UpdateRestaurantRequestDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface APIProyecto {
    @POST("loginuser")
    fun login(@Body loginRequest: LoginRequestDTO): Call<LoginResponseDTO>

    @POST("registeruser")
    fun register(@Body registerRequest: RegisterRequestDTO): Call<RegisterResponseDTO>

    //El token se tiene que mandar como Bearer + El string que se obtiene de PreferencesRepository.getToken()
    @POST("restaurants")
    fun insertRestaurant(@Header("Authorization") token: String, @Body restaurant: Restaurant): Call<Restaurant>

    @GET("/api/reservations")
    fun getReservations(@Header("Authorization") token: String): Call<List<ReservationResponseDTO>>

    @POST("menu-categories")
    fun createCategory(
        @Header("Authorization") authToken: String,
        @Body request: CreateCategoryRequestDTO
    ): Call<CategoryResponseDTO>

    @POST("restaurants")
    fun createRestaurant(
        @Header("Authorization") authToken: String,
        @Body request: CreateRestaurantRequestDTO
    ): Call<RestaurantResponseDTO>

    @GET("restaurants/")
    fun getUserRestaurants(
        @Header("Authorization") authToken: String,
        @Query("userId") userId: Int
    ): Call<List<RestaurantDTO>>

    @DELETE("restaurants/{id}")
    fun deleteRestaurant(
        @Header("Authorization") authToken: String,
        @Path("id") restaurantId: Int
    ): Call<Void>

//    @POST("restaurants/search")
//    fun getRestaurants(): Call<Restaurants>

//    @POST("restaurants/search")
//    fun postRestaurants(): Call<Restaurants>

    @POST("restaurants/search")
    fun postRestaurants(
        @Query("selectedDate") selectedDate: String?,
        @Query("startTime") startTime: String?,
        @Query("endTime") endTime: String?,
        @Query("city") city: String?
    ): Call<Restaurants>

    @POST("restaurants/search")
    fun postRestaurantList(): Call<List<Restaurant>>

    @GET("restaurants/{id}")
    fun getRestaurantById(@Path("id") id: Int): Call<Restaurant>

//    @GET("restaurants/{id}")
//    fun getRestaurantById(
//        @Path("id") id: Int
//    ): Call<Restaurant?>

    @POST("restaurants")
    fun insertRestaurant(
        @Body restaurant: Restaurant
    ): Call<Restaurant>

//    @GET("restaurants/{id}/menu")
//    fun getRestaurantMenu(@Path("id") id: Int): Call<Menu>

    @GET("restaurants/{id}/menu")
    fun getRestaurantMenu(@Path("id") id: Int): Call<List<Category>>

//    @PUT("restaurants/{id}")
//    fun updateRestaurant(
//        @Body restaurant: Restaurant,
//        @Path("id") id: Int
//    ): Call<Restaurant>

    @PUT("restaurants/{id}")
    fun updateRestaurant(
        @Header("Authorization") authToken: String,
        @Path("id") restaurantId: Int,
        @Body updateRestaurantRequest: UpdateRestaurantRequestDTO
    ): Call<RestaurantDTO>

    @DELETE("restaurants/{id}")
    fun deleteRestaurant(
        @Path("id") id: Int
    ): Call<Void>

    @GET("reservations")
    fun getUserReservations(
        @Header("Authorization") authToken: String
    ): Call<List<ReservationDTO>>

    @POST("reservations/{id}/cancel")
    fun cancelReservation(
        @Header("Authorization") authToken: String,
        @Path("id") reservationId: Int
    ): Call<Void>

    @GET("restaurants/{id}/reservations")
    fun getRestaurantReservations(
        @Header("Authorization") authToken: String,
        @Path("id") restaurantId: Int
    ): Call<List<ReservationDTO>>
}