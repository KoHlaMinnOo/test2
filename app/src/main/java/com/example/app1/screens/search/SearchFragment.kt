package com.example.app1.screens.search

import android.os.Bundle
import android.text.TextUtils.isEmpty
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.app1.R
import com.example.app1.databinding.FragmentSearchBinding
import com.example.app1.internet.API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchFragment : Fragment() {
    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater)
        binding.btnLogin.setOnClickListener {

            val phNo = binding.editTextPhone.text.toString()
            val password = binding.editTextPassword.text.toString()
            when {

                isEmpty(phNo) -> {
                    binding.editTextPhone.error = "Enter Your Phone Number"
                    binding.editTextPhone.requestFocus()
                    return@setOnClickListener
                }
                isEmpty(password) -> {
                    binding.editTextPassword.error = "Enter Your Password"
                    binding.editTextPassword.requestFocus()
                    return@setOnClickListener
                }
                else->{
                    binding.progressBar.visibility=View.VISIBLE
                    coroutineScope.launch {
                        val adminLoginInfo = API.retrofitService.adminLoginAsync(phNo, password)
                        try {
                            val result = adminLoginInfo.await()
                            binding.progressBar.visibility=View.GONE
                            if (!(result.error || result.message != "success")) {
                                activity?.supportFragmentManager?.beginTransaction()
                                    ?.replace(R.id.fragment_container, SearchListFragment())
                                    ?.commit()
                            } else {
                                Toast.makeText(context, "${result.message}", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "${e.message}", Toast.LENGTH_LONG).show()
                        }

                    }
                }

            }

        }
        return binding.root
    }

}