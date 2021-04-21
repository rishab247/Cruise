package com.example.cruise.UI.Tabs.Chat

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cruise.Adapter.ChatFragment.MessageAdapter
import com.example.cruise.Data.Messages
import com.example.cruise.R

class MessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)


        val mSendButton : ImageView = findViewById(R.id.send_id)
        val mAddButton: ImageView = findViewById(R.id.add_id)
        val mMessageType : EditText = findViewById(R.id.message_id)
        val mChatList: RecyclerView = findViewById(R.id.chat_message)


        val mMessageList : ArrayList<Messages> = ArrayList()
        mMessageList.add(Messages("ASDF", "AFD", 234, false))
        val mAdapter = MessageAdapter(mMessageList)

        val mLinearLayout = LinearLayoutManager(this)
        mChatList.setHasFixedSize(true)
        mChatList.layoutManager = mLinearLayout

        mChatList.adapter = mAdapter




    }
}