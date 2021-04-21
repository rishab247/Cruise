package com.example.cruise.Adapter.FriendFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cruise.Data.User_Info
import com.example.cruise.R
import com.example.cruise.UI.Tabs.Chat.MessageActivity
import com.example.cruise.UI.Tabs.Friends.BottomSheetFragment


class FriendRequestSendListAdapter(var context:Context,private val data: List<User_Info>, val fm: FragmentManager, val friendflag: Int) : RecyclerView.Adapter<FriendRequestSendListAdapter.FriendsViewHolder>() {
    private var isBottomSheetShowing = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.friends_listrow,
                parent,
                false
        )
        return FriendsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val currentItem = data[position]
        isBottomSheetShowing = false
        holder.textView1.text = currentItem.Name
        holder.textView2.text = currentItem.Uid

        if(friendflag==0)
        {
            if(!isBottomSheetShowing){
                val bottomsheet = BottomSheetFragment()
                val bundle = Bundle()
                bundle.putParcelable("receiver_data", currentItem)
                bottomsheet.arguments = bundle
                Log.e("Clicked", currentItem.Uid)


                // implementing the functionality of recycler view
                holder.itemView.setOnClickListener {
                    bottomsheet.show(
                            fm,
                            "BottomSheetDialog"
                    )
                }
                isBottomSheetShowing = true
            }else{
                isBottomSheetShowing=false
            }
        }
        else{
            holder.itemView.setOnClickListener {

                Toast.makeText(context,"Cell clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, MessageActivity::class.java)
                context.startActivity(intent)
            }
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }


    class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val imageView: ImageView = itemView.image_view
        val textView1: TextView = itemView.findViewById(R.id.textView6)
        val textView2: TextView = itemView.findViewById(R.id.textView7)
    }


}