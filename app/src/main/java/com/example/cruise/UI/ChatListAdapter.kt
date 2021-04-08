package com.example.cruise.UI

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cruise.CellClickListener
import com.example.cruise.Data.FriendsData
import com.example.cruise.R
import com.example.cruise.UI.Tabs.ChatFragment

class ChatListAdapter(context: Context, private val data: ArrayList<FriendsData>, private val cellClickListener: CellClickListener) : RecyclerView.Adapter<ChatListAdapter.FriendsViewHolder>(){


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

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener()
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