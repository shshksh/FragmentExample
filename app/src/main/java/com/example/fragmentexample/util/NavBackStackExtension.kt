package com.example.fragmentexample.util

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

    setupSelectedListener(fm, startNavGraphId, navHostMap)
    setupReselectedListener(navHostMap)
    setupMainBackStackChangedListener(fm, startNavGraphId, currentNavHost, navHostMap)

    swapFragment(selectedItemId, startNavGraphId, fm, navHostMap)

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
    }
    fm.popBackStack("MainBackStack", FragmentManager.POP_BACK_STACK_INCLUSIVE)
}

private fun getNavHost(
    graph: Int,
    fm: FragmentManager,
    containerId: Int,
    index: Int
): NavHostFragment {
    val fragment = fm.findFragmentByTag("navHostFragment$index") as? NavHostFragment
    fragment?.let { return it }

    val navHost = NavHostFragment.create(graph)
    fm.commitNow {
        add(containerId, navHost, "navHostFragment$index")
    }
    return navHost
}

private fun BottomNavigationView.setupSelectedListener(
    fm: FragmentManager,
    startNavGraphId: Int,
    navHostMap: SparseArray<NavHostFragment>
) {
    setOnNavigationItemSelectedListener {
        fm.popBackStack("MainBackStack", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        swapFragment(it.itemId, startNavGraphId, fm, navHostMap)
        true
    }
}

private fun swapFragment(
    selectedGraphId: Int,
    startNavGraphId: Int,
    fm: FragmentManager,
    navHostMap: SparseArray<NavHostFragment>
) {
    if (selectedGraphId != startNavGraphId) {
        fm.commit {
            detach(navHostMap[startNavGraphId])
            attach(navHostMap[selectedGraphId])
            setPrimaryNavigationFragment(navHostMap[selectedGraphId])
            addToBackStack("MainBackStack")
            setReorderingAllowed(true)
        }
    }
}

private fun BottomNavigationView.setupReselectedListener(navHostMap: SparseArray<NavHostFragment>) {
    setOnNavigationItemReselectedListener {
        val navHostFragment = navHostMap[it.itemId]
        val navController = navHostFragment.navController
        navController.popBackStack(navController.graph.startDestination, false)
    }
}

private fun BottomNavigationView.setupMainBackStackChangedListener(
    fm: FragmentManager,
    startNavGraphId: Int,
    currentNavHost: MutableLiveData<NavHostFragment>,
    navHostMap: SparseArray<NavHostFragment>
) {
    fm.addOnBackStackChangedListener {
        // if you press back button where it is not a root graph, change menu icon
        if (fm.backStackEntryCount == 0)
            menu.findItem(startNavGraphId).isChecked = true
        // when back stack is changed, notify changed navHostFragment to activity
        currentNavHost.value = navHostMap[selectedItemId]!!
    }
}