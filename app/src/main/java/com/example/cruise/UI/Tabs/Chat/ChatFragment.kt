package com.example.cruise.UI.Tabs.Chat

import android.content.Context
import android.os.Bundle
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
import com.example.cruise.Data.User_Info

class ChatFragment : Fragment() {
    lateinit var  fm: FragmentManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_chat, container, false)


        val context: Context = activity as FragmentActivity
        fm = (context as FragmentActivity).supportFragmentManager


        val listView: RecyclerView = v.findViewById(R.id.chat_list)

        val mList: ArrayList<User_Info> = ArrayList()
        var user_Info = User_Info()
        user_Info.Name   = "Rishab"
        mList.add(user_Info)
        user_Info.Name  = "Tushar"
        mList.add(user_Info)

        listView.adapter = ChatListAdapter(context, mList)
        listView.layoutManager = LinearLayoutManager(context)
        listView.setHasFixedSize(true)


        return v
    }




}