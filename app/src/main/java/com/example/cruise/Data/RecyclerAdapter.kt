package com.example.cruise.Data

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cruise.R
import com.example.cruise.ui.BottomSheetFragment


class RecyclerAdapter(private val data: List<User_Info>, val fm: FragmentManager) : RecyclerView.Adapter<RecyclerAdapter.FriendsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.friends_list_row,
            parent,
            false
        )
        return FriendsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val currentItem = data[position]

        holder.textView1.text = currentItem.Name
        holder.textView2.text = currentItem.Uid

        val bottomsheet = BottomSheetFragment()
        val bundle = Bundle()
        bundle.putParcelable("data",currentItem )

        // implementing the functionality of recycler view
        holder.itemView.setOnClickListener{
            Log.e("Clicked", holder.itemView.toString())
            bottomsheet.show(
                fm,
                "BottomSheetDialog"
            )
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }


    class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val imageView: ImageView = itemView.image_view
        val textView1: TextView = itemView.findViewById(R.id.textView6)
        val textView2: TextView = itemView.findViewById(R.id.textView7)
    }


}