package com.example.loginapi.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapi.databinding.ActivityAddRestaurantBinding
import com.example.loginapi.repositories.UserRepository

class AddRestaurantActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRestaurantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddRestaurant.setOnClickListener {
            val name = binding.etRestaurantName.text.toString().trim()
            val address = binding.etRestaurantAddress.text.toString().trim()
            val city = binding.etRestaurantCity.text.toString().trim()
            val description = binding.etRestaurantDescription.text.toString().trim()
            val logo = binding.etRestaurantLogo.text.toString().trim()
            val userId = 1

            if (name.isNotEmpty() && address.isNotEmpty() && city.isNotEmpty() && description.isNotEmpty() && logo.isNotEmpty()) {
                UserRepository.createRestaurant(
                    name = name,
                    address = address,
                    city = city,
                    description = description,
                    userId = userId,
                    logo = logo,
                    success = { restaurant ->
                        Toast.makeText(this, "Restaurante creado: ${restaurant?.name}", Toast.LENGTH_SHORT).show()
                        finish()
                    },
                    failure = { throwable ->
                        Toast.makeText(this, "Error: ${throwable.message}", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
