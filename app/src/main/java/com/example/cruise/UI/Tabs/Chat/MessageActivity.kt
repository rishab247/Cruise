package com.example.cruise.UI.Tabs.Chat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anilokcun.uwmediapicker.UwMediaPicker
import com.anilokcun.uwmediapicker.model.MediaType

import com.example.cruise.Adapter.ChatFragment.MessageAdapter
import com.example.cruise.Data.Messages
import com.example.cruise.Data.Messageschat
import com.example.cruise.Data.User_Info
import com.example.cruise.R
import com.google.android.gms.common.util.IOUtils
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import net.alhazmy13.mediapicker.Image.ImagePicker
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
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
    val OPEN_MEDIA_PICKER = 1 // Request code

    val REQUEST_CODE = 123
    var timeMilli: Long = 0

    private lateinit var auth: FirebaseAuth
    private lateinit var currentuser: FirebaseUser
    lateinit var database: FirebaseDatabase
    lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)
        mMessageList = ArrayList()
        context = this
        auth = FirebaseAuth.getInstance()
        currentuser = auth.currentUser!!
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        mSendButton = findViewById(R.id.send_id)
         mAddButton = findViewById(R.id.addmedia)
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








        mAddButton.setOnClickListener {

//            val intent = Intent(this, MessageActivity::class.java)
//            // Set the title
//            // Set the title
//            intent.putExtra("title", "Select media")
//            // Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
//            // Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
//            intent.putExtra("mode", 1)
//            intent.putExtra("maxSelection", 3) // Optional
//
//            startActivityForResult(intent, OPEN_MEDIA_PICKER)
//

            UwMediaPicker.with(this@MessageActivity)						// Activity or Fragment
            .setRequestCode(REQUEST_CODE)				// Give request code, default is 0
                    .setGalleryMode(UwMediaPicker.GalleryMode.ImageGallery) // GalleryMode: ImageGallery/VideoGallery/ImageAndVideoGallery, default is ImageGallery
                    .setGridColumnCount(4)                                  // Grid column count, default is 3
                    .setMaxSelectableMediaCount(1)                         // Maximum selectable media count, default is null which means infinite
                    .setLightStatusBar(true)                                // Is llight status bar enable, default is true
                    .enableImageCompression(true)				// Is image compression enable, default is false
                    .setCompressionMaxWidth(1280F)				// Compressed image's max width px, default is 1280
                    .setCompressionMaxHeight(720F)				// Compressed image's max height px, default is 720
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)		// Compressed image's format, default is JPEG
                    .setCompressionQuality(85)				// Image compression quality, default is 85
                    .setCompressedFileDestinationPath("/storage/emulated/0/cruise/images/")	// Compressed image file's destination path, default is Pictures Dir
                    .open()

//            Log.e("TAG", "onCreate: ")
//            ImagePicker.Builder(this@MessageActivity)
//                .mode(ImagePicker.Mode.GALLERY)
//                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
//                .directory("/storage/emulated/0/cruise/images/")
//                    .extension(ImagePicker.Extension.JPG)
//                .scale(600, 600)
//                .allowMultipleImages(false)
//                .enableDebuggingMode(true)
//                .build()
        }


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
    private fun sendimage(  path :String) {
             val date = Date()
            updatetime()

            timeMilli = date.getTime()
            if (path.equals("")  ) {
                Log.e("TAG", "sendmessage: error")
                Toast.makeText(context, "Invalid message", Toast.LENGTH_SHORT).show()
            } else {



              uploadimage(path)

//                var messages: Messages = Messages();
//                messages.mMessage = mMessageType.text.toString()
//                messages.mType = "2"
//
//                //TODO: time mask  = 3000000000000
//                messages.mTime = timeMilli
//                messages.mEmail = userInfo.Email
//                mMessageType.setText("")
//                val myRef: DatabaseReference =
//                        database.getReference("Public/Message/" + senderInfo.msgToken + "/msg")
//                myRef.push().setValue(messages)

            }



    }

    private fun uploadimage(path :String) {
        Log.e("TAG", "onActivityResult: "+path )
        val storageRef: StorageReference = storage.getReference()

        val mountainsRef: StorageReference = storageRef.child("message/"+senderInfo.msgToken+path )
        if (Build.VERSION.SDK_INT < 29) {

            val fIS = FileInputStream(File(path))
//
//            val bitmap: Bitmap = (imageView.getDrawable() as BitmapDrawable).getBitmap()
//            val baos = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data: ByteArray = IOUtils.toByteArray(fIS)

            val uploadTask: UploadTask = mountainsRef.putBytes(data)
            uploadTask.addOnSuccessListener(object :  OnSuccessListener<UploadTask.TaskSnapshot> {
                override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                    Log.e("TAG", "onSuccess: "  )
                 }

            })

        }else{

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val mPaths: List<String>? = data?.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH)

            if (mPaths != null) {
                                sendimage(mPaths.get(0))

            }


        }
        if (data != null && resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            val selectedImagesPathsList = data.getStringArrayExtra(UwMediaPicker.UwMediaPickerImagesArrayKey)
            if (selectedImagesPathsList != null) {
                for ( i in selectedImagesPathsList){
                    Log.e("TAG", "onActivityResult: "+i )
                }
            }
         }

//        if (requestCode === OPEN_MEDIA_PICKER) {
//            // Make sure the request was successful
//                 val selectionResult: ArrayList<String> = data?.getStringArrayListExtra("result")!!
//                if (selectionResult != null) {
//                    for(s in selectionResult){
//                        Log.e("TAG", "onActivityResult: "+s )
//                    }
//
//            }
//        }

    }

    override fun onBackPressed() {
        updatetime()
        super.onBackPressed()
    }
}