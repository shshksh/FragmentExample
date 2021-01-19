package com.example.fragmentexample.ui.menu3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fragmentexample.databinding.FragmentMenu3FirstBinding
import com.example.fragmentexample.ui.BaseFragment

class Menu3FirstFragment : BaseFragment(Menu3FirstFragment::class.simpleName) {

    lateinit var binding: FragmentMenu3FirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMenu3FirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMenu3First.setOnClickListener {
            findNavController().navigate(
                Menu3FirstFragmentDirections.actionMenu3FirstFragmentToMenu3SecondFragment()
            )
        }
    }

}