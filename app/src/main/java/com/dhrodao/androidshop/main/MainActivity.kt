package com.dhrodao.androidshop.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.dhrodao.androidshop.main.databinding.ActivityMainBinding
import com.dhrodao.androidshop.main.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationView

open class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var navigationView: NavigationView
    lateinit var binding: ActivityMainBinding

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main) as ActivityMainBinding
        binding.viewModel = mainViewModel

        // Set the toolbar
        val toolBar = binding.topAppBar
        setSupportActionBar(toolBar)

        // Get nav controller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
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

        if(savedInstanceState != null){
            recoverActualFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        saveActualFragment()
    }

    private fun saveActualFragment(){
        navController.currentDestination?.id?.let {
            mainViewModel.setCurrentFragment(it)
        }
    }

    private fun recoverActualFragment(){
        val fragmentId = mainViewModel.getCurrentFragment()
        navController.navigate(fragmentId)
    }
}