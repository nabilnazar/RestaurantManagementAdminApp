package com.meghamlabs.restaurantmanagementadminapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class FoodFragment : Fragment() {
    private lateinit var foodList: RecyclerView
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food, container, false)
        foodList = view.findViewById(R.id.food_list)
        foodList.layoutManager = GridLayoutManager(activity, 2)


        sharedPreferences = requireActivity().getSharedPreferences("food_preferences", Context.MODE_PRIVATE)


        val foodNames = listOf("Appam", "Dosa", "Puttu", "Fish", "Beef", "Chicken")
        val foodItemList = foodNames.mapIndexed { index, name -> FoodItem(index, name, getSharedPreferences().getBoolean(name, false)) }
        foodAdapter = FoodAdapter(foodItemList,this::onFoodItemClicked)
        foodList.adapter = foodAdapter

        return view
    }

    private fun onFoodItemClicked(foodItem: FoodItem) {
        sharedPreferences.edit().putBoolean(foodItem.name, foodItem.isAvailable).apply()
        val database = Firebase.database.reference.child("foods").child(foodItem.name)
        if (foodItem.isAvailable) {
            // If the checkbox is checked, add the food item to the database
            database.setValue(foodItem)
        } else {
            // If the checkbox is unchecked, remove the food item from the database
            database.removeValue()
        }
    }

    private fun getSharedPreferences(): SharedPreferences {
        return requireActivity().getSharedPreferences("food_preferences", Context.MODE_PRIVATE)
    }
}

