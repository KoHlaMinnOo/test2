package com.example.app1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app1.databinding.FragmentHomeBinding
import com.example.app1.screens.info.InfoFragment
import com.example.app1.screens.order.OrderFragment
import com.example.app1.screens.search.SearchFragment
import com.example.app1.screens.signup.SignUpFragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater)
        binding.btnInfo.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, InfoFragment())?.commit()
        }
        binding.btnOrder.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, OrderFragment())?.commit()
        }
        binding.btnSearch.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, SearchFragment())?.commit()
        }
        binding.btnSignUp.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, SignUpFragment())?.commit()
        }
        return binding.root
    }
}