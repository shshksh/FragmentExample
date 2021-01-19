package com.example.fragmentexample.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import androidx.navigation.fragment.NavHostFragment
import com.example.fragmentexample.R
import com.example.fragmentexample.databinding.ActivityMainBinding
import kotlin.collections.set

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.navMainBottom
        val navHostContainerId = R.id.container_main
        val startNavGraphId = bottomNavigationView.selectedItemId

        val bottomNavGraphList =
            listOf(R.navigation.nav_menu1, R.navigation.nav_menu2, R.navigation.nav_menu3)
        val navHostMap = mutableMapOf<Int, NavHostFragment>()

        bottomNavGraphList.forEachIndexed { index: Int, graph: Int ->
            val navHost = NavHostFragment.create(graph)
            supportFragmentManager.commitNow {
                add(navHostContainerId, navHost)
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
                bottomNavigationView.menu.findItem(startNavGraphId).isChecked = true
        }

        bottomNavigationView.setOnNavigationItemSelectedListener {
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
    }

}