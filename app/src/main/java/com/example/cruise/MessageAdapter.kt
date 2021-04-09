package com.example.cruise

import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView

class MessageAdapter(private val messageList: List<Messages>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val mMessageList : List<Messages> = messageList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.MessageViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.message_single_layout, parent, false)

        return MessageViewHolder(v)
    }

    override fun onBindViewHolder(holder: MessageAdapter.MessageViewHolder, position: Int) {
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