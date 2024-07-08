// RestaurantAdapter.kt
package com.example.loginapi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginapi.databinding.RestaurantItemLayoutBinding
import com.example.loginapi.databinding.UrestaurantItemLayoutBinding
import com.example.loginapi.models.dto.RestaurantDTO

class RistaurantAdapter(
    private val restaurantList: ArrayList<RestaurantDTO>,
    private val listener: OnRestaurantClickListener
) : RecyclerView.Adapter<RistaurantAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = UrestaurantItemLayoutBinding.inflate(
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

    fun updateData(restaurants: List<RestaurantDTO>?) {
        restaurants?.let {
            restaurantList.clear()
            restaurantList.addAll(it)
            notifyDataSetChanged()
        }
    }

    class RestaurantViewHolder(private val binding: UrestaurantItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: RestaurantDTO, listener: OnRestaurantClickListener) {
            binding.apply {
                lblBookName.text = restaurant.name
                lblBookGrade.text = restaurant.description
                lblBookAuthor.text = restaurant.city
                Glide.with(itemView.context).load(restaurant.logo).into(imgBookPicture)

                btnDeleteRestaurant.setOnClickListener {
                    listener.onRestaurantDelete(restaurant)
                }
                btnEditRestaurant.setOnClickListener {
                    listener.onRestaurantEdit(restaurant)
                }
                root.setOnClickListener {
                    listener.onRestaurantClick(restaurant)
                }
            }
        }
    }

    interface OnRestaurantClickListener {
        fun onRestaurantClick(restaurant: RestaurantDTO)
        fun onRestaurantDelete(restaurant: RestaurantDTO)
        fun onRestaurantEdit(restaurant: RestaurantDTO)
    }
}

