package com.example.app1.screens.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app1.databinding.FragmentRequestListBinding
import com.example.app1.internet.API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class RequestListFragment : Fragment() ,
OrderAdapter.OnItemClickListener{
    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRequestListBinding.inflate(inflater)
        coroutineScope.launch {
            val getRequestListDeferred=API.retrofitService.getRequestListAsync()
            try {
                val resultList=getRequestListDeferred.await()
                val adapter=OrderAdapter(resultList,this@RequestListFragment)
                binding.recyclerView.adapter=adapter
                binding.recyclerView.layoutManager=LinearLayoutManager(context)
                binding.recyclerView.setHasFixedSize(true)
                binding.progressBar.visibility=View.GONE
            }catch (e:Exception){

            }
        }

        return binding.root
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(context, "$position", Toast.LENGTH_SHORT).show()
    }

}