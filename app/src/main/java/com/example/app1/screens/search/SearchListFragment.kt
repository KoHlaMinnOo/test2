package com.example.app1.screens.search

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.app1.R
import com.example.app1.databinding.FragmentSearchListBinding

class SearchListFragment : Fragment() {

    private var bloodType: String? = null
    private var isChecking = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentSearchListBinding.inflate(inflater)

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
            when{
                bloodType==null->{
                    val builder = AlertDialog.Builder(context)
                    with(builder) {
                        setTitle("Error")
                        setMessage("Choose your Blood_Type type")
                        setPositiveButton("OK", null)
                    }
                    val alertDialog = builder.create()
                    alertDialog.show()
                }
                else->{
                    val bundle = Bundle()
                    bundle.putString("bloodType", bloodType)
                    val fragment = MemberFragment()
                    fragment.arguments = bundle

                    activity?.supportFragmentManager?.beginTransaction()?.replace(
                        R.id.fragment_container, fragment
                    )
                        ?.addToBackStack(null)
                        ?.commit()
                }
            }
        }
        return binding.root
    }
}

