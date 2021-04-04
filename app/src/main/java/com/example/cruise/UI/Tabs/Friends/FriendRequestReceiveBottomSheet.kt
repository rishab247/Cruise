package com.example.cruise.UI.Tabs.Friends

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cruise.Adapter.FriendFragment.FriendRequestReceiveListAdapter
import com.example.cruise.Data.User_Info
import com.example.cruise.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class FriendRequestReceiveBottomSheet : BottomSheetDialogFragment() {
    var currentUser: FirebaseUser? = null
    var requestData: ArrayList<User_Info>? = ArrayList<User_Info>()
    lateinit var myRef: DatabaseReference
    lateinit var user_info: User_Info
    lateinit var  fm: FragmentManager

     lateinit var mAuth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var friensrequestlist: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v: View = inflater.inflate(R.layout.friend_request, container, false)



        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fm = (context as FragmentActivity).supportFragmentManager


        friensrequestlist = view.findViewById(R.id.requestListView)
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        currentUser = mAuth.currentUser
        user_info = User_Info()
        user_info.get(activity!!)
        requestData  = ArrayList()
        myRef = database.getReference("Public/incoming_request/"+ user_info.RequestToken )
             myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                 override fun onDataChange(snapshot: DataSnapshot) {
                     for (snap in snapshot.children) {
                         var data = snap.getValue(User_Info::class.java)
                         if (data != null) {
                             Log.e(TAG, "onDataChange: "+data.Uid )
                             requestData!!.add(data)

                         }
                     }

                     friensrequestlist.adapter = FriendRequestReceiveListAdapter(requestData!!, fm )
                     friensrequestlist.layoutManager = LinearLayoutManager(context)
                     friensrequestlist.setHasFixedSize(true)
                 }

                 override fun onCancelled(error: DatabaseError) {
                     TODO("Not yet implemented")
                 }

             })



    }

}