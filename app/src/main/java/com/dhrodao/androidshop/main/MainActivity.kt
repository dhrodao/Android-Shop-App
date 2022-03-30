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

    private lateinit var navController: NavController

    private var CURR_FRAGMENT: String = "CURR_FRAGMENT"

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
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        recoverActualFragment(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveCurrentFragment(outState)
    }

    private fun saveCurrentFragment(bundle: Bundle){
        val fragmentId = getCurrentFragment()
        bundle.putInt(CURR_FRAGMENT, fragmentId)
    }

    private fun getCurrentFragment() : Int{
        return navController.currentDestination?.id ?: 0
    }

    private fun recoverActualFragment(bundle: Bundle){
        val fragmentId = bundle.getInt(CURR_FRAGMENT)
        navController.navigate(fragmentId)
    }
}