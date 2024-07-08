package com.example.loginapi.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginapi.databinding.ActivityUserReservationsBinding
import com.example.loginapi.models.dto.ReservationDTO
import com.example.loginapi.repositories.UserRepository
import com.example.loginapi.ui.adapters.ReservationAdapter
import com.example.loginapi.ui.viewmodels.UserReservationsViewModel

class UserReservationsActivity : AppCompatActivity(), ReservationAdapter.OnReservationClickListener {
    private lateinit var binding: ActivityUserReservationsBinding
    private val model: UserReservationsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserReservationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupViewModelListeners()
    }

    override fun onResume() {
        super.onResume()
        fetchUserReservations()
    }

    private fun setupRecyclerView() {
        binding.rvUserReservations.apply {
            layoutManager = LinearLayoutManager(this@UserReservationsActivity)
            adapter = ReservationAdapter(arrayListOf(), this@UserReservationsActivity)
        }
    }

    private fun setupViewModelListeners() {
        model.reservationList.observe(this) { reservations ->
            (binding.rvUserReservations.adapter as ReservationAdapter).updateData(reservations)
        }
    }

    private fun fetchUserReservations() {
        model.fetchUserReservations(this)
    }

    override fun onReservationClick(reservation: ReservationDTO) {
        val intent = Intent(this, ReservationDetailsActivity::class.java)
        intent.putExtra("RESERVATION", reservation)
        startActivity(intent)
    }
}
