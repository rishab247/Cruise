package com.example.cruise.Adapter.ChatFragment

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.cruise.Data.Messages
import com.example.cruise.Data.User_Info
import com.example.cruise.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.File


class MessageAdapter(
    public var messageList: List<Messages>,
    var userInfo: User_Info,
    var senderInfo: User_Info,
    var mLastSeentime: Long,
   var context: Context
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val mMessageList : List<Messages> = messageList
    lateinit var storage: FirebaseStorage


    fun set(messageLists: List<Messages>){
        messageList = messageLists
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        val v: View

        storage = FirebaseStorage.getInstance()




        if(viewType==0)
            v  = LayoutInflater.from(parent.context).inflate(R.layout.item_message_send, parent, false)
        else if(viewType==1)
            v  = LayoutInflater.from(parent.context).inflate(R.layout.item_message_receive, parent, false)
        else if(viewType==2)
            v  = LayoutInflater.from(parent.context).inflate(R.layout.item_message_image_send, parent, false)
        else
            v  = LayoutInflater.from(parent.context).inflate(R.layout.item_message_image_receive, parent, false)

        return MessageViewHolder(v,viewType )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message: Messages = mMessageList[position]
        if(message.mType.equals("1")) {
            Log.e(TAG, "onBindViewHolder: 122311231"  )
            holder.messageText!!.text = message.mMessage
        }
        else{
            Log.e(TAG, "onBindViewHolder: 1eeeeghhhg"  )
            holder.messageImage!!.setOnClickListener({
                Log.e(TAG, "onBindViewHolder: 1eeeeghhhg"  )

            })
            downloadFile(message,holder.messageImage)
        }


    }
    private fun downloadFile(  message: Messages,messageImage : ImageView?) {

         var checkfile =  File(Environment.getExternalStorageDirectory().toString() +"/cruise/"+senderInfo.msgToken +"/"+ message.mLocation +".jpg")
//        Log.e(TAG, "downloadFile: " + "message/" + checkfile.toUri()+checkfile.exists())

        if(!checkfile.exists()) {
            Log.e(TAG, "downloadFile: " + "message/" + senderInfo.msgToken)
            val storageRef = storage.getReference("message/" + senderInfo.msgToken)
            val islandRef = storageRef.child(message.mLocation)
            val rootPath =
                File(Environment.getExternalStorageDirectory(), "cruise/" + senderInfo.msgToken)
            if (!rootPath.exists()) {
                rootPath.mkdirs()
            }

            val localFile = File(rootPath, message.mLocation + ".jpg")
            islandRef.getFile(localFile)
                .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot?> {

                    if (messageImage != null) {
                        Log.e(
                        "firebase ",
                        ";local tem file created  created " + localFile.toString()+"   "+Uri.fromFile(  File(localFile.toString()))
                    )
                         val bitmap: Bitmap = MediaStore.Images.Media.getBitmap( context.contentResolver , checkfile.toUri())
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream)
                        val bitmap1 = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.toByteArray().size)

                        messageImage .setImageBitmap(  bitmap1 )
                    }

                    //  updateDb(timestamp,localFile.toString(),position);
                }).addOnFailureListener(OnFailureListener { exception ->
                    Log.e(
                        "firebase ",
                        ";local tem file not created  created $exception"
                    )
                })
        }
        else{
//            var ss: Uri? = checkfile.toUri()
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap( context.contentResolver , checkfile.toUri())
             val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream)
            val bitmap1 = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.toByteArray().size)

            messageImage!!.setImageBitmap(  bitmap1 )

        }
    }
    override fun getItemCount(): Int {
        return mMessageList.size

    }

    class MessageViewHolder(itemView: View,type:Int) : RecyclerView.ViewHolder(itemView) {
            var messageText: TextView? = null

         var messageImage: ImageView? = null

        init {
//            Log.e(TAG, "sdasdasd: "+message.mType+ "   "+message.mMessage )

            if(type==0||type==1)

            messageText = itemView.findViewById(R.id.message_text_layout)
             if(type==2||type==3) {
                 Log.e(TAG, "testing: ")
                   messageImage = itemView.findViewById(R.id.imageView_message_image)
             }
        }
    }

    override fun getItemViewType(position: Int): Int {

        val message: Messages = mMessageList[position]
        if(message.mType.equals("2")) {
            if (message.mEmail.equals(userInfo.Email))
                return 2
            return 3
        }
        if(message.mEmail.equals(userInfo.Email))
        return 0
        return 1
    }
}