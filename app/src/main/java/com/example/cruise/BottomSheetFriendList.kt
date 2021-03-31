package com.example.cruise

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cruise.Data.FriendListAdapter
import com.example.cruise.Data.FriendsData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFriendList(context: Context) : BottomSheetDialogFragment() {

    val mContext: Context = context;


    var listData: ArrayList<FriendsData>? = ArrayList<FriendsData>()
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.friend_request, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))
        listData?.add(FriendsData("Tushar Tambi", "18BCS10888"))


        val friends: RecyclerView = view.findViewById(R.id.friendList)
        friends.adapter = FriendListAdapter(listData!!)
        friends.layoutManager = LinearLayoutManager(mContext)
        friends.setHasFixedSize(true)

    }
}