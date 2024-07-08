package com.example.loginapi.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapi.databinding.RestaurantItemLayoutBinding
import com.example.loginapi.models.Restaurant
import com.example.loginapi.models.Restaurants
import com.bumptech.glide.Glide

class RistorantAdapter(
    private val restaurantList: ArrayList<Restaurant>,
    private val listener: OnLibroClickListener
) : RecyclerView.Adapter<RistorantAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = RestaurantItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurantList[position]
        holder.bind(restaurant, listener)
    }

    override fun getItemCount(): Int = restaurantList.size

    fun updateData(restaurants: List<Restaurant>?) {
        restaurants?.let {
            restaurantList.clear()
            restaurantList.addAll(it)
            notifyDataSetChanged()
        }
    }

    class RestaurantViewHolder(private val binding: RestaurantItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant, listener: OnLibroClickListener) {
            binding.apply {
                lblBookName.text = restaurant.name
                lblBookGrade.text = restaurant.description
                lblBookAuthor.text = restaurant.city
                Glide.with(itemView.context).load(restaurant.logo).into(imgBookPicture)
                root.setOnClickListener { listener.onLibroClick(restaurant) }
            }
        }
    }

    interface OnLibroClickListener {
        fun onLibroClick(restaurant: Restaurant)
        fun onLibroDelete(restaurant: Restaurant)
    }
}
