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

class ChatFragment : Fragment() {
    lateinit var  fm: FragmentManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_chat, container, false)


        val context: Context = activity as FragmentActivity
        fm = (context as FragmentActivity).supportFragmentManager


        val listView: RecyclerView = v.findViewById(R.id.chat_list)

        val mList: ArrayList<FriendsData> = ArrayList()
        mList.add(FriendsData("Tushar Tambi", "Hi how are your"))
        mList.add(FriendsData("Rishab Aggarwal", "Hi I am fine"))

        listView.adapter = ChatListAdapter(context, mList)
        listView.layoutManager = LinearLayoutManager(context)
        listView.setHasFixedSize(true)


        return v
    }




}