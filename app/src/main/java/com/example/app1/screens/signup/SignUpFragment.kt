package com.example.app1.screens.signup

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils.isEmpty
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.example.app1.R
import com.example.app1.databinding.FragmentSignUpBinding
import com.example.app1.internet.API
import com.example.app1.screens.login.LoginFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import android.widget.ArrayAdapter as ArrayAdapter


class SignUpFragment : Fragment() {

    var gender: String? = null
    private var isChecking = false
    private var bloodType: String? = null
    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSignUpBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sign_up, container, false
        )

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


        val genderList = arrayOf("Choose your gender type", "Male", "Female")
        val adapter = activity?.applicationContext?.let {
            ArrayAdapter(
                it,
                R.layout.support_simple_spinner_dropdown_item, genderList
            )
        }
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(context, "select", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p0?.getItemAtPosition(p2) != "Choose your gender type") {
                    gender = p0?.getItemAtPosition(p2) as String
                }
            }

        }


        //calender
        val cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd.MM.yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                binding.editTextBirthDate.setText(sdf.format(cal.time).toString())
            }

        binding.editTextBirthDate.setOnClickListener {
            context?.let {
                DatePickerDialog(
                    it, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        }

        //Btn Sign Up
        binding.btnSignUp.setOnClickListener {
            val etName = binding.editTextName.text.toString()
            val etPhone = binding.editTextPhone.text.toString()
            val etWeight = binding.editTextWeight.text.toString()
            val etBirthDate = binding.editTextBirthDate.text.toString()
            when {
                isEmpty(etName) -> {
                    binding.editTextName.error = "Enter You Name"
                    binding.editTextName.requestFocus()
                    return@setOnClickListener
                }
                isEmpty(etPhone) -> {
                    binding.editTextPhone.error = "Enter Your Phone"
                    binding.editTextPhone.requestFocus()
                    return@setOnClickListener
                }
                gender == null -> {
                    val builder = AlertDialog.Builder(context)
                    with(builder) {
                        setTitle("Error")
                        setMessage("Choose your gender type")
                        setPositiveButton("OK", null)
                    }
                    val alertDialog = builder.create()
                    alertDialog.show()
                }
                isEmpty(etWeight) -> {
                    binding.editTextWeight.error = "Enter Your Weight"
                    binding.editTextWeight.requestFocus()
                    return@setOnClickListener
                }
                isEmpty(etBirthDate) -> {
                    binding.editTextBirthDate.error = "Enter Your Weight"
                    binding.editTextBirthDate.requestFocus()
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
                    signUp(etName, etPhone, gender!!, etWeight, etBirthDate, bloodType!!)
                }
            }

        }

        //Login Text
        binding.loginText.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, LoginFragment())
                ?.commit()

        }
        return binding.root

    }

    private fun signUp(
        name: String,
        phone: String,
        gender: String,
        weight: String,
        birthDate: String,
        bloodType: String
    ) {
        coroutineScope.launch {

            val postMemberDeferred = API.retrofitService.postMemberAsync(
                name, phone, gender, weight, birthDate, bloodType, "1000"
            )
            try {
                val result = postMemberDeferred.await()
                Toast.makeText(context, "${result.source()}", Toast.LENGTH_LONG).show()

            } catch (e: Exception) {
                Toast.makeText(context, "${e.message}", Toast.LENGTH_LONG).show()
            }
        }


    }

}

