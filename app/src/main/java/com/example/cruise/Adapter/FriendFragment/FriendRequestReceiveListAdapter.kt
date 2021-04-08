package com.example.cruise.Adapter.FriendFragment

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cruise.Data.User_Info
import com.example.cruise.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class FriendRequestReceiveListAdapter(private val data: ArrayList<User_Info>,private val user_info:User_Info) : RecyclerView.Adapter<FriendRequestReceiveListAdapter.FriendsViewHolder>(){
    lateinit var myRef: DatabaseReference
    var currentUser: FirebaseUser? = null
     lateinit var mAuth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.friendrequest_listrow,
                parent,
                false
        )
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        currentUser = mAuth.currentUser
        return FriendsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val currentItem = data[position]

        holder.nameText.text = currentItem.Name
        holder.uidText.text = currentItem.Uid

        holder.requestaccept.setOnClickListener({

            acceptrequest(currentItem)
            Log.e("TAG", "onBindViewHolder: "+currentItem.Uid )

        })


        holder.requestrejected.setOnClickListener({
            rejectrequrest(currentItem)
            Log.e("TAG", "onBindViewHolder: reject"+currentItem.Uid )
        })

    }

    private fun rejectrequrest(requestuser: User_Info) {

//       delete request sent to the requesuser



        myRef = database.getReference("Public/incoming_request/"+ user_info.RequestToken+"/" +requestuser.RequestToken)
        myRef.removeValue()
        myRef = database.getReference("Public/incoming_request/"+ requestuser.RequestToken+"/" +user_info.RequestToken)
        myRef.removeValue()
notifyDataSetChanged()
        Log.e("TAG", "rejectrequrest: ", )


    }

    private fun acceptrequest(requestuser: User_Info) {
        //check is requestid is already present then delete
        Log.e("TAG", "accept: ww", )

        myRef = database.getReference("Private/friend_list/"+ currentUser!!.uid )
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChild(requestuser.RequestToken)){
                    rejectrequrest(requestuser)
                    Log.e("TAG", "rejectrequrest: ", )

                }
                else
                {
                    myRef = database.getReference("Private/friend_list/"+ currentUser!!.uid +"/"+requestuser.RequestToken)
                    myRef.setValue(requestuser);
                    Log.e("TAG", "accept: ", )

                    rejectrequrest(requestuser)
                }



            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

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