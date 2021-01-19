package com.example.fragmentexample.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.fragmentexample.R
import com.example.fragmentexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val menu1Host = NavHostFragment.create(R.navigation.nav_menu1)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, menu1Host, "Menu1NavHost")
            .setPrimaryNavigationFragment(menu1Host)
            .commitNow()

        val manager = menu1Host.childFragmentManager
        manager.addOnBackStackChangedListener {
            Log.d(
                "TestFragmentManager",
                "TestBackStackCount: ${manager.backStackEntryCount}"
            )
            for (i in 0 until manager.backStackEntryCount) {
                Log.d(
                    "TestFragmentManager",
                    "TestBackStackAt(${i}): ${manager.getBackStackEntryAt(i).name}"
                )
            }
        }
    }

    fun detachNavHost() {
        val navHost = supportFragmentManager.findFragmentByTag("Menu1NavHost")
        navHost?.let {
            supportFragmentManager.beginTransaction()
                .detach(it)
                .commit()
        }
    }

    fun attachNavHost() {
        val navHost = supportFragmentManager.findFragmentByTag("Menu1NavHost")
        navHost?.let {
            supportFragmentManager.beginTransaction()
                .attach(it)
                .commit()
        }
    }
}