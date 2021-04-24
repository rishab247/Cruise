package com.example.cruise.Adapter.ChatFragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cruise.Data.Messages
import com.example.cruise.Data.User_Info
import com.example.cruise.R


class MessageAdapter(private val messageList: List<Messages>,var userInfo: User_Info,var senderInfo: User_Info,var mLastSeentime: Long) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val mMessageList : List<Messages> = messageList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val message: Messages = mMessageList[viewType]

        val v: View
        Log.e("asda", "onCreateViewHolder: "+message.mMessage +viewType.toString())





        if(viewType==0)
            v  = LayoutInflater.from(parent.context).inflate(R.layout.item_message_send, parent, false)
        else
            v  = LayoutInflater.from(parent.context).inflate(R.layout.item_message_receive, parent, false)

        return MessageViewHolder(v,message.mType)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message: Messages = mMessageList[position]

        holder.messageText.text = message.mMessage

    }

    override fun getItemCount(): Int {
        return mMessageList.size

    }

    class MessageViewHolder(itemView: View,type:String) : RecyclerView.ViewHolder(itemView) {
        var messageText: TextView
//        var imageView: CircleImageView
//        var messageImage: ImageView

        init {
            messageText = itemView.findViewById(R.id.message_text_layout)
//            imageView = itemView.findViewById(R.id.message_profile_layout)
//            messageImage = itemView.findViewById(R.id.message_image_layout)
        }
    }

    override fun getItemViewType(position: Int): Int {

        val message: Messages = mMessageList[position]

        if(message.mEmail.equals(userInfo.Email))
        return 0
        return 1
    }
}