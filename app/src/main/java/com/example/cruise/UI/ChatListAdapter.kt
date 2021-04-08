package com.example.cruise.UI

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cruise.Adapter.FriendFragment.FriendRequestReceiveListAdapter
import com.example.cruise.Data.FriendsData
import com.example.cruise.Data.User_Info
import com.example.cruise.R

class ChatListAdapter(private val data: ArrayList<FriendsData>, fm: FragmentManager) : RecyclerView.Adapter<ChatListAdapter.FriendsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.chat_list_row,
                parent,
                false
        )



        return FriendsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val currentItem = data[position]

        holder.nameText.text = currentItem.userName
        holder.uidText.text = currentItem.lastMessage

        holder.nameText.setOnClickListener{
            Log.e("Clicked", "Clicked")
        }

        holder.uidText.setOnClickListener{
            Log.e("Clicked", "Clicked")
        }



    }

    override fun getItemCount(): Int {
        return data.size
    }


    class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.textView6)
        val uidText: TextView = itemView.findViewById(R.id.textView7)


    }


}