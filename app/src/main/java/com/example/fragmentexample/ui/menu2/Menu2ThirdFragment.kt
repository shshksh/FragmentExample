package com.example.fragmentexample.ui.menu2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fragmentexample.databinding.FragmentMenu2ThirdBinding
import com.example.fragmentexample.ui.BaseFragment

class Menu2ThirdFragment : BaseFragment(Menu2ThirdFragment::class.simpleName) {

    lateinit var binding: FragmentMenu2ThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMenu2ThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMenu2Third.setOnClickListener {

        }
    }

}