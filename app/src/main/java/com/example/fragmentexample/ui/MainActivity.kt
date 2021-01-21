package com.example.fragmentexample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.fragmentexample.R
import com.example.fragmentexample.databinding.ActivityMainBinding
import com.example.fragmentexample.util.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState ?: setupBottomNavBar()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        setupBottomNavBar()
    }

    val BottomNavigationView.last: Int
    get() = 1
    private fun setupBottomNavBar() {

        val bottomNavGraphList =
            listOf(R.navigation.nav_menu1, R.navigation.nav_menu2, R.navigation.nav_menu3)
        val currentNavHost = binding.navMainBottom.setupWithNavController(
            supportFragmentManager, bottomNavGraphList, R.id.container_main
        )

        val topLevelDestSet =
            setOf(R.id.menu1FirstFragment, R.id.menu2FirstFragment, R.id.menu3FirstFragment)
        val appBarConfiguration = AppBarConfiguration(topLevelDestSet)

        currentNavHost.observe(this) {
            binding.appbarMainTop.setupWithNavController(it.navController, appBarConfiguration)
        }
    }
}