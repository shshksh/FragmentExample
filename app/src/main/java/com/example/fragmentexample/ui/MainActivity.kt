package com.example.fragmentexample.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentexample.R
import com.example.fragmentexample.databinding.ActivityMainBinding
import com.example.fragmentexample.ui.menu1.Menu1FirstFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.addOnBackStackChangedListener {
            Log.d(
                "TestFragmentManager",
                "TestBackStackCount: ${supportFragmentManager.backStackEntryCount}"
            )
            for (i in 0 until supportFragmentManager.backStackEntryCount) {
                Log.d(
                    "TestFragmentManager",
                    "TestBackStackAt(${i}): ${supportFragmentManager.getBackStackEntryAt(i).name}"
                )
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, Menu1FirstFragment(), "Menu1First")
            .addToBackStack("FirstFragment")
            .commit()

    }

}