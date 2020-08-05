package com.example.app1.screens.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.app1.R
import com.example.app1.databinding.FragmentSearchBinding
import com.example.app1.databinding.FragmentSearchListBinding
import com.example.app1.internet.API
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class SearchListFragment : Fragment() {

    private var bloodType:String?=null
    private var isChecking = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding=FragmentSearchListBinding.inflate(inflater)

        //radio group

        binding.radioGroupOne.setOnCheckedChangeListener { _: RadioGroup, i: Int ->
            if (i != -1 && isChecking) {
                isChecking = false
                binding.radioGroupTwo.clearCheck()
            }
            isChecking = true
            val radioButton: RadioButton? = view?.findViewById(i)
            bloodType = radioButton?.text.toString()

        }

        binding.radioGroupTwo.setOnCheckedChangeListener { _: RadioGroup, i: Int ->
            if (i != -1 && isChecking) {
                isChecking = false
                binding.radioGroupOne.clearCheck()

            }
            isChecking = true
            val radioButton: RadioButton? = view?.findViewById(i)
            bloodType = radioButton?.text.toString()

        }

        binding.btnSearch.setOnClickListener {
            val bundle=Bundle()
            bundle.putString("bloodType",bloodType)
            val fragment=MemberFragment()
            fragment.arguments=bundle

            activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.fragment_container,fragment)
                ?.addToBackStack(null)
                ?.commit()
        }
        return binding.root
    }
}

