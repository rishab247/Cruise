package com.example.cruise.UI.Tabs.Chat

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cruise.Adapter.ChatFragment.MessageAdapter
import com.example.cruise.Data.Messages
import com.example.cruise.Data.User_Info
import com.example.cruise.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.*

class MessageActivity : AppCompatActivity() {

   lateinit  var mSendButton : ImageView
    lateinit  var mAddButton: ImageView
    lateinit  var mMessageType : EditText
    lateinit  var mChatList: RecyclerView
    lateinit var userInfo: User_Info
    lateinit var senderInfo: User_Info
    lateinit var  mMessageList : ArrayList<Messages>
    lateinit var  context :Context

      var   timeMilli: Long  =0

    private lateinit var auth: FirebaseAuth
    private lateinit var currentuser: FirebaseUser
    lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)
        mMessageList  = ArrayList()
    context  = this
        auth = FirebaseAuth.getInstance()
        currentuser = auth.currentUser!!
        database = FirebaseDatabase.getInstance()

        mSendButton = findViewById(R.id.send_id)
          mAddButton = findViewById(R.id.add_id)
          mMessageType = findViewById(R.id.message_id)
          mChatList = findViewById(R.id.chat_message)
          userInfo = User_Info()
            userInfo.get(this)
        senderInfo = (intent.getParcelableExtra("sender") as? User_Info)!!

        //set time
        val date = Date()
          timeMilli    = date.getTime()

        var myRef: DatabaseReference = database.getReference("Public/Message/" + senderInfo.msgToken+"/"+userInfo.Name )
            myRef.setValue(timeMilli)



        sendmessage()

        //getdata

          myRef = database.getReference("Public/Message/" + senderInfo.msgToken+"/msg"  )
        myRef.addChildEventListener(object  : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.e("TAG", "onChildAdded: "+snapshot.value.toString()   )

                mMessageList.add(snapshot.getValue(Messages::class.java)!!)

                val mAdapter = MessageAdapter(mMessageList,userInfo,senderInfo)

                val mLinearLayout = LinearLayoutManager(context)
                mChatList.setHasFixedSize(true)
                mChatList.layoutManager = mLinearLayout

                mChatList.adapter = mAdapter


            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

        })








        val mAdapter = MessageAdapter(mMessageList, userInfo, senderInfo)

        val mLinearLayout = LinearLayoutManager(this)
        mChatList.setHasFixedSize(true)
        mChatList.layoutManager = mLinearLayout

        mChatList.adapter = mAdapter




    }

    private fun sendmessage() {
        mSendButton.setOnClickListener {
            val date = Date()
            timeMilli    = date.getTime()
            if(mMessageType.text.equals("")){
                Toast.makeText( context, "Invalid message", Toast.LENGTH_SHORT).show()
            }
            else{
                var messages: Messages  = Messages();
                messages.mMessage = mMessageType.text.toString()
                messages.mType = "1"

                //TODO: time mask  = 3000000000000
                messages.mTime = 3000000000000-timeMilli
                messages.msendername = userInfo.Name
                mMessageType.setText("")
                val myRef: DatabaseReference = database.getReference("Public/Message/" + senderInfo.msgToken+"/msg"  )
                 myRef. push().setValue(messages)

            }
        }



    }
}