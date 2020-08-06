package com.example.app1.screens.order

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app1.OrderData
import com.example.app1.R
import com.example.app1.RequestInfo
import com.example.app1.databinding.FragmentRequestListBinding
import com.example.app1.internet.API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class RequestListFragment : Fragment(),
    OrderAdapter.OnItemClickListener {
    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)
    private lateinit var resultList: RequestInfo
    lateinit var progressBar:ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRequestListBinding.inflate(inflater)
        coroutineScope.launch {
            val getRequestListDeferred = API.retrofitService.getrequestListAsync()
            try {
                resultList = getRequestListDeferred.await()
                if (resultList.error) {
                    Toast.makeText(context, resultList.message, Toast.LENGTH_SHORT).show()
                } else {
                    val adapter = OrderAdapter(resultList.data, this@RequestListFragment)
                    binding.recyclerView.adapter = adapter
                    binding.recyclerView.layoutManager = LinearLayoutManager(context)
                    binding.recyclerView.setHasFixedSize(true)
                    progressBar=binding.progressBar
                    progressBar.visibility=View.GONE
                }

            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onItemClick(position: Int) {
        val builder = AlertDialog.Builder(context)
        with(builder) {
            setTitle("Warning")
            setMessage("Are you want to delete")
            setPositiveButton("OK") { _, _ ->
                deleteRequest(resultList.data[position])
            }
            setNegativeButton("Cancel", null)
        }
        val alertDialog = builder.create()
        alertDialog.show()

    }

    private fun deleteRequest(data: OrderData) {
        coroutineScope.launch {
            progressBar.visibility=View.VISIBLE
            val deleteRequestDeferred = API.retrofitService.deleteRequestAsync(data.ph_no)
            try {
                val result=deleteRequestDeferred.await()
                if (result.error) {

                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }else{
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.fragment_container,RequestListFragment())
                        ?.commit()
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                    progressBar.visibility=View.GONE
                }

            }catch (e:Exception){
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

}