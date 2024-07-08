package com.example.loginapi.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginapi.R
import com.example.loginapi.databinding.ActivityMainBinding
import com.example.loginapi.databinding.ActivityRestaurantBinding
import com.example.loginapi.models.Restaurant
import com.example.loginapi.repositories.UserRepository
import com.example.loginapi.ui.adapters.RistorantAdapter
import com.example.loginapi.ui.viewmodels.MainViewModel
import com.example.loginapi.ui.viewmodels.RestaurantViewModel

class RestaurantActivity : AppCompatActivity(), RistorantAdapter.OnLibroClickListener {
    private lateinit var binding: ActivityRestaurantBinding
    private val model: RestaurantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupWindowInsets()
        setupEventListeners()
        setupRecyclerView()
        setupViewModelListeners()
    }

    override fun onResume() {
        super.onResume()
        fetchFilteredData()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupViewModelListeners() {
        model.libroList.observe(this) { libros ->
            (binding.rvbookList.adapter as RistorantAdapter).updateData(libros)
        }
    }

    private fun setupRecyclerView() {
        binding.rvbookList.apply {
            layoutManager = LinearLayoutManager(this@RestaurantActivity)
            adapter = RistorantAdapter(
                arrayListOf(),
                this@RestaurantActivity
            )
        }
    }

    private fun setupEventListeners() {
        binding.btnShowGenres.setOnClickListener {
            fetchFilteredData()
        }

        binding.fabAddRestaurant.setOnClickListener {
            val intent = Intent(this, AddRestaurantActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchFilteredData() {
        val selectedDate = binding.etSelectedDate.text.toString()
        val startTime = binding.etStartTime.text.toString()
        val endTime = binding.etEndTime.text.toString()
        val city = binding.etCity.text.toString()

        model.fetchListaLibros(selectedDate, startTime, endTime, city)
    }

    override fun onLibroClick(libro: Restaurant) {
        val intent = Intent(this, RestaurantDetailActivity::class.java)
        intent.putExtra("RESTAURANT_ID", libro.id)
        startActivity(intent)
    }

    override fun onLibroDelete(libro: Restaurant) {
        // Acción al eliminar un libro
    }
}
