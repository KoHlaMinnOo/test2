package com.example.app1.screens.search

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app1.MemberData
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
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)
    private lateinit var resultList: List<MemberData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bloodType = arguments?.getString("bloodType")
        val binding: FragmentMemberBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_member, container, false
        )
        coroutineScope.launch {
            val getMemberDeferred = API.retrofitService.getMemberAsync(bloodType!!)
            try {
               // resultList = getMemberDeferred.await()
                val result=getMemberDeferred.await()

                   if(result.error){
                       Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                   }else{
                       resultList=result.data
                       val adapter=MemberAdapter(result.data,this@MemberFragment)
                       binding.recyclerView.adapter=adapter
                       binding.recyclerView.layoutManager=LinearLayoutManager(context)
                       binding.recyclerView.setHasFixedSize(true)
                   }

                binding.progressBar.visibility=View.INVISIBLE

            } catch (e: Exception) {
            }
        }

        return binding.root
    }

    override fun onItemClick(position: Int) {
        val builder = AlertDialog.Builder(context)
        with(builder) {
            setTitle("Warning")
            setMessage("Are You Want to Delete")
            setPositiveButton("OK") { _, _ ->
               deleteMember(resultList[position])
            }
            setNegativeButton("Cancel", null)
        }
        val alertDialog = builder.create()
        alertDialog.show()


    }

    private fun deleteMember(member: MemberData) {

        val bloodType=member.blood_type
        coroutineScope.launch {
            val deleteMemberDeferred =
                API.retrofitService.deleteMemberAsync(member.ph_no)
            try {
                val result = deleteMemberDeferred.await()
                Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                bundle.putString("bloodType", bloodType)
                val fragment = MemberFragment()
                fragment.arguments = bundle

                activity?.supportFragmentManager?.beginTransaction()?.replace(
                    R.id.fragment_container, fragment
                )
                    ?.commit()
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }

    }
}
