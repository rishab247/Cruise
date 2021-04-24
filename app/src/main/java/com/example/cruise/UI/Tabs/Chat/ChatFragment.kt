package com.example.cruise.UI.Tabs.Chat

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cruise.Data.FriendsData
import com.example.cruise.R
import com.example.cruise.Adapter.ChatFragment.ChatListAdapter
import com.example.cruise.Data.Messageschat
import com.example.cruise.Data.User_Info
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import java.util.*
import kotlin.collections.ArrayList

class ChatFragment : Fragment() {
    lateinit var fm: FragmentManager


    private val messageAdapter = GroupAdapter<GroupieViewHolder>()


    private lateinit var auth: FirebaseAuth
    private lateinit var currentuser: FirebaseUser
    lateinit var database: FirebaseDatabase
    lateinit var mChatList: ArrayList<Messageschat>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_chat, container, false)


        val context: Context = activity as FragmentActivity
        fm = (context as FragmentActivity).supportFragmentManager
        auth = FirebaseAuth.getInstance()
        currentuser = auth.currentUser!!
        database = FirebaseDatabase.getInstance()

        val listView: RecyclerView = v.findViewById(R.id.chat_list)
        mChatList = ArrayList()


        var myRef = database.getReference("Private/chatlist/" + auth.uid)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
//                TODO("Not yet implemented")
                mChatList = ArrayList()
//                Log.e("TAG", "onDataChange: "+snapshot.getValue(String::class.java) )
                for (snap in snapshot.children) {
                    mChatList.add(snap.getValue(Messageschat::class.java)!!)

                }

                listView.adapter = ChatListAdapter(context, mChatList)
                listView.layoutManager = LinearLayoutManager(context)
                listView.setHasFixedSize(true)


            }

        })






        return v
    }


}