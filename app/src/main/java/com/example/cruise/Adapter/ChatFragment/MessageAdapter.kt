package com.example.cruise.Adapter.ChatFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cruise.Data.Messages
import com.example.cruise.Data.User_Info
import com.example.cruise.R
import de.hdodenhof.circleimageview.CircleImageView

class MessageAdapter(private val messageList: List<Messages>, userInfo: User_Info, senderInfo: User_Info) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val mMessageList : List<Messages> = messageList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.message_single_layout, parent, false)

        return MessageViewHolder(v)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val c: Messages = mMessageList[position]

        holder.messageText.text = c.mMessage

    }

    override fun getItemCount(): Int {
        return mMessageList.size

    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var messageText: TextView
        var imageView: CircleImageView
        var messageImage: ImageView

        init {
            messageText = itemView.findViewById(R.id.message_text_layout)
            imageView = itemView.findViewById(R.id.message_profile_layout)
            messageImage = itemView.findViewById(R.id.message_image_layout)
        }
    }


}