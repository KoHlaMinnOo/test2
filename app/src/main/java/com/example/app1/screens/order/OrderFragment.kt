package com.example.app1.screens.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app1.R
import com.example.app1.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOrderBinding.inflate(inflater)
        binding.btnOrder.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, RequestBloodFragment())
                ?.commit()
        }
        binding.btnRequestList.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, RequestListFragment())
                ?.commit()
        }
        return binding.root
    }


}