package com.meghamlabs.restaurantmanagementadminapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter(private val foodList: List<FoodItem>, private val onFoodItemClicked: (FoodItem) -> Unit) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodName: TextView = itemView.findViewById(R.id.food_name)
        val foodAvailability: CheckBox = itemView.findViewById(R.id.food_checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val currentItem = foodList[position]
        holder.foodName.text = currentItem.name
        holder.foodAvailability.isChecked = currentItem.isAvailable
        holder.foodAvailability.setOnCheckedChangeListener { _, isChecked ->
            currentItem.isAvailable = isChecked
            onFoodItemClicked(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

}

