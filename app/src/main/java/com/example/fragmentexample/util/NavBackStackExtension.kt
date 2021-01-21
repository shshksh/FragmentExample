package com.example.fragmentexample.util

import android.util.Log
import android.util.SparseArray
import androidx.core.util.set
import androidx.core.view.get
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.setupWithNavController(
        fm: FragmentManager,
        navGraphList: List<Int>,
        containerId: Int
): MutableLiveData<NavHostFragment> {

    val navHostMap = SparseArray<NavHostFragment>()
    val startNavGraphId = menu[0].itemId
    val currentNavHost = MutableLiveData<NavHostFragment>()

    initNavHosts(navGraphList, fm, containerId, navHostMap)

    // attach root navGraph and notify
    fm.commit {
        attach(navHostMap[startNavGraphId])
        setPrimaryNavigationFragment(navHostMap[startNavGraphId])
    }
    currentNavHost.value = navHostMap[startNavGraphId]

    setupMainBackStackChangedListener(fm, startNavGraphId, currentNavHost, navHostMap)
    setupSelectedListener(fm, startNavGraphId, navHostMap)
    setupReselectedListener(navHostMap)

    if (selectedItemId != startNavGraphId) {
        fm.commit {
            detach(navHostMap[startNavGraphId])
            attach(navHostMap[selectedItemId])
            setPrimaryNavigationFragment(navHostMap[selectedItemId])
            addToBackStack("MainBackStack")
            setReorderingAllowed(true)
        }
    }

    return currentNavHost
}

private fun initNavHosts(
        navGraphList: List<Int>,
        fm: FragmentManager,
        containerId: Int,
        navHostMap: SparseArray<NavHostFragment>
) {
    navGraphList.forEachIndexed { index, graph ->
        val navHost = getNavHost(graph, fm, containerId, index)
        fm.commit {
            detach(navHost)
        }
        navHostMap[navHost.navController.graph.id] = navHost

        val manager = navHost.childFragmentManager
        manager.addOnBackStackChangedListener {
            Log.d("BackStackChanged", "Nav${index}BackStackCount: ${manager.backStackEntryCount}")
            for (i in 0 until manager.backStackEntryCount) {
                Log.d("BackStackChanged",
                        "Nav${index}BackStackAt(${i}): ${manager.getBackStackEntryAt(i).name}")
            }
        }
    }
    fm.popBackStack("MainBackStack", FragmentManager.POP_BACK_STACK_INCLUSIVE)
}

private fun getNavHost(graph: Int,
                       fm: FragmentManager,
                       containerId: Int,
                       index: Int): NavHostFragment {
    val fragment = fm.findFragmentByTag("navHostFragment$index") as? NavHostFragment
    fragment?.let { return it }

    val navHost = NavHostFragment.create(graph)
    fm.commitNow {
        add(containerId, navHost, "navHostFragment$index")
    }
    return navHost
}

private fun BottomNavigationView.setupMainBackStackChangedListener(
        fm: FragmentManager,
        startNavGraphId: Int,
        currentNavHost: MutableLiveData<NavHostFragment>,
        navHostMap: SparseArray<NavHostFragment>
) {
    fm.addOnBackStackChangedListener {
        Log.d("BackStackChanged", "MainBackStackCount: ${fm.backStackEntryCount}")
        for (i in 0 until fm.backStackEntryCount) {
            Log.d("BackStackChanged",
                    "MainBackStackAt(${i}): ${fm.getBackStackEntryAt(i).name}")
        }
        // if you press back button where it is not a root graph, change menu icon
        if (fm.backStackEntryCount == 0)
            menu.findItem(startNavGraphId).isChecked = true
        // when back stack is changed, notify changed navHostFragment to activity
        currentNavHost.value = navHostMap[selectedItemId]!!
    }
}

private fun BottomNavigationView.setupSelectedListener(
        fm: FragmentManager,
        startNavGraphId: Int,
        navHostMap: SparseArray<NavHostFragment>
) {
    setOnNavigationItemSelectedListener {
        fm.popBackStack("MainBackStack", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        if (it.itemId != startNavGraphId) {
            fm.commit {
                detach(navHostMap[startNavGraphId])
                attach(navHostMap[it.itemId])
                setPrimaryNavigationFragment(navHostMap[it.itemId])
                addToBackStack("MainBackStack")
                setReorderingAllowed(true)
            }
        }
        true
    }
}

private fun BottomNavigationView.setupReselectedListener(navHostMap: SparseArray<NavHostFragment>) {
    setOnNavigationItemReselectedListener {
        val navHostFragment = navHostMap[it.itemId]
        val navController = navHostFragment.navController
        navController.popBackStack(navController.graph.startDestination, false)
    }
}