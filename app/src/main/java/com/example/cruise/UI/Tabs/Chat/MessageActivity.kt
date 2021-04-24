package com.example.cruise.UI.Tabs.Chat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cruise.Adapter.ChatFragment.MessageAdapter
import com.example.cruise.Data.Messages
import com.example.cruise.Data.Messageschat
import com.example.cruise.Data.User_Info
import com.example.cruise.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import net.alhazmy13.mediapicker.Image.ImagePicker
import java.util.*
import kotlin.collections.ArrayList


class MessageActivity : AppCompatActivity() {

    lateinit var mSendButton: Button
    lateinit var mAddButton: ImageView
    lateinit var mMessageType: EditText
    lateinit var mChatList: RecyclerView
    lateinit var userInfo: User_Info
    lateinit var senderInfo: User_Info
    lateinit var mMessageList: ArrayList<Messages>
    lateinit var context: Context
    var mLastSeentime: Long = 0L

    var timeMilli: Long = 0

    private lateinit var auth: FirebaseAuth
    private lateinit var currentuser: FirebaseUser
    lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)
        mMessageList = ArrayList()
        context = this
        auth = FirebaseAuth.getInstance()
        currentuser = auth.currentUser!!
        database = FirebaseDatabase.getInstance()

        mSendButton = findViewById(R.id.send_id)
        // mAddButton = findViewById(R.id.add_id)
        mMessageType = findViewById(R.id.message_id)
        mChatList = findViewById(R.id.chat_message)
        userInfo = User_Info()
        userInfo.get(this)
        senderInfo = (intent.getParcelableExtra("sender") as? User_Info)!!

        //set time
        updatetime()



        sendmessage()

        //getdata

        var myRef = database.getReference("Public/Message/" + senderInfo.msgToken)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                mMessageList = ArrayList()


                var msg: Message = Message()
                for (snap in snapshot.children) {
                    var flag: Boolean = false
                    for (sna in snap.children) {
                        flag = true

                        Log.e(
                            "TAG",
                            "onChildAdded:ssss " + (sna.getValue(Messages::class.java)!!).mMessage + "  " + sna.value.toString()
                        )

                        mMessageList.add(sna.getValue(Messages::class.java)!!)


                    }
                    if (flag == false) {
                        if (!snap.key.toString().equals(userInfo.Name)) {

                            mLastSeentime = snap.value.toString().toLong()
                        }
                        Log.e(
                            "TAG",
                            "onChildAdded:ssss " + snap.key.toString() + "  " + snap.value.toString() + " " + mLastSeentime.toString()
                        )
                    }
                }
                val mAdapter = MessageAdapter(mMessageList, userInfo, senderInfo, mLastSeentime)

                val mLinearLayout = LinearLayoutManager(context)
               // mLinearLayout.scrollToPosition(mMessageList.size + 1)
                mChatList.setHasFixedSize(true)
                mChatList.layoutManager = mLinearLayout


                mChatList.adapter = mAdapter
                mChatList.scrollToPosition( mAdapter.itemCount - 1)
                if (mMessageList.size >= 1)
                    updatechatlist(senderInfo, mMessageList[mMessageList.size - 1].mMessage)
            }

        })








//        mAddButton.setOnClickListener {
//            Log.e("TAG", "onCreate: ")
//            ImagePicker.Builder(this@MessageActivity)
//                .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
//                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
//                .directory(ImagePicker.Directory.DEFAULT)
//                .extension(ImagePicker.Extension.PNG)
//                .scale(600, 600)
//                .allowMultipleImages(false)
//                .enableDebuggingMode(true)
//                .build()
//        }


    }

    private fun updatechatlist(senderInfo: User_Info, mMessage: String) {
        val date = Date()
        timeMilli = date.getTime()

        var myRef: DatabaseReference =
            database.getReference("Private/chatlist/" + currentuser.uid + "/" + senderInfo.msgToken)
        var mRecentMessages: Messageschat = Messageschat()



        mRecentMessages.mLastMessage = mMessage
        mRecentMessages.mTime = timeMilli
        mRecentMessages.mMessages = senderInfo
        myRef.setValue(mRecentMessages)
    }

    private fun updatetime() {
        val date = Date()
        timeMilli = date.getTime()

        var myRef: DatabaseReference =
            database.getReference("Public/Message/" + senderInfo.msgToken + "/" + userInfo.Name)
        myRef.setValue(timeMilli)

    }

    private fun sendmessage() {
        mSendButton.setOnClickListener {
            val date = Date()
            updatetime()

            timeMilli = date.getTime()
            if (mMessageType.text.toString().equals("") || mMessageType.text.toString()
                    .equals(" ")
            ) {
                Log.e("TAG", "sendmessage: error")
                Toast.makeText(context, "Invalid message", Toast.LENGTH_SHORT).show()
            } else {
                var messages: Messages = Messages();
                messages.mMessage = mMessageType.text.toString()
                messages.mType = "1"

                //TODO: time mask  = 3000000000000
                messages.mTime = timeMilli
                messages.mEmail = userInfo.Email
                mMessageType.setText("")
                val myRef: DatabaseReference =
                    database.getReference("Public/Message/" + senderInfo.msgToken + "/msg")
                myRef.push().setValue(messages)

            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //Your Code
        }
    }

    override fun onBackPressed() {
        updatetime()
        super.onBackPressed()
    }
}