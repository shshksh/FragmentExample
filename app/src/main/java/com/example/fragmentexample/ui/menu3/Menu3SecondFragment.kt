package com.example.fragmentexample.ui.menu3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fragmentexample.databinding.FragmentMenu3SecondBinding
import com.example.fragmentexample.ui.BaseFragment

class Menu3SecondFragment : BaseFragment(Menu3SecondFragment::class.simpleName) {

    lateinit var binding: FragmentMenu3SecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMenu3SecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMenu3Second.setOnClickListener {
            findNavController().navigate(
                Menu3SecondFragmentDirections.actionMenu3SecondFragmentToMenu3ThirdFragment()
            )
        }
    }

}