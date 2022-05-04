package com.dhrodao.androidshop.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.dhrodao.androidshop.main.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

open class MainActivity : AppCompatActivity() {
    //private lateinit var mainViewModel: MainViewModel

    private lateinit var navigationView: NavigationView
    lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main) as ActivityMainBinding
        //binding.viewModel = mainViewModel

        // Set the toolbar
        val toolBar = binding.topAppBar
        setSupportActionBar(toolBar)

        // Get nav controller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        restoreCurrentStackIfExists(savedInstanceState)

        // Get navigation view (drawer)
        navigationView = findViewById(R.id.nav_view)

        // Set Nav Controller to the Drawer view
        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)

        // Create a new Builder
        val builder = AppBarConfiguration.Builder(setOf(R.id.fruitShopFragment,
            R.id.landingFragment, R.id.chatFragment, R.id.sentMessagesFragment,
            R.id.sportsShopFragment, R.id.butcherShopFragment, R.id.inboxFragment,
            R.id.fishShopFragment, R.id.globalBasketFragment))

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        builder.setOpenableLayout(drawerLayout)

        val appBarConfiguration = builder.build()
        // Set the AppBar with the NavController
        toolBar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveCurrentStack(outState)
    }

    private fun saveCurrentStack(bundle: Bundle){
        val backStack: Bundle? = navController.saveState()
        bundle.putBundle("backStack", backStack)
    }

    private fun restoreCurrentStackIfExists(bundle: Bundle?){
        if(bundle != null) {
            val backStack: Bundle? = bundle.getBundle("backStack")
            navController.restoreState(backStack)
        }else{
            navController.graph = navController.navInflater.inflate(R.navigation.nav_graph)
        }
    }
}