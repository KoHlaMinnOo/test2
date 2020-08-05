package com.example.app1.screens.login

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.app1.R
import com.example.app1.databinding.FragmentLoginBinding
import com.example.app1.screens.signup.SignUpFragment


class LoginFragment : Fragment() {
    private val name = "kkk"
    private val phone = "123"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )


        //Text Sign up
        binding.signUpText.setOnClickListener {
            // Navigation.findNavController(view).navigate(R.id.action_homeFragment2_to_signUpFragment)
        }


        //Btn Login
        binding.btnLogin.setOnClickListener {
            val etname = binding.editTextName.text.toString()
            val etphone = binding.editTextPhone.text.toString()

            if (TextUtils.isEmpty(etname)) {
                binding.editTextName.error = "Enter your name"
                binding.editTextName.requestFocus()
                return@setOnClickListener
            } else if (TextUtils.isEmpty(etphone)) {
                binding.editTextPhone.error = "Enter your phone"
                binding.editTextPhone.requestFocus()
                return@setOnClickListener
            } else if (etname == name && etphone == phone) {
                // Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_memberFragment)
            } else {
                Toast.makeText(context, "Name or Password Incorrect", Toast.LENGTH_SHORT).show()
            }

        }
        binding.signUpText.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, SignUpFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        return binding.root

    }


}