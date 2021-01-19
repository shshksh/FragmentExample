package com.example.fragmentexample.ui.menu1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fragmentexample.R
import com.example.fragmentexample.databinding.FragmentMenu1ThirdBinding
import com.example.fragmentexample.ui.BaseFragment
import com.example.fragmentexample.ui.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class Menu1ThirdFragment : BaseFragment(Menu1ThirdFragment::class.simpleName) {

    lateinit var binding: FragmentMenu1ThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMenu1ThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMenu1Third.setOnClickListener {
            Log.d("testDetach", "onViewCreated: detach ")
            (activity as? MainActivity)?.detachNavHost()
            lifecycleScope.launch {
                delay(2000)
                Log.d("testAttach", "onViewCreated: attach ")
                (activity as? MainActivity)?.attachNavHost()
            }
        }
    }

}