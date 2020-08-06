package com.example.app1.screens.order

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils.isEmpty
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.app1.HomeFragment
import com.example.app1.R
import com.example.app1.databinding.FragmentRequestBloodBinding
import com.example.app1.internet.API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class RequestBloodFragment : Fragment() {
    private var isChecking = false
    private var bloodType: String? = null
    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentRequestBloodBinding.inflate(inflater)

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


        binding.btnRequestBlood.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val phoneNumber = binding.editTextPhone.text.toString()
            val hospitalName = binding.editTextHospitalName.text.toString()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            when {
                isEmpty(name) -> {
                    binding.editTextName.error = "Enter Your Name"
                    binding.editTextName.requestFocus()
                    return@setOnClickListener
                }
                isEmpty(phoneNumber) -> {
                    binding.editTextPhone.error = "Enter Your Phone Number"
                    binding.editTextPhone.requestFocus()
                    return@setOnClickListener
                }
                isEmpty(hospitalName) -> {
                    binding.editTextHospitalName.error = "Enter Hospital Name"
                    binding.editTextHospitalName.requestFocus()
                    return@setOnClickListener
                }
                bloodType == null -> {
                    val builder = AlertDialog.Builder(context)
                    with(builder) {
                        setTitle("Error")
                        setMessage("Choose your Blood_Type type")
                        setPositiveButton("OK", null)
                    }
                    val alertDialog = builder.create()
                    alertDialog.show()
                }
                else -> {
                    binding.progressBar.visibility=View.VISIBLE
                    coroutineScope.launch {
                        val requestBloodDeferred = API.retrofitService.requestBloodAsync(
                            name, phoneNumber, hospitalName, bloodType!!, currentDate
                        )
                        try {
                            val result = requestBloodDeferred.await()
                            Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                            binding.progressBar.visibility = View.GONE
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(R.id.fragment_container,RequestListFragment())?.commit()
                        } catch (e: Exception) {
                            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                        }

                    }

                }
            }

        }
        return binding.root
    }

}