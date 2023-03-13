package com.meghamlabs.restaurantmanagementadminapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    val name: String = "",
    var quantity: Int = 0,

    ): Parcelable