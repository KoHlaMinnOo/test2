package com.example.app1.screens.order

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app1.OrderData
import com.example.app1.R
import kotlinx.android.synthetic.main.request_list_item.view.*

class OrderAdapter(
    private val recyclerList: List<OrderData>,
    private val listener: RequestListFragment
) : RecyclerView.Adapter<OrderAdapter.RecyclerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val listItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.request_list_item, parent, false)
        return RecyclerViewHolder(listItem)
    }

    override fun getItemCount() = recyclerList.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentItem = recyclerList[position]
        holder.name?.text = currentItem.name
        holder.bloodType?.text = currentItem.blood_type
        holder.hospitalName?.text = currentItem.hospital_name
        val phone = currentItem.ph_no
        holder.phone?.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(phone)))
            it.context.startActivity(intent)
        }

    }


    inner class RecyclerViewHolder(item_view: View) : RecyclerView.ViewHolder(item_view),
        View.OnClickListener {
        val name: TextView? = item_view.item_name
        val phone: ImageView? = item_view.item_phone
        val bloodType: TextView? = item_view.item_blood_type
        val hospitalName: TextView? = item_view.item_hospital_name

        init {
            item_view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}

