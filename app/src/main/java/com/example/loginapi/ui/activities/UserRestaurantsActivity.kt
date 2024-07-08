package com.example.loginapi.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginapi.databinding.ActivityUserRestaurantsBinding
import com.example.loginapi.models.dto.RestaurantDTO
import com.example.loginapi.repositories.UserRepository
import com.example.loginapi.ui.adapters.RistaurantAdapter
import com.example.loginapi.ui.viewmodels.UserRestaurantsViewModel

class UserRestaurantsActivity : AppCompatActivity(), RistaurantAdapter.OnRestaurantClickListener {
    private lateinit var binding: ActivityUserRestaurantsBinding
    private val model: UserRestaurantsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserRestaurantsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupViewModelListeners()
    }

    override fun onResume() {
        super.onResume()
        fetchUserRestaurants()
    }

    private fun setupRecyclerView() {
        binding.rvUserRestaurants.apply {
            layoutManager = LinearLayoutManager(this@UserRestaurantsActivity)
            adapter = RistaurantAdapter(arrayListOf(), this@UserRestaurantsActivity)
        }
    }

    private fun setupViewModelListeners() {
        model.restaurantList.observe(this) { restaurants ->
            (binding.rvUserRestaurants.adapter as RistaurantAdapter).updateData(restaurants)
        }
    }

    private fun fetchUserRestaurants() {
        model.fetchUserRestaurants(this)
    }

    override fun onRestaurantClick(restaurant: RestaurantDTO) {
        val intent = Intent(this, UserRestaurantDetailActivity::class.java)
        intent.putExtra("RESTAURANT_ID", restaurant.id)
        startActivity(intent)
    }

    override fun onRestaurantDelete(restaurant: RestaurantDTO) {
        UserRepository.deleteRestaurant(this, restaurant.id,
            success = {
                fetchUserRestaurants()
            },
            failure = {
                it.printStackTrace()
            }
        )
    }

    override fun onRestaurantEdit(restaurant: RestaurantDTO) {
        val intent = Intent(this, EditRestaurantActivity::class.java)
        intent.putExtra("RESTAURANT", restaurant)
        startActivity(intent)
    }
}



