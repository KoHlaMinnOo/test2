package com.example.app1.screens.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app1.R
import com.example.app1.databinding.FragmentMemberBinding
import com.example.app1.internet.API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception


class MemberFragment : Fragment(),
    MemberAdapter.OnItemClickListener {

    private var job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bloodType=arguments?.getString("bloodType")
        val binding:FragmentMemberBinding=DataBindingUtil.inflate(
            inflater, R.layout.fragment_member,container,false
        )
        coroutineScope.launch {
            val getMemberDeferred=API.retrofitService.getMemberAsync(bloodType!!)
            try {
                val resultList=getMemberDeferred.await()
                val adapter=MemberAdapter(resultList,this@MemberFragment)
                binding.recyclerView.adapter=adapter
                binding.recyclerView.layoutManager=LinearLayoutManager(context)
                binding.recyclerView.setHasFixedSize(true)
                binding.progressBar.visibility=View.INVISIBLE
            }catch (e:Exception){
            }
        }

        return binding.root
    }
    override fun onItemClick(position: Int) {
        Toast.makeText(context, "Item $position click", Toast.LENGTH_SHORT).show()
    }
}