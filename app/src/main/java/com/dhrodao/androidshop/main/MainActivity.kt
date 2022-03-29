package com.dhrodao.androidshop.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

open class MainActivity : AppCompatActivity() {
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the toolbar
        val toolBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.topAppBar)
        setSupportActionBar(toolBar)

        // Get nav controller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        navController.graph = navController.navInflater.inflate(R.navigation.nav_graph)

        // Get navigation view (drawer)
        navigationView = findViewById(R.id.nav_view)

        // Set Nav Controller to the Drawer view
        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)

        // Create a new Builder
        val builder = AppBarConfiguration.Builder(R.id.nav_graph)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        builder.setOpenableLayout(drawerLayout)

        val appBarConfiguration = builder.build()
        // Set the AppBar with the NavController
        toolBar.setupWithNavController(navController, appBarConfiguration)
    }
}