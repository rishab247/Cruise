package com.example.cruise.UI.Tabs.Friends

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cruise.Adapter.FriendFragment.FriendRequestSendListAdapter
import com.example.cruise.Data.User_Info
import com.example.cruise.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class FriendsFragment : Fragment() {
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var memberData: ArrayList<User_Info>? = ArrayList<User_Info>()
    var friendData: ArrayList<User_Info>? = ArrayList<User_Info>()
    lateinit var  fm: FragmentManager

    lateinit var user_info: User_Info
    lateinit var mAuth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    var currentUser: FirebaseUser? = null
    lateinit var myRef: DatabaseReference
    lateinit var mListView: RecyclerView
    lateinit var floatbutton: FloatingActionButton
     override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_friends, container, false)
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        currentUser = mAuth.currentUser
        memberData = ArrayList()
        friendData = ArrayList()
//        swipeRefreshLayout = v.findViewById(R.id.swipe);
//        swipeRefreshLayout.canScrollVertically()
//        swipeRefreshLayout.setOnRefreshListener {
//           swipeRefreshLayout.isRefreshing = false
//        }



          var context :Context   = activity as FragmentActivity

              fm = (context as FragmentActivity).supportFragmentManager

        user_info = User_Info()
        user_info.get(activity!!)

        // implementing the list view for friend list
        floatbutton = v.findViewById(R.id.friendfloat)
        mListView = v.findViewById(R.id.listView)
//        val exampleList = generateDummyList(10)
        mListView.adapter = FriendRequestSendListAdapter(context,memberData!!, fm,0)
        mListView.layoutManager = LinearLayoutManager(context)
        mListView.setHasFixedSize(true)
        loadfrienddata(context)


        var flag =0
        floatbutton.setOnClickListener {
            if (flag == 0) {
                flag = 1
//                floatbutton.setIcon(R.drawable.baseline_account_circle_black_24dp)
                Toast.makeText(context,"members",Toast.LENGTH_SHORT).show();
                loadmemberdata(context)


            }
            else{
                flag=0
                Toast.makeText(context,"friends",Toast.LENGTH_SHORT).show();

                loadfrienddata(context)
            }
        }

        return v
    }

    private fun loadfrienddata(context : Context) {
        if(friendData!=null) {
            mListView.adapter = FriendRequestSendListAdapter(context,friendData!!, fm,1)

            myRef = database.getReference("Private/friend_list/"+ (currentUser?.uid ?: "null"))
            if (friendData!!.size == 0)
                myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (snap in snapshot.children) {
                            var data = snap.getValue(User_Info::class.java)
                            if (data != null) {
                                Log.e(TAG, "onDataChange: " + data.Uid)
                                if (user_info.Uid != data.Uid) {

                                    friendData?.add(data)
                                    mListView.adapter?.notifyDataSetChanged()

                                }
                            }


                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
        }
    }
    private fun loadmemberdata(context:Context) {
        if(memberData!=null) {
            mListView.adapter = FriendRequestSendListAdapter(context,memberData!!, fm,0)

            myRef = database.getReference("Public/member_list/")
            if (memberData!!.size == 0)
                myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (snap in snapshot.children) {
                            var data = snap.getValue(User_Info::class.java)
                            if (data != null) {
                                Log.e(TAG, "onDataChange: " + data.Uid)
                                if (user_info.Uid != data.Uid) {

                                    memberData?.add(data)
                                    mListView.adapter?.notifyDataSetChanged()

                                }
                            }


                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
        }
    }


}