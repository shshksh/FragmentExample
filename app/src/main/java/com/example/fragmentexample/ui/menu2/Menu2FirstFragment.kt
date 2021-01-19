package com.example.fragmentexample.ui.menu2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fragmentexample.databinding.FragmentMenu2FirstBinding
import com.example.fragmentexample.ui.BaseFragment

class Menu2FirstFragment : BaseFragment(Menu2FirstFragment::class.simpleName) {

    lateinit var binding: FragmentMenu2FirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMenu2FirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMenu2First.setOnClickListener {
            findNavController().navigate(
                Menu2FirstFragmentDirections.actionMenu2FirstFragmentToMenu2SecondFragment()
            )
        }
    }

}