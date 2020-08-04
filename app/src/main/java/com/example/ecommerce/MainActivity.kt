package com.example.ecommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration : AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = this.findNavController(R.id.nav_host_fragment)
//        NavigationUI.setupActionBarWithNavController(this, navController)
//        appBarConfiguration = AppBarConfiguration(navController.graph)

        // SetOf "home screens" of the app, top layers, the ones that shouldn't show back button
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.loginFragment,
                R.id.marketListFragment
            ))
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Shutting AppBar overlay shadow
        supportActionBar?.elevation = 0F
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}