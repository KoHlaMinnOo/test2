package com.example.app1.screens.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app1.R
import com.example.app1.databinding.FragmentInfoBinding
import com.example.app1.internet.API
import com.example.app1.screens.search.MemberListItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class InfoFragment : Fragment() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentInfoBinding.inflate(inflater)
        coroutineScope.launch {
            val getPropertiesDeferred = API.retrofitService.getRequestListAsync()
            try {
                val listResult = getPropertiesDeferred.await()
                binding.info.text = listResult.toString()
            } catch (e: Exception) {
                binding.info.text = "error ${e.message}"
            }
        }

        return binding.root
    }

}