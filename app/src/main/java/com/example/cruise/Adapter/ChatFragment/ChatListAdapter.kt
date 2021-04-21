package com.example.cruise.Adapter.ChatFragment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cruise.Data.FriendsData
import com.example.cruise.Data.User_Info
import com.example.cruise.R
import com.example.cruise.UI.Tabs.Chat.MessageActivity

class ChatListAdapter(    var context :   Context, private val data: ArrayList<User_Info>) : RecyclerView.Adapter<ChatListAdapter.FriendsViewHolder>(){


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

        holder.nameText.text = currentItem.Name
        holder.uidText.text = currentItem.Name

        holder.itemView.setOnClickListener {

             Toast.makeText(context,"Cell clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, MessageActivity::class.java)
            intent.putExtra("sender",currentItem);

            context.startActivity(intent)
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