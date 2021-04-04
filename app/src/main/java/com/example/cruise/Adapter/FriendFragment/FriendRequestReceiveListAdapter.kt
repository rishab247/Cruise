package com.example.cruise.Adapter.FriendFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cruise.Data.User_Info
import com.example.cruise.R

class FriendRequestReceiveListAdapter(private val data: ArrayList<User_Info>, fm: FragmentManager) : RecyclerView.Adapter<FriendRequestReceiveListAdapter.FriendsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.friendrequest_listrow,
                parent,
                false
        )

        return FriendsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val currentItem = data[position]

        holder.textView1.text = currentItem.Name
        holder.textView2.text = currentItem.Uid



    }

    override fun getItemCount(): Int {
        return data.size
    }


    class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.findViewById(R.id.textView6)
        val textView2: TextView = itemView.findViewById(R.id.textView7)
    }


}