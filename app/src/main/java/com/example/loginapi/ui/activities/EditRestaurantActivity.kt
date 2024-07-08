package com.example.loginapi.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapi.databinding.ActivityEditRestaurantBinding
import com.example.loginapi.models.dto.RestaurantDTO
import com.example.loginapi.models.dto.UpdateRestaurantRequestDTO
import com.example.loginapi.repositories.UserRepository

class EditRestaurantActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditRestaurantBinding
    private var restaurantId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener datos del intent
        val restaurant = intent.getSerializableExtra("RESTAURANT") as? RestaurantDTO
        restaurant?.let {
            restaurantId = it.id
            binding.etRestaurantName.setText(it.name)
            binding.etRestaurantAddress.setText(it.address)
            binding.etRestaurantCity.setText(it.city)
            binding.etRestaurantDescription.setText(it.description)
            binding.etRestaurantLogo.setText(it.logo)
        }

        binding.btnSaveRestaurant.setOnClickListener {
            saveRestaurant()
        }
    }

    private fun saveRestaurant() {
        val updatedRestaurant = UpdateRestaurantRequestDTO(
            name = binding.etRestaurantName.text.toString(),
            address = binding.etRestaurantAddress.text.toString(),
            city = binding.etRestaurantCity.text.toString(),
            description = binding.etRestaurantDescription.text.toString(),
            logo = binding.etRestaurantLogo.text.toString()
        )

        UserRepository.updateRestaurant(this, restaurantId, updatedRestaurant,
            success = { restaurant ->
                // Manejar éxito, volver a la lista de restaurantes
                finish()
            },
            failure = {
                it.printStackTrace()
                // Mostrar mensaje de error
            }
        )
    }
}
