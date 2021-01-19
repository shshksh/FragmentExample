package com.example.fragmentexample.ui.menu3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fragmentexample.databinding.FragmentMenu3ThirdBinding
import com.example.fragmentexample.ui.BaseFragment

class Menu3ThirdFragment : BaseFragment(Menu3ThirdFragment::class.simpleName) {

    lateinit var binding: FragmentMenu3ThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMenu3ThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMenu3Third.setOnClickListener {

        }
    }

}