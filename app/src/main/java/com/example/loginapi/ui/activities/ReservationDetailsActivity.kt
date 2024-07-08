package com.example.loginapi.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapi.databinding.ActivityReservationDetailsBinding
import com.example.loginapi.models.dto.ReservationDTO
import com.example.loginapi.repositories.UserRepository

class ReservationDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationDetailsBinding
    private var reservationId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reservation = intent.getSerializableExtra("RESERVATION") as? ReservationDTO
        reservation?.let {
            reservationId = it.id
            binding.lblReservationDate.text = it.date
            binding.lblReservationTime.text = it.time
            binding.lblReservationPeople.text = it.people.toString()
            binding.lblReservationStatus.text = it.status
            binding.lblReservationRestaurantName.text = it.restaurant.name
            binding.lblReservationRestaurantAddress.text = it.restaurant.address
        }

        binding.btnCancelReservation.setOnClickListener {
            cancelReservation()
        }
    }

    private fun cancelReservation() {
        UserRepository.cancelReservation(this, reservationId,
            success = {
                Toast.makeText(this, "Reservation cancelled successfully", Toast.LENGTH_SHORT).show()
                finish() // Close the activity and go back to the previous one
            },
            failure = {
                it.printStackTrace()
                Toast.makeText(this, "Failed to cancel reservation", Toast.LENGTH_SHORT).show()
            }
        )
    }
}
