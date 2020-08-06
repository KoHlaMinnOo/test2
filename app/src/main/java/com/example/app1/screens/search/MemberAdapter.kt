package com.example.app1.screens.search

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app1.MemberData
import com.example.app1.R
import kotlinx.android.synthetic.main.member_list_item.view.*

class MemberAdapter(
    private val recyclerList:List<MemberData>,
    private val listener: MemberFragment
):RecyclerView.Adapter<MemberAdapter.RecyclerViewHolder> (){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val listItem=LayoutInflater.from(parent.context).
                inflate(R.layout.member_list_item,parent,false)
        return RecyclerViewHolder(listItem)
    }

    override fun getItemCount()=recyclerList.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentItem=recyclerList[position]
        holder.bloodType?.text = currentItem.blood_type
        holder.name?.text = currentItem.name
        holder.date?.text = currentItem.birth_date
        holder.gender?.text = currentItem.gender
        val phone=currentItem.ph_no
        holder.phone?.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(phone)))
            it.context.startActivity(intent)
        }

    }



    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val bloodType: TextView? =itemView.text_view_blood_type
        val name: TextView? =itemView.item_name
        val date: TextView? =itemView.item_date
        val gender: TextView? =itemView.item_gender
        val phone: ImageView? =itemView.btn_phone

        init {
            itemView.setOnClickListener(this)
        }


        override fun onClick(p0: View?) {
            val position=adapterPosition
            if (position!=RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }

        }



    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}