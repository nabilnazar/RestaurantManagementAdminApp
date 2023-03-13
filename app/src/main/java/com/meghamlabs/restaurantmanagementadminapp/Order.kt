package com.meghamlabs.restaurantmanagementadminapp


data class Order(val id: String = "",val tableNumber: Int = 0, val food: MutableList<Food>? = null)
