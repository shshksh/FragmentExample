package com.example.fragmentexample.ui.menu2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fragmentexample.databinding.FragmentMenu1SecondBinding
import com.example.fragmentexample.databinding.FragmentMenu2SecondBinding
import com.example.fragmentexample.ui.BaseFragment
import com.example.fragmentexample.ui.menu1.Menu1SecondFragmentDirections

class Menu2SecondFragment : BaseFragment(Menu2SecondFragment::class.simpleName) {

    lateinit var binding: FragmentMenu2SecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMenu2SecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMenu2Second.setOnClickListener {
            findNavController().navigate(
                Menu2SecondFragmentDirections.actionMenu2SecondFragmentToMenu2ThirdFragment()
            )
        }
    }

}