package com.example.loginapi.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginapi.R
import com.example.loginapi.databinding.ActivityRestaurantMenuBinding
import com.example.loginapi.repositories.UserRepository
import com.example.loginapi.ui.adapters.MenuCategoryAdapter
import com.example.loginapi.ui.viewmodels.RestaurantMenuViewModel

class RestaurantMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantMenuBinding
    private val model: RestaurantMenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantId = intent.getIntExtra("RESTAURANT_ID", 0)
        if (restaurantId != 0) {
            model.fetchRestaurantMenu(restaurantId)
        }

        setupRecyclerView()
        setupViewModelListeners()

    }

    private fun setupRecyclerView() {
        binding.rvMenuCategories.layoutManager = LinearLayoutManager(this)
        binding.rvMenuCategories.adapter = MenuCategoryAdapter(arrayListOf())
    }

    private fun setupViewModelListeners() {
        model.menu.observe(this) { menu ->
            menu?.let {
                (binding.rvMenuCategories.adapter as MenuCategoryAdapter).updateData(it.categories)
            }
        }
    }


}



