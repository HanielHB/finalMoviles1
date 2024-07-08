package com.example.loginapi.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapi.R
import com.example.loginapi.models.Category

class MenuCategoryAdapter(private var categories: List<Category>) :
    RecyclerView.Adapter<MenuCategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    fun updateData(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: Category) {
            itemView.findViewById<TextView>(R.id.tvCategoryName).text = category.name
            val dishesRecyclerView = itemView.findViewById<RecyclerView>(R.id.rvDishes)
            dishesRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            dishesRecyclerView.adapter = DishAdapter(category.plates)
        }
    }
}