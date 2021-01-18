package com.example.fragmentexample.ui.menu1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragmentexample.R
import com.example.fragmentexample.databinding.FragmentMenu1FirstBinding
import com.example.fragmentexample.databinding.FragmentMenu1SecondBinding
import com.example.fragmentexample.ui.BaseFragment

class Menu1SecondFragment : BaseFragment(Menu1SecondFragment::class.simpleName) {

    lateinit var binding: FragmentMenu1SecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMenu1SecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMenu1Second.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .add(R.id.container_main, Menu1ThirdFragment(), "Menu1Third")
                .addToBackStack("ThirdFragment")
                .commit()
        }
    }

}