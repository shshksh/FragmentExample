package com.example.fragmentexample.ui.menu1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fragmentexample.databinding.FragmentMenu1FirstBinding
import com.example.fragmentexample.ui.BaseFragment

class Menu1FirstFragment : BaseFragment(Menu1FirstFragment::class.simpleName) {

    lateinit var binding: FragmentMenu1FirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMenu1FirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMenu1First.setOnClickListener {
            findNavController().navigate(
                Menu1FirstFragmentDirections.actionMenu1FirstFragmentToMenu1SecondFragment()
            )
        }
    }

}