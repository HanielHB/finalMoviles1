package com.example.loginapi.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapi.models.dto.ReservationDTO
import com.example.loginapi.repositories.UserRepository

class UserReservationsViewModel : ViewModel() {
    private val _reservationList = MutableLiveData<List<ReservationDTO>>()
    val reservationList: LiveData<List<ReservationDTO>> get() = _reservationList

    fun fetchUserReservations(context: Context) {
        UserRepository.getUserReservations(
            context = context,
            success = { reservations ->
                _reservationList.postValue(reservations)
            },
            failure = {
                it.printStackTrace()
            }
        )
    }
}
