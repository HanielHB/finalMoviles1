package com.example.loginapi.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.loginapi.databinding.ActivityRestaurantDetailBinding
import com.example.loginapi.databinding.ActivityUserRestaurantDetailBinding
import com.example.loginapi.models.dto.RestaurantDTO
import com.example.loginapi.repositories.PreferencesRepository
import com.example.loginapi.ui.viewmodels.RestaurantDetailViewModel

class UserRestaurantDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserRestaurantDetailBinding
    private val model: RestaurantDetailViewModel by viewModels()
    private var restaurantId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restaurantId = intent.getIntExtra("RESTAURANT_ID", 0)
        if (restaurantId != 0) {
            model.fetchRestaurantById(restaurantId)
        }

        setupViewModelListeners()
        setupEventListeners()
    }

    private fun setupViewModelListeners() {
        model.restaurant.observe(this) { restaurant ->
            restaurant?.let {
                binding.lblRestaurantName.text = it.name
                binding.lblRestaurantDescription.text = it.description
                binding.lblRestaurantCity.text = it.city
                Glide.with(this).load(it.logo).into(binding.imgRestaurantLogo)
            }
        }
    }

    private fun setupEventListeners() {
        binding.btnShowMenu.setOnClickListener {
            val intent = Intent(this, RestaurantMenuActivity::class.java)
            intent.putExtra("RESTAURANT_ID", restaurantId)
            startActivity(intent)
        }
        binding.btnReserve.setOnClickListener {
            if (isUserLoggedIn()) {
                navigateToReservation()
            } else {
                promptUserToLoginOrRegister()
            }
        }
    }

    private fun isUserLoggedIn(): Boolean {
        val token = PreferencesRepository.getToken(this)
        return !token.isNullOrEmpty()
    }

    private fun navigateToReservation() {
        val intent = Intent(this, RestaurantActivity::class.java)
        intent.putExtra("RESTAURANT_ID", restaurantId)
        startActivity(intent)
    }

    private fun promptUserToLoginOrRegister() {
        Toast.makeText(this, "Por favor inicie sesion o registrese", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

