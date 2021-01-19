package com.example.fragmentexample.ui

import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.set
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.fragmentexample.R
import com.example.fragmentexample.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavGraphList =
                listOf(R.navigation.nav_menu1, R.navigation.nav_menu2, R.navigation.nav_menu3)
        val currentNavHost = binding.navMainBottom.setupWithNavController(
                bottomNavGraphList,
                R.id.container_main)
        val topLevelDestSet = setOf(R.id.menu1FirstFragment, R.id.menu2FirstFragment, R.id.menu3FirstFragment)
        val appBarConfiguration = AppBarConfiguration(topLevelDestSet)

        currentNavHost.observe(this) {
            binding.appbarMainTop.setupWithNavController(it.navController, appBarConfiguration)
        }
    }

    private fun BottomNavigationView.setupWithNavController(navGraphList: List<Int>, containerId: Int): MutableLiveData<NavHostFragment> {

        val navHostMap = SparseArray<NavHostFragment>()
        val startNavGraphId = selectedItemId
        val currentNavHost = MutableLiveData<NavHostFragment>()

        navGraphList.forEachIndexed { index: Int, graph: Int ->
            val navHost = NavHostFragment.create(graph)
            supportFragmentManager.commitNow {
                add(containerId, navHost)
                detach(navHost)
            }
            val manager = navHost.childFragmentManager
            manager.addOnBackStackChangedListener {
                Log.d(
                        "BackStackChanged",
                        "nav$index-count: ${manager.backStackEntryCount}"
                )
                for (i in 0 until manager.backStackEntryCount) {
                    Log.d(
                            "BackStackChanged",
                            "nav$index-BackStackAt(${i}): ${manager.getBackStackEntryAt(i).name}"
                    )
                }
            }
            navHostMap[navHost.navController.graph.id] = navHost
        }

        supportFragmentManager.commit {
            attach(navHostMap[startNavGraphId]!!)
            setPrimaryNavigationFragment(navHostMap[startNavGraphId]!!)
        }
        currentNavHost.value = navHostMap[selectedItemId]!!


        supportFragmentManager.addOnBackStackChangedListener {
            Log.d(
                    "BackStackChanged",
                    "support-count: ${supportFragmentManager.backStackEntryCount}"
            )
            for (i in 0 until supportFragmentManager.backStackEntryCount) {
                Log.d(
                        "BackStackChanged",
                        "support-BackStackAt(${i}): ${supportFragmentManager.getBackStackEntryAt(i).name}"
                )
            }
            if (supportFragmentManager.backStackEntryCount == 0)
                menu.findItem(startNavGraphId).isChecked = true
            currentNavHost.value = navHostMap[selectedItemId]!!
        }

        setOnNavigationItemSelectedListener {
            supportFragmentManager.popBackStack(
                    "outerBackStack",
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            if (it.itemId != startNavGraphId) {
                Log.d("TAG", "onCreate: ${it.itemId}")
                supportFragmentManager.commit {
                    attach(navHostMap[it.itemId]!!)
                    setPrimaryNavigationFragment(navHostMap[it.itemId]!!)
                    addToBackStack("outerBackStack")
                    setReorderingAllowed(true)
                }
            }
            true
        }

        setOnNavigationItemReselectedListener {
            val navHostFragment = navHostMap[it.itemId]!!
            navHostFragment.navController.popBackStack(navHostFragment.navController.graph.startDestination, false)
        }

        return currentNavHost
    }

}