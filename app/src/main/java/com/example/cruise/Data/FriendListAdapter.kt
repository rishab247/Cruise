package com.example.cruise.Data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cruise.R

class FriendListAdapter(private val data: ArrayList<FriendsData>) : RecyclerView.Adapter<FriendListAdapter.FriendsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.friendlist_row,
                parent,
                false
        )

        return FriendsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val currentItem = data[position]
//
//        holder.textView1.text = currentItem.name
//        holder.textView2.text = currentItem.id

    }

    override fun getItemCount(): Int {
        return data.size
    }


    class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.findViewById(R.id.textView6)
        val textView2: TextView = itemView.findViewById(R.id.textView7)
    }


}