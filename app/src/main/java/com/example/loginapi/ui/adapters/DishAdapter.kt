package com.example.loginapi.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapi.R
import com.example.loginapi.models.Plate


class DishAdapter(private val dishes: List<Plate>) : RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dish, parent, false)
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        holder.bind(dishes[position])
    }

    override fun getItemCount(): Int = dishes.size

    class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dish: Plate) {
            itemView.findViewById<TextView>(R.id.tvDishName).text = dish.name
            itemView.findViewById<TextView>(R.id.tvDishDescription).text = dish.description
            itemView.findViewById<TextView>(R.id.tvDishPrice).text = dish.price
        }
    }
}



