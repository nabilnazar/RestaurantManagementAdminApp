package com.meghamlabs.restaurantmanagementadminapp

import android.os.Bundle
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Set up the buttons to navigate between fragments
        findViewById<Button>(R.id.foods_button).setOnClickListener {
            navController.navigate(R.id.foodFragment)

        }

        findViewById<Button>(R.id.orders_button).setOnClickListener {
            navController.navigate(R.id.orderFragment)

        }
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                println("Back button pressed")
                finishAffinity()
                // Code that you need to execute on back press i.e. finish()
            }
        })
    }

}