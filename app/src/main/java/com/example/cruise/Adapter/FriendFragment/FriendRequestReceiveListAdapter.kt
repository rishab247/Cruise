package com.example.cruise.Adapter.FriendFragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

        holder.nameText.text = currentItem.Name
        holder.uidText.text = currentItem.Uid

        holder.requestaccept.setOnClickListener({
            Log.e("TAG", "onBindViewHolder: "+currentItem.Uid )
        })


        holder.requestrejected.setOnClickListener({
            Log.e("TAG", "onBindViewHolder: reject"+currentItem.Uid )
        })

    }

    override fun getItemCount(): Int {
        return data.size
    }


    class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.textView6)
        val uidText: TextView = itemView.findViewById(R.id.textView7)
          var requestaccept: Button = itemView.findViewById(R.id.requestaccept)
          var requestrejected: Button=itemView.findViewById(R.id.requestrejected)
    }


}